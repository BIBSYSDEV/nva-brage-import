package no.unit.nva.brage.importer.generator;

import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import no.unit.nva.importbrage.metamodel.types.PublisherType;
import no.unit.nva.importbrage.metamodel.types.RelationType;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import no.unit.nva.importbrage.metamodel.types.TypeValue;
import no.unit.nva.model.Level;
import no.unit.nva.model.contexttypes.Book;
import no.unit.nva.model.contexttypes.Degree;
import no.unit.nva.model.contexttypes.Journal;
import no.unit.nva.model.contexttypes.PublicationContext;
import no.unit.nva.model.contexttypes.Report;
import no.unit.nva.model.exceptions.InvalidIsbnException;
import no.unit.nva.model.exceptions.InvalidIssnException;
import no.unit.nva.model.instancetypes.book.BookAnthology;
import no.unit.nva.model.instancetypes.book.BookMonograph;
import no.unit.nva.model.instancetypes.cartographic.CartographicMap;
import no.unit.nva.model.instancetypes.chapter.ChapterArticle;
import no.unit.nva.model.instancetypes.degree.DegreeBachelor;
import no.unit.nva.model.instancetypes.degree.DegreeMaster;
import no.unit.nva.model.instancetypes.degree.DegreePhd;
import no.unit.nva.model.instancetypes.degree.OtherStudentWork;
import no.unit.nva.model.instancetypes.journal.FeatureArticle;
import no.unit.nva.model.instancetypes.journal.JournalArticle;
import no.unit.nva.model.instancetypes.journal.JournalLeader;
import no.unit.nva.model.instancetypes.journal.JournalLetter;
import no.unit.nva.model.instancetypes.journal.JournalReview;
import no.unit.nva.model.instancetypes.journal.JournalShortCommunication;
import no.unit.nva.model.instancetypes.report.ReportBasic;
import no.unit.nva.model.instancetypes.report.ReportPolicy;
import no.unit.nva.model.instancetypes.report.ReportResearch;
import no.unit.nva.model.instancetypes.report.ReportWorkingPaper;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class RandomPublishingContext extends RandomPublication {
    public static final String EN_US = "en_US";
    public static final String SERIES_TEMPLATE = "%s;%s";
    private final PublicationContext publicationContext;

    public RandomPublishingContext(Class<?> instance) throws MalformedURLException,
            InvalidIsbnException, InvalidIssnException {
        this.publicationContext = generatePublicationContext(instance);
    }

    private PublicationContext generatePublicationContext(Class<?> instance) throws
            MalformedURLException, InvalidIsbnException, InvalidIssnException {
        if (instance.equals(BookAnthology.class)) {
            return generateBook(true);
        } else if (instance.equals(BookMonograph.class)) {
            return generateBook(false);
        } else if (instance.equals(ChapterArticle.class)) {
            return generateChapter();
        } else if (instance.equals(CartographicMap.class)) {
            return generateCartographicMap();
        } else if (instance.equals(DegreeBachelor.class)) {
            return generateDegree();
        } else if (instance.equals(DegreeMaster.class)) {
            return generateDegree();
        } else if (instance.equals(DegreePhd.class)) {
            return generateDegree();
        } else if (instance.equals(JournalArticle.class)) {
            return generateJournal();
        } else if (instance.equals(FeatureArticle.class)) {
            return generateJournal();
        } else if (instance.equals(JournalLeader.class)) {
            return generateJournal();
        } else if (instance.equals(JournalLetter.class)) {
            return generateJournal();
        } else if (instance.equals(JournalReview.class)) {
            return generateJournal();
        } else if (instance.equals(JournalShortCommunication.class)) {
            return generateJournal();
        } else if (instance.equals(ReportBasic.class)) {
            return generateReport();
        } else if (instance.equals(ReportPolicy.class)) {
            return generateReport();
        } else if (instance.equals(ReportResearch.class)) {
            return generateReport();
        } else if (instance.equals(ReportWorkingPaper.class)) {
            return generateReport();
        } else if (instance.equals(OtherStudentWork.class)) {
            return generateDegree();
        } else {
            throw new RuntimeException("Unsupported Publication context: " + instance.getSimpleName());
        }
    }

    private PublicationContext generateReport() throws MalformedURLException, InvalidIsbnException,
            InvalidIssnException {
        return new Report.Builder()
                .withIsbnList(getIsbns())
                .withLevel(getLevel())
                .withOpenAccess(getOpenAccess())
                .withPeerReviewed(getPeerReviewed())
                .withPublisher(getPublisher())
                .withSeriesNumber(getSeriesNumber())
                .withSeriesTitle(getSeriesTitle())
                .withUrl(getUrl())
                .build();
    }

    private PublicationContext generateJournal() throws MalformedURLException, InvalidIssnException {
        return new Journal.Builder()
                .withPeerReviewed(getPeerReviewed())
                .withLevel(getJournalLevel())
                .withOpenAccess(getOpenAccess())
                .withUrl(getJournalUrl())
                .withOnlineIssn(getOnlineIssn())
                .withPrintIssn(getPrintIssn())
                .withTitle(getJournalTitle())
                .build();
    }

    private Level getJournalLevel() {
        return Level.LEVEL_2;
    }

    private URL getJournalUrl() throws MalformedURLException {
        return URI.create("http://www.nature.com/nature/").toURL();
    }

    private PublicationContext generateDegree() throws MalformedURLException, InvalidIsbnException {
        return new Degree.Builder()
                .withUrl(getUrl())
                .withSeriesTitle(getSeriesTitle())
                .withSeriesNumber(getSeriesNumber())
                .withPublisher(getPublisher())
                .withIsbnList(getIsbns())
                .build();
    }

    private PublicationContext generateCartographicMap() {
        throw new RuntimeException("Cartographic map is not implemented");
    }

    private PublicationContext generateChapter() {
        throw new RuntimeException("Chapter is not implemented");
    }

    private PublicationContext generateBook(boolean isAnthology) throws MalformedURLException, InvalidIsbnException {
        Book.Builder builder = new Book.Builder()
                .withIsbnList(getIsbns())
                .withLevel(getLevel())
                .withOpenAccess(getOpenAccess())
                .withPeerReviewed(getPeerReviewed())
                .withPublisher(getPublisher())
                .withUrl(getUrl());
        if (isAnthology) {
            builder.withSeriesNumber(getSeriesNumber())
                    .withSeriesTitle(getSeriesTitle());
        }
        return builder.build();
    }

    private URL getUrl() throws MalformedURLException {
        URL url = URI.create("https://example.org/cowflash").toURL();
        addDcValue(IdentifierType.IDENTIFIER, IdentifierType.URI.getQualifier(), EN_US, url.toString());
        return url;
    }

    private String getSeriesTitle() {
        return "Snagle";
    }

    private String getSeriesNumber() {
        String seriesNumber = "22";
        var value = String.format(SERIES_TEMPLATE, getSeriesTitle(), seriesNumber);
        addDcValue(RelationType.RELATION, RelationType.IS_PART_OF_SERIES.getQualifier(), EN_US, value);
        return seriesNumber;
    }

    private String getPublisher() {
        String publisher = "Jim's publishing AS";
        addDcValue(PublisherType.PUBLISHER, PublisherType.UNQUALIFIED.getQualifier(), EN_US, publisher);
        return publisher;
    }

    private boolean getPeerReviewed() {
        addDcValue(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(), EN_US, TypeValue.PEER_REVIEWED.getTypeName());
        return true;
    }

    /**
     * This method should return a value, at the present time it returns null
     * as there is no way of extracting this information from Brage.
     * @return false
     */
    private boolean getOpenAccess() {
        return false;
    }

    /**
     * This method should return a value, at the present time it returns null
     * as there is no way of extracting this information from Brage.
     * @return null
     */
    private Level getLevel() {
        return null;
    }

    private List<String> getIsbns() {
        String isbn = "9788253024288";
        addDcValue(IdentifierType.IDENTIFIER, IdentifierType.ISBN.getQualifier(), EN_US, isbn);
        return List.of(isbn);
    }

    public PublicationContext getPublicationContext() {
        return publicationContext;
    }

    private String getJournalTitle() {
        String title = "Nature";
        addDcValue(SourceType.SOURCE, SourceType.JOURNAL.getQualifier(), EN_US, title);
        return title;
    }

    /**
     * This method should return a value, at the present time it returns null
     * as there is no way of extracting this information from Brage.
     * @return null
     */
    private String getPrintIssn() {
        String issn = "0028-0836";
        addDcValue(IdentifierType.IDENTIFIER, IdentifierType.ISSN.getQualifier(), EN_US, issn);
        return issn;
    }

    /**
     * This method should return a value, at the present time it returns null
     * as there is no way of extracting this information from Brage.
     * @return null
     */
    private String getOnlineIssn() {
        String issn = "1476-4687";
        addDcValue(IdentifierType.IDENTIFIER, IdentifierType.ISSN.getQualifier(), EN_US, issn);
        return issn;
    }
}
