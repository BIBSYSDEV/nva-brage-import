package no.unit.nva.importbrage;

/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.dublincore.DublinCore;
import no.unit.nva.brage.nsddbhimiport.JournalParser;
import no.unit.nva.brage.nsddbhimiport.PublisherInfo;
import no.unit.nva.brage.testing.DcValueBuilder;
import no.unit.nva.brage.testing.DublinCoreBuilder;
import no.unit.nva.brage.testing.generator.TestPair;
import no.unit.nva.brage.testing.generator.TestPairGenerator;
import no.unit.nva.importbrage.metamodel.exceptions.AmbiguousDoiException;
import no.unit.nva.importbrage.metamodel.exceptions.ArticleNumberException;
import no.unit.nva.importbrage.metamodel.exceptions.BadOrcidException;
import no.unit.nva.importbrage.metamodel.exceptions.DoiException;
import no.unit.nva.importbrage.metamodel.exceptions.IllegalDateConversionException;
import no.unit.nva.importbrage.metamodel.exceptions.IncorrectlyFormattedSeriesInformationException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidPublicationDateException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.exceptions.IssuedDateException;
import no.unit.nva.importbrage.metamodel.exceptions.JournalIssueException;
import no.unit.nva.importbrage.metamodel.exceptions.JournalVolumeException;
import no.unit.nva.importbrage.metamodel.exceptions.LanguageException;
import no.unit.nva.importbrage.metamodel.exceptions.PageRangeException;
import no.unit.nva.importbrage.metamodel.exceptions.PagesException;
import no.unit.nva.importbrage.metamodel.exceptions.PublisherException;
import no.unit.nva.importbrage.metamodel.exceptions.SeriesTitleException;
import no.unit.nva.importbrage.metamodel.exceptions.SubmittedDateException;
import no.unit.nva.importbrage.metamodel.exceptions.TitleException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownLanguageException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownTypeMappingException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownTypeValueException;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.importbrage.metamodel.types.CoverageType;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;
import no.unit.nva.importbrage.metamodel.types.ElementType;
import no.unit.nva.importbrage.metamodel.types.Filterable;
import no.unit.nva.importbrage.metamodel.types.FormatType;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import no.unit.nva.importbrage.metamodel.types.LanguageType;
import no.unit.nva.importbrage.metamodel.types.ProvenanceType;
import no.unit.nva.importbrage.metamodel.types.PublisherType;
import no.unit.nva.importbrage.metamodel.types.RelationType;
import no.unit.nva.importbrage.metamodel.types.RightsType;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import no.unit.nva.importbrage.metamodel.types.SubjectType;
import no.unit.nva.importbrage.metamodel.types.TitleType;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import no.unit.nva.importbrage.metamodel.types.TypeValue;
import no.unit.nva.model.Level;
import no.unit.nva.model.Organization;
import no.unit.nva.model.exceptions.InvalidIsbnException;
import no.unit.nva.model.exceptions.InvalidIssnException;
import no.unit.nva.model.exceptions.MalformedContributorException;
import no.unit.nva.model.instancetypes.book.BookAnthology;
import no.unit.nva.model.instancetypes.book.BookMonograph;
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
import nva.commons.utils.log.LogUtils;
import nva.commons.utils.log.TestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Instant.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
*/
class XmlImportTest {
/*
    public static final String TEMP_FILE = "complete_record.xml";
    public static final String EN_US = "en_US";
    public static final String DELIMITER = ", ";
    public static TestAppender logger;
    public static final ObjectMapper mapper = new XmlMapper();

    @TempDir
    File testDirectory;
    private JournalParser mockJournalParser;

    private static Stream<Arguments> typeSupplier() {
        return Stream.of(
                Arguments.of(FeatureArticle.class),
                Arguments.of(JournalArticle.class),
                Arguments.of(JournalLeader.class),
                Arguments.of(JournalLetter.class),
                Arguments.of(JournalReview.class),
                Arguments.of(JournalShortCommunication.class),
                Arguments.of(BookAnthology.class),
                Arguments.of(BookMonograph.class),
                Arguments.of(DegreeBachelor.class),
                Arguments.of(DegreeMaster.class),
                Arguments.of(DegreePhd.class),
                Arguments.of(OtherStudentWork.class),
                Arguments.of(ReportBasic.class),
                Arguments.of(ReportResearch.class),
                Arguments.of(ReportWorkingPaper.class),
                Arguments.of(ReportPolicy.class)
        );
    }

    private static Stream<Arguments> generateFullRecordByType() throws UnknownRoleMappingException,
            MalformedContributorException, MalformedURLException, InvalidIssnException, InvalidIsbnException {
        return Stream.of(
                Arguments.of(new TestPairGenerator(BookMonograph.class).getTestPair()),
                Arguments.of(new TestPairGenerator(BookAnthology.class).getTestPair()),
                Arguments.of(new TestPairGenerator(DegreeBachelor.class).getTestPair()),
                Arguments.of(new TestPairGenerator(DegreeMaster.class).getTestPair()),
                Arguments.of(new TestPairGenerator(DegreePhd.class).getTestPair()),
                Arguments.of(new TestPairGenerator(OtherStudentWork.class).getTestPair()),
                Arguments.of(new TestPairGenerator(ReportBasic.class).getTestPair()),
                Arguments.of(new TestPairGenerator(ReportPolicy.class).getTestPair()),
                Arguments.of(new TestPairGenerator(ReportResearch.class).getTestPair()),
                Arguments.of(new TestPairGenerator(ReportWorkingPaper.class).getTestPair()),
                Arguments.of(new TestPairGenerator(FeatureArticle.class).getTestPair()),
                Arguments.of(new TestPairGenerator(JournalArticle.class).getTestPair()),
                Arguments.of(new TestPairGenerator(JournalReview.class).getTestPair()),
                Arguments.of(new TestPairGenerator(JournalLetter.class).getTestPair()),
                Arguments.of(new TestPairGenerator(JournalLeader.class).getTestPair()),
                Arguments.of(new TestPairGenerator(JournalShortCommunication.class).getTestPair())
            );
    }

    @BeforeEach
    void setup() {
        logger = LogUtils.getTestingAppender(XmlImport.class);
        mockJournalParser = mock(JournalParser.class);
        when(mockJournalParser.byIssn(anyString())).thenReturn(getMockPublisherInfo());
    }

    private PublisherInfo getMockPublisherInfo() {
        return new PublisherInfo("Nature",
                "0028-0836",
                "1476-4687",
                false,
                "Pants",
                "npi",
                Map.of("2020", Level.LEVEL_2),
                "PSA",
                "eng",
                "http://www.nature.com/nature/");
    }

    @ParameterizedTest(name = "XmlImport returns HTTP schema DOI when input is {0}")
    @ValueSource(strings = {"https://doi.org/10.000/fake-doi", "http://doi.org/10.000/fake-doi", "10.000/fake-doi"})
    void xmlImportReturnsHttpDoiWhenInputIsDoi(String doi) throws IOException, IssuedDateException,
            InvalidPublicationDateException, UnknownLanguageException, MalformedContributorException, LanguageException,
            IllegalDateConversionException, TitleException, UnknownRoleMappingException,
            BadOrcidException {
        var expected = doi.startsWith("https")
                ? URI.create(doi)
                : URI.create("https://doi.org/" + doi.replace("http://doi.org/", ""));
        var xmlImport = new XmlImport(mockJournalParser);
        var publication = xmlImport.map(generateXmlFile(getBook(doi)));
        var actualDoi = publication.getEntityDescription().getReference().getDoi();
        assertThat(actualDoi, equalTo(expected));
    }

    @ParameterizedTest(name = "XmlImport throws {0} when input is {1}")
    @MethodSource("exceptionProvider")
    void xmlImportLogsException(DublinCore dublinCore, String expectedError) throws IOException, IssuedDateException,
            InvalidPublicationDateException, UnknownLanguageException, MalformedContributorException, LanguageException,
            IllegalDateConversionException, TitleException, UnknownRoleMappingException,
            BadOrcidException {
        var xmlImport = new XmlImport(mockJournalParser);
        var actual = xmlImport.map(generateXmlFile(dublinCore));
        assertNull(actual);
        var errors = String.join(DELIMITER, xmlImport.getErrors());
        assertThat(errors, containsString(expectedError));
    }

    private static Stream<Arguments> exceptionProvider() {
        var badValue = "twigs";
        var dois = List.of("https://doi.org/10.000/cake", "https://doi.org/10.000/sandwich");
        var articleNumbers = List.of("hello", "goodbye");
        var seriesData = "Not a series-12";
        var pageRange = "k*s";
        var pagesRange = List.of("1-2", "7-9");
        var creatorQualifier = "author";
        var name = "Stevenson, Steve";
        var languageTwoLetter = "01";
        var languageThreeLetter = "010";
        var languageFourLetter = "nobo";
        var languagesTwoLetter = List.of("nb", "nn");
        var languagesThreeLetter = List.of("nob", "nno");
        var languagesFourLetter = List.of("nobo", "nnor");
        var pages = List.of("123", "224");
        var issuedDates = List.of("2001-09-01", "2020-02-03");
        var seriesInfo = List.of("A series;1", "A series;2");
        var publishers = List.of("A publisher", "B publisher");
        var titles = List.of("A title", "B title");
        var volumes = List.of("1", "2");
        var issues = List.of("2", "3");
        var nonUriDoi = "https://example.org/10.000/fake-doi";
        var contributorType = ContributorType.OTHER;

        return Stream.of(
                Arguments.of(invalidTypeValue(badValue), unknownTypeError(badValue)),
                Arguments.of(invalidOrcidValue(badValue), badOrcidError(badValue)),
                Arguments.of(getDublinCoreWithMultipleDois(dois), ambiguousDoiError(dois)),
                Arguments.of(getDublinCoreWithNonDoiUri(nonUriDoi), doiError(nonUriDoi)),
                Arguments.of(invalidArticleNumberException(articleNumbers), articleNumberError(articleNumbers)),
                Arguments.of(incorrectlyFormattedSeries(seriesData), incorrectlyFormattedSeriesError(seriesData)),
                Arguments.of(incorrectlyFormattedPageRange(pageRange), incorrectlyFormattedPageRangeError(pageRange)),
                Arguments.of(tooManyPageRanges(pagesRange), tooManyPageRangesError(pagesRange)),
                Arguments.of(qualifiedCreator(creatorQualifier, name), qualifiedCreatorError(creatorQualifier, name)),
                Arguments.of(unknownLanguage(languageTwoLetter), unknownLanguageError(languageTwoLetter)),
                Arguments.of(unknownLanguage(languageThreeLetter), unknownLanguageError(languageThreeLetter)),
                Arguments.of(unknownLanguage(languageFourLetter), unknownLanguageError(languageFourLetter)),
                Arguments.of(multipleLanguages(languagesTwoLetter), languageError(languagesTwoLetter)),
                Arguments.of(multipleLanguages(languagesThreeLetter), languageError(languagesThreeLetter)),
                Arguments.of(multipleLanguages(languagesFourLetter), languageError(languagesFourLetter)),
                Arguments.of(badlyFormattedPagesInFormat(pages), pagesError(pages)),
                Arguments.of(multipleIssuedDates(issuedDates), issuedDateError(issuedDates)),
                Arguments.of(multipleSubmittedDates(issuedDates), submittedDateError(issuedDates)),
                Arguments.of(multipleSeriesTitles(seriesInfo), seriesTitleError(seriesInfo)),
                Arguments.of(multiplePublishers(publishers), publisherError(publishers)),
                Arguments.of(multipleTitles(titles), titleError(titles)),
                Arguments.of(missingTitle(), titleError()),
                Arguments.of(multipleJournalVolumes(volumes), journalVolumeError(volumes)),
                Arguments.of(multipleJournalIssues(issues), journalIssueError(issues)),
                Arguments.of(unknownRoleNvaOther(contributorType, name), unknownRoleError(contributorType, name))
        );
    }

    private static String unknownRoleError(ContributorType role, String name) {
        return String.format(UnknownRoleMappingException.MESSAGE_TEMPLATE, role.getQualifier(), name);
    }

    private static DublinCore unknownRoleNvaOther(ContributorType role, String value) {
        var dcValues = List.of(getType(TypeValue.BOOK), getContributor(role, value));
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static String doiError(String nonUriDoi) {
        return String.format(DoiException.MESSAGE_TEMPLATE, nonUriDoi);
    }

    private static String journalIssueError(List<String> issues) {
        return String.format(JournalIssueException.MESSAGE_TEMPLATE, String.join(DELIMITER, issues));
    }

    private static DublinCore multipleJournalIssues(List<String> issues) {
        return getDublinCore(issues, SourceType.SOURCE, SourceType.ISSUE, TypeValue.JOURNAL_ARTICLE);
    }

    private static String journalVolumeError(List<String> volumes) {
        return String.format(JournalVolumeException.MESSAGE_TEMPLATE, String.join(DELIMITER, volumes));
    }

    private static DublinCore multipleJournalVolumes(List<String> volumes) {
        return getDublinCore(volumes, SourceType.SOURCE, SourceType.VOLUME, TypeValue.JOURNAL_ARTICLE);
    }

    private static DublinCore missingTitle() {
        var dcValue = new DcValueBuilder()
                .withElement(TypeBasic.TYPE)
                .withQualifier(TypeBasic.UNQUALIFIED.getQualifier())
                .withValue(TypeValue.BOOK.getTypeName())
                .withLanguage(EN_US)
                .build();

        return new DublinCoreBuilder()
                .withDcValues(List.of(dcValue))
                .withSchema("dc")
                .build();
    }

    private static String titleError() {
        return TitleException.MESSAGE_TOO_FEW;
    }

    private static String titleError(List<String> titles) {
        return String.format(TitleException.MESSAGE_TEMPLATE_TOO_MANY, String.join(DELIMITER, titles));
    }

    private static DublinCore multipleTitles(List<String> titles) {
        return getDublinCore(titles, TitleType.TITLE, TitleType.UNQUALIFIED, TypeValue.BOOK);
    }

    private static String publisherError(List<String> publishers) {
        return String.format(PublisherException.MESSAGE_TEMPLATE, String.join(DELIMITER, publishers));
    }

    private static DublinCore multiplePublishers(List<String> publishers) {
        return getDublinCore(publishers, PublisherType.PUBLISHER, PublisherType.UNQUALIFIED, TypeValue.BOOK);
    }

    private static String seriesTitleError(List<String> seriesInfo) {
        return String.format(SeriesTitleException.MESSAGE_TEMPLATE, String.join(DELIMITER, seriesInfo));
    }

    private static DublinCore multipleSeriesTitles(List<String> seriesInfo) {
        return getDublinCore(seriesInfo, RelationType.RELATION, RelationType.IS_PART_OF_SERIES, TypeValue.BOOK);
    }

    private static String submittedDateError(List<String> submittedDates) {
        return String.format(SubmittedDateException.MESSAGE_TEMPLATE, String.join(DELIMITER, submittedDates));
    }

    private static DublinCore multipleSubmittedDates(List<String> submittedDates) {
        return getDublinCore(submittedDates, DateType.DATE, DateType.SUBMITTED, TypeValue.BACHELOR_THESIS);
    }

    private static String issuedDateError(List<String> issuedDates) {
        return String.format(IssuedDateException.MESSAGE_TEMPLATE, String.join(DELIMITER, issuedDates));
    }

    private static DublinCore multipleIssuedDates(List<String> issuedDates) {
        return getDublinCore(issuedDates, DateType.DATE, DateType.ISSUED, TypeValue.BOOK);
    }

    private static String pagesError(List<String> pages) {
        return String.format(PagesException.MESSAGE_TEMPLATE, String.join(DELIMITER, pages));
    }

    private static DublinCore badlyFormattedPagesInFormat(List<String> pages) {
        return getDublinCore(pages, SourceType.SOURCE, SourceType.PAGE_NUMBER, TypeValue.BOOK);
    }

    private static DublinCore getDublinCore(List<String> values, String type, ElementType elementType,
                                            TypeValue typeValue) {
        var dcValues = values.stream()
                .map(value -> getDcValueSingleton(type,
                        elementType.getQualifier(),
                        value))
                .collect(Collectors.toList());
        dcValues.add(getDcValueSingleton(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(),
                typeValue.getTypeName()));
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static String languageError(List<String> languages) {
        return String.format(LanguageException.MESSAGE_TEMPLATE, String.join(DELIMITER, languages));
    }

    private static DublinCore multipleLanguages(List<String> languages) {
        var dcValues = languages.stream()
                .map(language -> getDcValueSingleton(LanguageType.LANGUAGE,
                        LanguageType.ISO.getQualifier(),
                        language))
                .collect(Collectors.toList());
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static String unknownLanguageError(String language) {
        return String.format(UnknownLanguageException.MESSAGE_TEMPLATE, language);
    }

    private static DublinCore unknownLanguage(String language) {
        var dcValue = new DcValueBuilder()
                .withElement(LanguageType.LANGUAGE)
                .withQualifier(LanguageType.ISO.getQualifier())
                .withLanguage(EN_US)
                .withValue(language)
                .build();
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(List.of(dcValue))
                .build();
    }

    private static String qualifiedCreatorError(String qualifier, String name) {
        var allowedValues = ElementType.getAllowedValues(CreatorType.values());
        return String.format(InvalidQualifierException.MESSAGE_TEMPLATE, CreatorType.CREATOR, name, qualifier,
                allowedValues);
    }

    private static DublinCore qualifiedCreator(String qualifier, String name) {
        var dcValue = new DcValueBuilder()
                .withElement(CreatorType.CREATOR)
                .withQualifier(qualifier)
                .withLanguage(EN_US)
                .withValue(name)
                .build();
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(List.of(dcValue))
                .build();
    }

    private static String tooManyPageRangesError(List<String> pageRanges) {
        return String.format(PageRangeException.MESSAGE_TEMPLATE_TOO_MANY, String.join(DELIMITER, pageRanges));
    }

    private static DublinCore tooManyPageRanges(List<String> pageRanges) {
        return getDublinCore(pageRanges, SourceType.SOURCE, SourceType.PAGE_NUMBER, TypeValue.JOURNAL_ARTICLE);
    }

    private static String incorrectlyFormattedPageRangeError(String pageRange) {
        return String.format(PageRangeException.MESSAGE_TEMPLATE_BADLY_STRUCTURED, pageRange);
    }

    private static DublinCore incorrectlyFormattedPageRange(String pageRange) {
        var dcValue = getDcValueSingleton(SourceType.SOURCE,
                SourceType.PAGE_NUMBER.getQualifier(),
                pageRange);
        var dcValues = new ArrayList<DcValue>();
        dcValues.add(dcValue);
        dcValues.add(getDcValueSingleton(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(),
                TypeValue.JOURNAL_ARTICLE.getTypeName()));
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static String incorrectlyFormattedSeriesError(String seriesData) {
        return String.format(IncorrectlyFormattedSeriesInformationException.MESSAGE_TEMPLATE, seriesData);
    }

    private static DublinCore incorrectlyFormattedSeries(String seriesData) {
        var dcValues = new ArrayList<DcValue>();
        dcValues.add(getDcValueSingleton(RelationType.RELATION, RelationType.IS_PART_OF_SERIES.getQualifier(),
                seriesData));
        dcValues.add(getDcValueSingleton(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(),
                TypeValue.BOOK.getTypeName()));
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static String articleNumberError(List<String> articleNumbers) {
        return String.format(ArticleNumberException.MESSAGE_TEMPLATE, String.join(DELIMITER, articleNumbers));
    }

    private static DublinCore invalidArticleNumberException(List<String> articleNumbers) {
        return getDublinCore(articleNumbers, SourceType.SOURCE, SourceType.ARTICLE_NUMBER, TypeValue.JOURNAL_ARTICLE);
    }

    private static String ambiguousDoiError(List<String> dois) {
        return String.format(AmbiguousDoiException.MESSAGE_TEMPLATE, String.join(DELIMITER, dois));
    }

    private static String badOrcidError(String badValue) {
        return String.format(BadOrcidException.MESSAGE_TEMPLATE, badValue);
    }

    private static DublinCore invalidOrcidValue(String badValue) {
        var dcValue = getContributor(ContributorType.ORCID, badValue);
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(List.of(dcValue))
                .build();
    }

    private static String unknownTypeError(String unknownType) {
        return String.format(UnknownTypeValueException.MESSAGE_TEMPLATE, unknownType,
                String.join(DELIMITER, Filterable.getAllowedValues(TypeValue.values())));
    }

    private static DublinCore invalidTypeValue(String unknownType) {
        var dcValue = new DcValueBuilder()
                .withElement(TypeBasic.TYPE)
                .withQualifier(TypeBasic.UNQUALIFIED.getQualifier())
                .withLanguage(EN_US)
                .withValue(unknownType)
                .build();
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(List.of(dcValue))
                .build();
    }

    @ParameterizedTest(name = "Transform Dublin core for type {0}")
    @MethodSource("typeSupplier")
    void xmlImportTransformsDublinCoreToPublication(Class<?> type) throws MalformedContributorException, IOException,
            InvalidIssnException, InvalidIsbnException, UnknownRoleMappingException, BadOrcidException,
            UnknownLanguageException, LanguageException, InvalidPublicationDateException,
            IllegalDateConversionException, IssuedDateException, TitleException {
        var testPair = new TestPairGenerator(type).getTestPair();
        var dublinCore = testPair.getDublinCore();
        var file = generateXmlFile(dublinCore);
        var xmlImport = new XmlImport(mockJournalParser);
        var actual = xmlImport.map(file);
        var expected = testPair.getNvaPublication();
        assert actual != null;
        addObligatoryValues(actual, expected);
        assertThat(actual, equalTo(expected));
    }

    private void addObligatoryValues(no.unit.nva.model.Publication actual, no.unit.nva.model.Publication expected) {
        var now = now();
        actual.setOwner("ha");
        expected.setOwner("ha");
        actual.setModifiedDate(now);
        expected.setModifiedDate(now);
        var org = new Organization.Builder()
                .withId(URI.create("https://example.org"))
                .build();
        actual.setPublisher(org);
        expected.setPublisher(org);
    }

    @Test
    void xmlImportIgnoresCreatorWhenContributorWithSameValueIsPresent() throws UnknownRoleMappingException,
            MalformedContributorException, IOException, InvalidIssnException, InvalidIsbnException,
            InvalidPublicationDateException, IllegalDateConversionException, LanguageException, TitleException,
            UnknownLanguageException, IssuedDateException, BadOrcidException {
        var testPair = new TestPairGenerator(BookMonograph.class).getTestPairWithCreator(true);
        var dublinCore = testPair.getDublinCore();
        var file = generateXmlFile(dublinCore);
        var xmlImport = new XmlImport(mockJournalParser);
        var actual = xmlImport.map(file);
        var expected = testPair.getNvaPublication();
        assert actual != null;
        addObligatoryValues(actual, expected);
        assertThat(actual, equalTo(expected));
    }

    @Test
    void xmlImportRetainsCreatorWhenContributorWithDifferentValueIsPresent() throws UnknownRoleMappingException,
            MalformedContributorException, IOException, InvalidIssnException, InvalidIsbnException,
            InvalidPublicationDateException, IllegalDateConversionException, LanguageException, TitleException,
            UnknownLanguageException, IssuedDateException, BadOrcidException {
        var testPair = new TestPairGenerator(BookMonograph.class).getTestPairWithCreator(false);
        var dublinCore = testPair.getDublinCore();
        var file = generateXmlFile(dublinCore);
        var xmlImport = new XmlImport(mockJournalParser);
        var actual = xmlImport.map(file);
        var expected = testPair.getNvaPublication();
        assert actual != null;
        addObligatoryValues(actual, expected);
        assertThat(actual, equalTo(expected));
    }

    @ParameterizedTest(name = "Error is reported when element {0} has qualifier {1}")
    @MethodSource("elementAndQualifierSupplier")
    void xmlImportReturnsNullAndErrorsOccurInErrorsList(DublinCore dublinCore, String expectedError) throws IOException,
            IssuedDateException, InvalidPublicationDateException, UnknownLanguageException,
            MalformedContributorException, LanguageException, IllegalDateConversionException, TitleException,
            UnknownRoleMappingException, BadOrcidException {
        var xmlImport = new XmlImport(mockJournalParser);
        var file = generateXmlFile(dublinCore);
        var actual = xmlImport.map(file);
        assertNull(actual);
        var errors = String.join(DELIMITER, xmlImport.getErrors());
        assertThat(errors, containsString(expectedError));
    }

    @ParameterizedTest(name = "Logs unknown mapping for {0}")
    @MethodSource("unknownTypeSupplier")
    void xmlImportReturnsNullAndLogsUnknownMappingAndMissingTitle(TypeValue type) throws IOException, UnknownRoleMappingException,
            MalformedContributorException, InvalidPublicationDateException,
            IllegalDateConversionException, LanguageException, TitleException, UnknownLanguageException,
            IssuedDateException, BadOrcidException {
        var dublinCore = getDublinCoreSingleton(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(),
                type.getTypeName());
        var file = generateXmlFile(dublinCore);
        var xmlImport = new XmlImport(mockJournalParser);
        var actual = xmlImport.map(file);
        assertNull(actual);
        String expectedTypeError = String.format(UnknownTypeMappingException.MESSAGE_TEMPLATE, type.getTypeName());
        String errors = String.join(DELIMITER, xmlImport.getErrors());
        assertThat(errors, containsString(expectedTypeError));
        var titleError = TitleException.MESSAGE_TOO_FEW;
        assertThat(errors, containsString(titleError));
    }

    @ParameterizedTest(name = "Valid fully specified {0} passes")
    @MethodSource("generateFullRecordByType")
    void xmlImportReturnsPublicationBookWhenInputIsCompleteAndValid(TestPair testPair) throws IOException, IssuedDateException, InvalidPublicationDateException, UnknownLanguageException, MalformedContributorException, LanguageException, IllegalDateConversionException, TitleException, UnknownRoleMappingException, BadOrcidException {
        var file = generateXmlFile(testPair.getDublinCore());
        var xmlImport = new XmlImport(mockJournalParser);
        var actual = xmlImport.map(file);
        assertThat(actual, equalTo(testPair.getNvaPublication()));
    }

    private DublinCore getBook(String doi) {
        var dcValues = new ArrayList<DcValue>();
        dcValues.add(getType(TypeValue.BOOK));
        dcValues.add(getType(TypeValue.PEER_REVIEWED));
        dcValues.add(getTitle(TitleType.UNQUALIFIED, "A title"));
        dcValues.add(getTitle(TitleType.ALTERNATIVE, "Eit alternativtittel"));
        dcValues.add(getContributor(ContributorType.AUTHOR, "Herriss, Herry"));
        dcValues.add(getContributor(ContributorType.ADVISOR, "Berriss, Berry"));
        dcValues.add(getContributor(ContributorType.ILLUSTRATOR, "Derriss, Derry"));
        dcValues.add(getCoverage(CoverageType.SPATIAL, "Norway"));
        dcValues.add(getCoverage(CoverageType.TEMPORAL, "1900s"));
        dcValues.add(getDate(DateType.ISSUED, "2015-05-01"));
        dcValues.add(getDate(DateType.ACCESSIONED, "2015-05-01T10:10:10Z"));
        dcValues.add(getDate(DateType.AVAILABLE, "2015-05-01T10:10:10Z"));
        dcValues.add(getDate(DateType.CREATED, "2015-05-01T10:10:10Z"));
        dcValues.add(getDescription(DescriptionType.ABSTRACT, "An abstract"));
        dcValues.add(getDescription(DescriptionType.UNQUALIFIED, "A description"));
        dcValues.add(getFormat(FormatType.EXTENT, "2225"));
        dcValues.add(getIdentifier(IdentifierType.DOI, doi));
        dcValues.add(getIdentifier(IdentifierType.ISBN, "978-3-16-148410-0"));
        dcValues.add(getLanguage("eng"));
        dcValues.add(getPublisher("Stephen's publishing LLD"));
        dcValues.add(getSeries("Stephen's series;2"));
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private DcValue getSeries(String series) {
        return new DcValueBuilder()
                .withElement(RelationType.RELATION)
                .withQualifier(RelationType.IS_PART_OF_SERIES.getQualifier())
                .withLanguage(EN_US)
                .withValue(series)
                .build();
    }

    private DcValue getPublisher(String publisher) {
        return new DcValueBuilder()
                .withElement(PublisherType.PUBLISHER)
                .withQualifier(PublisherType.UNQUALIFIED.getQualifier())
                .withLanguage(EN_US)
                .withValue(publisher)
                .build();
    }

    private DcValue getLanguage(String value) {
        return new DcValueBuilder()
                .withElement(LanguageType.LANGUAGE)
                .withQualifier(LanguageType.ISO.getQualifier())
                .withLanguage(EN_US)
                .withValue(value)
                .build();
    }

    private static DcValue getIdentifier(IdentifierType doi, String s) {
        return new DcValueBuilder()
                .withElement(IdentifierType.IDENTIFIER)
                .withQualifier(doi.getQualifier())
                .withLanguage(EN_US)
                .withValue(s)
                .build();
    }

    private DcValue getFormat(FormatType formatType, String value) {
        return new DcValueBuilder()
                .withElement(FormatType.FORMAT)
                .withQualifier(formatType.getQualifier())
                .withLanguage(EN_US)
                .withValue(value)
                .build();
    }

    private DcValue getDescription(DescriptionType anAbstract, String s) {
        return new DcValueBuilder()
                .withElement(DescriptionType.DESCRIPTION)
                .withQualifier(anAbstract.getQualifier())
                .withLanguage(EN_US)
                .withValue(s)
                .build();
    }

    private DcValue getDate(DateType issued, String s) {
        return new DcValueBuilder()
                .withElement(DateType.DATE)
                .withQualifier(issued.getQualifier())
                .withLanguage(EN_US)
                .withValue(s)
                .build();
    }

    private DcValue getCoverage(CoverageType spatial, String norway) {
        return new DcValueBuilder()
                .withElement(CoverageType.COVERAGE)
                .withQualifier(spatial.getQualifier())
                .withLanguage(EN_US)
                .withValue(norway)
                .build();
    }

    private static DcValue getContributor(ContributorType contributorType, String name) {
        return new DcValueBuilder()
                .withElement(ContributorType.CONTRIBUTOR)
                .withQualifier(contributorType.getQualifier())
                .withLanguage(EN_US)
                .withValue(name)
                .build();
    }

    private static DcValue getTitle(TitleType unqualified, String s) {
        return new DcValueBuilder()
                .withElement(TitleType.TITLE)
                .withQualifier(unqualified.getQualifier())
                .withLanguage(EN_US)
                .withValue(s)
                .build();
    }

    private static DcValue getType(TypeValue book) {
        return new DcValueBuilder()
                .withElement(TypeBasic.TYPE)
                .withQualifier(TypeBasic.UNQUALIFIED.getQualifier())
                .withLanguage(EN_US)
                .withValue(book.getTypeName())
                .build();
    }

    private static DublinCore getDublinCoreWithMultipleDois(List<String> dois) {
        var dcValues = dois.stream()
                .map(XmlImportTest::generateDoiDcValue)
                .collect(Collectors.toList());
        dcValues.add(anyType());
        dcValues.add(anyTitle());
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static DublinCore getDublinCoreWithNonDoiUri(String doi) {
        var dcValues = new ArrayList<>(List.of(generateDoiDcValue(doi)));
        dcValues.add(anyTitle());
        dcValues.add(anyType());
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(dcValues)
                .build();
    }

    private static DcValue anyTitle() {
        return getTitle(TitleType.UNQUALIFIED, "Irrelevant");
    }

    private static DcValue anyType() {
        return getType(TypeValue.BACHELOR_THESIS);
    }

    private static DcValue generateDoiDcValue(String doi) {
        return getIdentifier(IdentifierType.DOI, doi);
    }

    private static Stream<Arguments> unknownTypeSupplier() {
        return Stream.of(
                Arguments.of(TypeValue.ARTISTIC_PRODUCTION),
                Arguments.of(TypeValue.CONFERENCE_OBJECT),
                Arguments.of(TypeValue.ISSUE),
                Arguments.of(TypeValue.LEARNING_OBJECT),
                Arguments.of(TypeValue.LECTURE)
        );
    }

    private static Stream<Arguments> elementAndQualifierSupplier() {

        String irrelevant = "irrelevant";
        String nonsense = "nonsense";
        return Stream.of(
                Arguments.of(getDublinCoreSingleton(CreatorType.CREATOR, nonsense, irrelevant),
                        generateFormattedException(CreatorType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(ContributorType.CONTRIBUTOR, nonsense, irrelevant),
                        generateFormattedException(ContributorType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(CoverageType.COVERAGE, nonsense, irrelevant),
                        generateFormattedException(CoverageType.SPATIAL, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(DateType.DATE, nonsense, irrelevant),
                        generateFormattedException(DateType.SUBMITTED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(DescriptionType.DESCRIPTION, nonsense, irrelevant),
                        generateFormattedException(DescriptionType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(FormatType.FORMAT, nonsense, irrelevant),
                        generateFormattedException(FormatType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(IdentifierType.IDENTIFIER, nonsense, irrelevant),
                        generateFormattedException(IdentifierType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(LanguageType.LANGUAGE, nonsense, irrelevant),
                        generateFormattedException(LanguageType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(ProvenanceType.PROVENANCE, nonsense, irrelevant),
                        generateFormattedException(ProvenanceType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(PublisherType.PUBLISHER, nonsense, irrelevant),
                        generateFormattedException(PublisherType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(RelationType.RELATION, nonsense, irrelevant),
                        generateFormattedException(RelationType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(RightsType.RIGHTS, nonsense, irrelevant),
                        generateFormattedException(RightsType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(SourceType.SOURCE, nonsense, irrelevant),
                        generateFormattedException(SourceType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(SubjectType.SUBJECT, nonsense, irrelevant),
                        generateFormattedException(SubjectType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(TitleType.TITLE, nonsense, irrelevant),
                        generateFormattedException(TitleType.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(TypeBasic.TYPE, nonsense, irrelevant),
                        generateFormattedException(TypeBasic.UNQUALIFIED, nonsense, irrelevant)),
                Arguments.of(getDublinCoreSingleton(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(), nonsense),
                        unknownTypeError(nonsense))
        );
    }

    private static String generateUnknownTypeException(String value) {
        return String.format(UnknownTypeMappingException.MESSAGE_TEMPLATE, value);
    }

    private static String generateFormattedException(ElementType type, String qualifier, String value) {
        return String.format(InvalidQualifierException.MESSAGE_TEMPLATE, type.getName(), value, qualifier,
                ElementType.getAllowedValues(type.getValues()));
    }

    private static DcValue getDcValueSingleton(String type, String qualifier, String value) {
        return new DcValueBuilder()
                .withElement(type)
                .withQualifier(qualifier)
                .withLanguage(EN_US)
                .withValue(value)
                .build();
    }

    private static DublinCore getDublinCoreSingleton(String type, String qualifier, String value) {
        var qualifiedDcCreator = new DcValueBuilder()
                .withElement(type)
                .withQualifier(qualifier)
                .withLanguage(EN_US)
                .withValue(value)
                .build();
        var dublinCore = new DublinCore();
        dublinCore.setDcValues(List.of(qualifiedDcCreator));
        return dublinCore;
    }

    private File getTemporaryFile() {
        return new File(testDirectory, TEMP_FILE);
    }

    public void writeXmlFile(File file, DublinCore dublinCore) throws IOException {
        mapper.writeValue(file, dublinCore);
    }

    private File generateXmlFile(DublinCore key) throws IOException {
        var file = getTemporaryFile();
        writeXmlFile(file, key);
        return file;
    }
    */
}