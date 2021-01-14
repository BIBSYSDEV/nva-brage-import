package no.unit.nva.brage.nsddbhimiport;

import no.unit.nva.brage.nsddbhimiport.helpers.JournalResource;
import no.unit.nva.model.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static no.unit.nva.brage.nsddbhimiport.JournalParser.ERROR_PARSING_FILE;
import static no.unit.nva.brage.nsddbhimiport.JournalParser.LEVEL_ERROR;
import static no.unit.nva.brage.nsddbhimiport.JournalParser.MISSING_FILE_ERROR;
import static no.unit.nva.brage.nsddbhimiport.JournalParser.OUT_OF_DATE_PUBLISHER_INFORMATION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JournalParserTest {

    private static final int CUTOFF = 2003;
    public static final String STORBRITANNIA = "Storbritannia";
    public static final String ENGELSK = "Engelsk";
    public static final String ONLINE_ISSN = "1752-7554";
    public static final String PRINT_ISSN = "0047-2336";
    public static final String SAMFUNNSVITENSKAP = "Samfunnsvitenskap";
    public static final String UTVIKLINGSSTUDIER = "Utviklingsstudier";
    public static final String EXPECTED_TITLE = "Journal of Contemporary Asia";
    public static final String EXPECTED_URL = "https://www.tandfonline.com/toc/rjoc20/current";
    public static final String NOT_AN_ISSN = "nonsense";
    public static final String TWENTY_TWENTY = "2020";
    public static final String TWENTY_NINETEEN = "2019";


    /**
     * This test fails whenever data is imported from the API, which it must be because the Excel output suffers from
     * character set issues. The data must be fixed because double quotes are not escaped in the data.
     *
     * Typically, the data is imported from https://api.nsd.no/dbhapiklient/ table 851, using query in
     * @link{example_nsd_journal_query.json}
     *
     * Typically, the quotes are in the journal titles and publisher names. Some end-quotes are missing too.
     *
     * On one occasion, a double quote was found at the end of a URL.
     */
    @Test
    void journalParserParsesCurrentPublishingChannelFile() {
        var journalParser = new JournalParser();
        assertDoesNotThrow(() -> journalParser.byIssn(ONLINE_ISSN));
    }

    @Test
    void journalParserThrowsRuntimeExceptionWhenPublishingChannelDataIsInvalid() {
        Executable executable = () -> new JournalParser(JournalResource.getCatPicture());
        var exception = assertThrows(RuntimeException.class, executable);
        assertThat(exception.getMessage(), equalTo(ERROR_PARSING_FILE));
    }

    @Test
    void journalParserThrowsRuntimeExceptionWhenPublishingChannelDataIsMissing() {
        Executable executable = () -> new JournalParser(JournalResource.getNonExistingResource());
        var exception = assertThrows(RuntimeException.class, executable);
        assertThat(exception.getMessage(), equalTo(MISSING_FILE_ERROR));
    }

    @ParameterizedTest(name = "JournalParser.byIssn({0}) returns publisher information")
    @ValueSource(strings = {ONLINE_ISSN,PRINT_ISSN})
    void journalParserByIssnReturnsPublisherInfoWhenPrintOrOnlineIssnIsFound(String issn) {
        var journalParser = new JournalParser(JournalResource.validResource());
        var publisherInfo = journalParser.byIssn(issn);
        assertThat(publisherInfo.getCountry(), equalTo(STORBRITANNIA));
        assertThat(publisherInfo.getLanguage(), equalTo(ENGELSK));
        assertThat(publisherInfo.getLevel(), equalTo(getLevel()));
        assertThat(publisherInfo.getOnlineIssn(), equalTo(ONLINE_ISSN));
        assertThat(publisherInfo.getPrintIssn(), equalTo(PRINT_ISSN));
        assertThat(publisherInfo.getNpiSubject(), equalTo(SAMFUNNSVITENSKAP));
        assertThat(publisherInfo.getNpiSubjectHeading(), equalTo(UTVIKLINGSSTUDIER));
        assertThat(publisherInfo.getOpenAccess(), equalTo(false));
        assertThat(publisherInfo.getTitle(), equalTo(EXPECTED_TITLE));
        assertThat(publisherInfo.getUrl(), equalTo(EXPECTED_URL));
    }

    @Test
    void journalParserByIssnReturnsNullWhenPrintOrOnlineIssnIsFound() {
        var journalParser = new JournalParser(JournalResource.validResource());
        var publisherInfo = journalParser.byIssn(NOT_AN_ISSN);
        assertThat(publisherInfo, is(nullValue()));
    }

    @Test
    void journalParserThrowsRuntimeExceptionWhenDataIsOutdated() {
        Executable executable = () -> new JournalParser(JournalResource.invalidOldResource());
        var exception = assertThrows(RuntimeException.class, executable);
        assertThat(exception.getMessage(), equalTo(OUT_OF_DATE_PUBLISHER_INFORMATION));
    }

    @Test
    void journalParserThrowsRuntimeExceptionNpiLevelIsNotNumber() {
        Executable executable = () -> new JournalParser(JournalResource.invalidLevelResourceNotNumber());
        var exception = assertThrows(RuntimeException.class, executable);
        assertThat(exception.getMessage(), equalTo(LEVEL_ERROR));
    }

    @Test
    void journalParserThrowsRuntimeExceptionNpiLevelIsOutOfRange() {
        Executable executable = () -> new JournalParser(JournalResource.invalidLevelResourceOutOfRange());
        var exception = assertThrows(RuntimeException.class, executable);
        assertThat(exception.getMessage(), equalTo(LEVEL_ERROR));
    }

    @Test
    void journalParserReturnsHigherValueWhenMultipleLevelsAreRegisteredAndValuesAreOneAndTwo() {
        var journalParser = new JournalParser(JournalResource.multipleLevelValuesResource());
        var publisherInfo = journalParser.byIssn(PRINT_ISSN);
        assertThat(publisherInfo.getLevel().get(TWENTY_TWENTY), equalTo(Level.LEVEL_2));
    }

    @Test
    void journalParserReturnsHigherValueWhenMultipleLevelsAreRegisteredAndValuesAreZeroAndOne() {
        var journalParser = new JournalParser(JournalResource.multipleLevelValuesResource());
        var publisherInfo = journalParser.byIssn(PRINT_ISSN);
        assertThat(publisherInfo.getLevel().get("2016"), equalTo(Level.LEVEL_1));
    }

    @Test
    void journalParserReturnsLevelOneWhenLevelOneIsRegistered() {
        var journalParser = new JournalParser(JournalResource.multipleLevelValuesResource());
        var publisherInfo = journalParser.byIssn(PRINT_ISSN);
        assertThat(publisherInfo.getLevel().get("2018"), equalTo(Level.LEVEL_1));
    }

    @Test
    void journalParserReturnsLevelZeroWhenLevelZeroIsRegistered() {
        var journalParser = new JournalParser(JournalResource.multipleLevelValuesResource());
        var publisherInfo = journalParser.byIssn(PRINT_ISSN);
        assertThat(publisherInfo.getLevel().get("2017"), equalTo(Level.LEVEL_0));
    }

    @Test
    void journalParserReturnsNoLevelWhenNoValueIsRegistered() {
        var journalParser = new JournalParser(JournalResource.multipleLevelValuesResource());
        var publisherInfo = journalParser.byIssn(PRINT_ISSN);
        assertThat(publisherInfo.getLevel().get(TWENTY_NINETEEN), equalTo(Level.NO_LEVEL));
    }

    private Map<String, Level> getLevel() {
        int year = adjustYearForNsdDbhLatency();
        var level = new HashMap<String, Level>();
        while (year > CUTOFF) {
            level.put(String.valueOf(year), Level.LEVEL_1);
            year--;
        }
        return level;
    }

    private int adjustYearForNsdDbhLatency() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return LocalDate.now().getMonthValue() > 2 ? currentYear : currentYear - 1;
    }
}
