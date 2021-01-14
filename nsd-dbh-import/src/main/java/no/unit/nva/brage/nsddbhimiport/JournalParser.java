package no.unit.nva.brage.nsddbhimiport;

import no.unit.nva.model.Level;
import nva.commons.utils.SingletonCollector;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;

public class JournalParser {
    public static final String ORIGINAL_TITLE = "Original tittel";
    private static final String INTERNATIONAL_TITLE = "Internasjonal tittel";
    public static final String BIT_TRUE = "1";
    public static final String DEFAULT_SOURCE = "/publiseringskanaler_tidsskrift.csv";
    public static final int CUTOFF_2003 = 2003;
    public static final char SEMICOLON = ';';
    public static final String PRINT_ISSN = "Print ISSN";
    public static final String ONLINE_ISSN = "Online ISSN";
    public static final String OPEN_ACCESS = "Open Access";
    public static final String NPI_SUBJECT_HEADING = "NPI Fagfelt";
    public static final String NPI_SUBJECT = "NPI Fagområde";
    public static final String COUNTRY = "Utgiverland";
    public static final String LANGUAGE = "Språk";
    public static final String URL = "Url";
    public static final String LEVEL_HEADER_STUB = "Nivå ";
    public static final String OUT_OF_DATE_PUBLISHER_INFORMATION = "The application is out of date, Unit needs to update"
            + " the publisher information for the current year";
    public static final char QUOTE_CHAR = '"';
    public static final String LEVEL_ERROR = "Could not generate level list for publication channel";
    public static final String ERROR_PARSING_FILE = "Cannot parse CSV for publishing channels";
    public static final String MISSING_FILE_ERROR = "The Journal Data file is missing";
    private final List<PublisherInfo> journals = new ArrayList<>();

    public JournalParser() {
        this(DEFAULT_SOURCE);
    }

    protected JournalParser(String file) {
        try (Reader reader = new InputStreamReader(JournalParser.class.getResourceAsStream(file), UTF_8)) {
            Iterable<CSVRecord> records;
            records = parseCsv(reader);
            parseJournalRecord(records);
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException(MISSING_FILE_ERROR);
        }
    }

    private Iterable<CSVRecord> parseCsv(Reader reader) throws IOException {
        return CSVFormat.MYSQL
                .withFirstRecordAsHeader()
                .withDelimiter(SEMICOLON)
                .withQuote(QUOTE_CHAR)
                .withHeader()
                .parse(reader);
    }

    private void parseJournalRecord(Iterable<CSVRecord> records) {
        try {
            for (CSVRecord record : records) {
                String title = record.get(ORIGINAL_TITLE);
                String alternativeTitle = record.get(INTERNATIONAL_TITLE);
                String printIssn = record.get(PRINT_ISSN);
                String onlineIssn = record.get(ONLINE_ISSN);
                boolean openAccess = record.get(OPEN_ACCESS).equals(BIT_TRUE);
                String npiSubjectHeading = record.get(NPI_SUBJECT_HEADING);
                String npiSubject = record.get(NPI_SUBJECT);
                String country = record.get(COUNTRY);
                String language = record.get(LANGUAGE);
                String uri = record.get(URL);

                var level = new HashMap<String, Level>();

                int year = adjustForNsdDbhLatency();

                while (year > CUTOFF_2003) {
                    addLevelForYear(record, level, year);
                    year--;
                }

                journals.add(new PublisherInfo(title, alternativeTitle, printIssn, onlineIssn, openAccess,
                        npiSubjectHeading, npiSubject, level, country, language, uri));
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ERROR_PARSING_FILE);
        }
    }

    private int adjustForNsdDbhLatency() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = LocalDate.now().getMonthValue();
        return currentMonth > 2 ? currentYear : currentYear - 1;
    }

    /**
     * Get publication channel data for a Journal by ISSN.
     *
     * @param issn an ISSN.
     * @return Publication information for a journal or null if no data is available.
     */
    public PublisherInfo byIssn(String issn) {
        return journals.stream()
                .filter(journal -> hasIssn(issn, journal))
                .collect(SingletonCollector.collectOrElse(null));
    }

    public PublisherInfo byTitle(String journalTitle) {
        var titles = new ArrayList<String>();
        titles.add(journalTitle);
        addPossibleAlternatives(journalTitle, titles);
        int counter = 0;
        PublisherInfo publisherInfo = null;
        while (isNull(publisherInfo) && counter < titles.size()) {
            var current = titles.get(counter);
            publisherInfo = journals.stream()
                    .filter(journal -> hasTitle(current, journal))
                    .collect(SingletonCollector.collectOrElse(null));
            counter ++;
        }
        return publisherInfo;
    }

    private void addPossibleAlternatives(String journalTitle, ArrayList<String> titles) {
        if (journalTitle.contains("/")) {
            var possibleAlternatives = journalTitle.split("/");
            for (String alternative : possibleAlternatives) {
                titles.add(alternative.trim());
            }
        }
    }

    private void addLevelForYear(CSVRecord record, HashMap<String, Level> level, int year) {
        var currentColumn = LEVEL_HEADER_STUB + year;
        String levelForYear;
        try {
            levelForYear = record.get(currentColumn);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(OUT_OF_DATE_PUBLISHER_INFORMATION);
        }
        level.put(String.valueOf(year), getLevel(levelForYear));
    }

    private Level getLevel(String levelString) {
        if (levelString.isEmpty()) {
            return Level.NO_LEVEL;
        } else if (levelString.equals("2") || levelString.equals("1, 2")) {
            return Level.LEVEL_2;
        } else if (levelString.equals("1")) {
            return Level.LEVEL_1;
        } else if (levelString.equals("0")) {
            return Level.LEVEL_0;
        } else {
            throw new RuntimeException(LEVEL_ERROR);
        }
    }

    private boolean hasIssn(String issn, PublisherInfo journal) {
        return journal.getOnlineIssn().equals(issn) || journal.getPrintIssn().equals(issn);
    }

    private boolean hasTitle(String journalTitle, PublisherInfo journal) {
        return journal.getTitle().equals(journalTitle) || journal.getAlternativeTitle().equals(journalTitle);
    }
}
