package no.unit.nva.importbrage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCoverage;
import no.unit.nva.importbrage.metamodel.BrageCreator;
import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BrageDescription;
import no.unit.nva.importbrage.metamodel.BrageFormat;
import no.unit.nva.importbrage.metamodel.BrageIdentifier;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.importbrage.metamodel.types.CoverageType;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;
import no.unit.nva.importbrage.metamodel.types.FormatType;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import nva.commons.utils.log.LogUtils;
import nva.commons.utils.log.TestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException.MESSAGE_TEMPLATE;
import static no.unit.nva.importbrage.metamodel.types.FormatType.FORMAT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class XmlImportTest {

    public static final String TEMP_FILE = "complete_record.xml";
    public static final String EXAMPLE_URI = "https://example.org/article/1";
    public static final String CONTRIBUTOR = "contributor";
    public static final String RANDY_OLSON = "Randy Olson";
    public static final String COVERAGE = "coverage";
    public static final String NORWAY = "Norway";
    public static final String DATE = "date";
    public static final String ANY_DATE = "2020-02-01";
    public static final String IDENTIFIER = "identifier";
    private static final String EN_US = "en_US";
    public static final String DC = "dc";
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION_STRING = "Something resembling a description!";
    public static final String CREATOR = "creator";
    public static final String DELIMITER = ", ";
    public static final String NONSENSE = "nonsense";
    public static final String ANY_FORMAT = "123-300";
    public static TestAppender logger;
    public static final ObjectMapper mapper = new XmlMapper();

    @TempDir
    File testDirectory;

    @BeforeEach
    void setup() {
        logger = LogUtils.getTestingAppender(XmlImport.class);
    }

    @Test
    void xmlImportLoadsData() throws IOException {
        var testData = Map.of(
                ContributorType.AUTHOR, RANDY_OLSON,
                CoverageType.SPATIAL, NORWAY,
                CreatorType.UNQUALIFIED, RANDY_OLSON,
                DateType.ACCESSIONED, ANY_DATE,
                IdentifierType.URI, EXAMPLE_URI,
                DescriptionType.ABSTRACT, "A long descriptive text that stands as a description",
                FormatType.EXTENT, "232");
        var testPair = generateTestPair(testData);
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @Test
    void xmlImportReturnsNullOnUnknownTypes() throws IOException {
        var testValues = generateDcValuesWithUnknownType();
        var file = getTemporaryFile();
        writeXmlFile(file, testValues);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
    }

    @Test
    void xmlImportLogsErrorWhenDcValueHasUnknownType() throws IOException {
        var testValues = generateDcValuesWithUnknownType();
        var file = getTemporaryFile();
        writeXmlFile(file, testValues);
        var xmlImport = new XmlImport();
        xmlImport.map(file);
        assertThat(logger.getMessages(), containsString(file.getAbsolutePath()));
    }

    @Test
    void xmlImportCreatesErrorListWhenDcValueHasUnknownType() throws IOException {
        var testValues = generateDcValuesWithUnknownType();
        var xmlImport = new XmlImport();
        var file = getTemporaryFile();
        writeXmlFile(file, testValues);
        xmlImport.map(file);
        var errors = xmlImport.getErrors();
        assertThat(errors, is(not(empty())));
    }

    @ParameterizedTest(name = "XmlImport allows Description qualified type {0}")
    @EnumSource(DescriptionType.class)
    void xmlImportGeneratesBragePublicationWhenDescriptionsArePresent(DescriptionType type) throws IOException {
        var testPair = generateTestPair(Map.of(type, DESCRIPTION_STRING));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @ParameterizedTest(name = "XmlImport allows Identifier qualified type {0}")
    @EnumSource(IdentifierType.class)
    void xmlImportGeneratesBragePublicationWhenIdentifiersArePresent(IdentifierType type) throws IOException {
        var testPair = generateTestPair(Map.of(type, NORWAY));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @ParameterizedTest(name = "XmlImport allows Contributor qualified type {0}")
    @EnumSource(ContributorType.class)
    void xmlImportGeneratesBragePublicationWhenContributorsArePresent(ContributorType type) throws IOException {
        var testPair = generateTestPair(Map.of(type, NORWAY));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @ParameterizedTest(name = "XmlImport allows Format qualified type {0}")
    @EnumSource(FormatType.class)
    void xmlImportGeneratesBragePublicationWhenFormatsArePresent(FormatType type) throws IOException {
        var testPair = generateTestPair(Map.of(type, ANY_FORMAT));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @ParameterizedTest(name = "XmlImport allows Coverage qualified type {0}")
    @EnumSource(CoverageType.class)
    void xmlImportGeneratesBragePublicationWhenCoveragePresent(CoverageType type) throws IOException {
        var testPair = generateTestPair(Map.of(type, NORWAY));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @ParameterizedTest(name = "XmlImport disallows unqualified Coverage for \"{0}\"")
    @NullAndEmptySource
    void xmlImportReportsBragePublicationWhenCoverageIsUnqualified(String type) throws IOException {
        var dublinCore = generateDublinCoreWithQualifierForElement(COVERAGE, type);
        var file = getTemporaryFile();
        writeXmlFile(file, dublinCore);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        var actual = String.join(DELIMITER, xmlImport.getErrors());
        String expectedMessage = String.format(MESSAGE_TEMPLATE, COVERAGE, type, CoverageType.getAllowedValues());
        assertThat(actual, containsString(expectedMessage));
    }

    @ParameterizedTest(name = "XmlImport creates report when qualifier is unknown for {0}")
    @ValueSource(strings = {CONTRIBUTOR, COVERAGE, CREATOR, DATE, DESCRIPTION, FORMAT, IDENTIFIER})
    void xmlImportReportsBragePublicationWhenQualifierIsUnknown(String type) throws IOException {
        var dublinCore = generateDublinCoreWithQualifierForElement(type, NONSENSE);
        var file = getTemporaryFile();
        writeXmlFile(file, dublinCore);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        var actual = String.join(DELIMITER, xmlImport.getErrors());
        String expectedMessage = String.format(MESSAGE_TEMPLATE, type, NONSENSE, getAllowedValues(type));
        assertThat(actual, containsString(expectedMessage));
    }

    private String getAllowedValues(String type) {
        switch (type) {
            case CONTRIBUTOR:
                return ContributorType.getAllowedValues();
            case COVERAGE:
                return CoverageType.getAllowedValues();
            case CREATOR:
                return CreatorType.getAllowedValues();
            case DATE:
                return DateType.getAllowedValues();
            case DESCRIPTION:
                return DescriptionType.getAllowedValues();
            case FORMAT:
                return FormatType.getAllowedValues();
            case IDENTIFIER:
                return IdentifierType.getAllowedValues();
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    @ParameterizedTest(name = "XmlImport allows Date qualified type {0}")
    @EnumSource(DateType.class)
    void xmlImportGeneratesBragePublicationWhenDatesArePresent(DateType type) throws IOException {
        var testPair = generateTestPair(Map.of(type, ANY_DATE));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNotNull(publication);
        assertThat(publication, is(equalTo(testPair.getValue())));
    }

    @Test
    void xmlImportGeneratesBragePublicationWhenCreatorIsUnqualified() throws IOException {
        var testPair = generateTestPair(Map.of(CreatorType.UNQUALIFIED, RANDY_OLSON));
        var file = getTemporaryFile();
        writeXmlFile(file, testPair.getKey());
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNotNull(publication);
        assertThat(publication, is(equalTo(testPair.getValue())));
    }

    @Test
    void xmlImportReportsErrorsWhenCreatorIsQualified() throws IOException {
        var testPair = generateDublinCoreWithQualifierForElement(CREATOR, NONSENSE);
        var file = getTemporaryFile();
        writeXmlFile(file, testPair);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        var actual = String.join(DELIMITER, xmlImport.getErrors());
        var expected = String.format(MESSAGE_TEMPLATE, CREATOR, NONSENSE, CreatorType.getAllowedValues());
        assertThat(actual, containsString(expected));
    }

    private DublinCore generateDublinCoreWithQualifierForElement(String element, String qualifier) {
        var coverage = new DcValueBuilder()
                .withQualifier(qualifier)
                .withElement(element)
                .withValue("irrelevant")
                .withLanguage(EN_US)
                .build();
        return new DublinCoreBuilder()
                .withSchema("dc")
                .withDcValues(List.of(coverage))
                .build();
    }

    private File getTemporaryFile() {
        return new File(testDirectory, TEMP_FILE);
    }

    private DublinCore generateDcValuesWithUnknownType() {
        var dcValue = new DcValueBuilder()
                .withElement(NONSENSE)
                .withQualifier("yet more nonsense")
                .withLanguage(null)
                .withValue("even more nonsense")
                .build();
        return new DublinCoreBuilder()
                .withSchema(DC)
                .withDcValues(List.of(dcValue))
                .build();
    }

    public void writeXmlFile(File file, DublinCore dublinCore) throws IOException {
        mapper.writeValue(file, dublinCore);
    }

    private AbstractMap.SimpleEntry<DublinCore, BragePublication> generateTestPair(
            Map<? extends Enum<? extends Enum<?>>, String> data) {
        List<DcValue> dcValues = new ArrayList<>();
        BragePublication publication = new BragePublication();

        data.forEach((type, value) -> {
            if (type instanceof ContributorType) {
                var contributorType = (ContributorType) type;
                dcValues.add(generateDcValueForContributor(contributorType, value));
                publication.addContributor(new BrageContributor(contributorType, value));
                return;
            }
            if (type instanceof CoverageType) {
                var coverageType = (CoverageType) type;
                dcValues.add(generateDcValueForCoverage(coverageType, value));
                publication.addCoverage(new BrageCoverage(coverageType, value));
                return;
            }
            if (type instanceof CreatorType) {
                var creatorType = (CreatorType) type;
                dcValues.add(generateDcValueForCreator(creatorType, value));
                publication.addCreator(new BrageCreator(creatorType, value));
                return;
            }
            if (type instanceof DateType) {
                var dateType = (DateType) type;
                dcValues.add(generateDcValueForDate(dateType, value));
                publication.addDate(new BrageDate(dateType, value));
                return;
            }
            if (type instanceof DescriptionType) {
                var descriptionType = (DescriptionType) type;
                dcValues.add(generateDcValueForDescription(descriptionType, value));
                publication.addDescription(new BrageDescription(descriptionType, value));
                return;
            }
            if (type instanceof IdentifierType) {
                var identifierType = (IdentifierType) type;
                dcValues.add(generateDcValueForIdentifier(identifierType, value));
                publication.addIdentifier(new BrageIdentifier(identifierType, value));
                return;
            }
            if (type instanceof FormatType) {
                var formatType = (FormatType) type;
                dcValues.add(generateDcValueForFormat(formatType, value));
                publication.addFormat(new BrageFormat(formatType, value));
                return;
            }
            throw new RuntimeException("Cannot generate test data for unknown type");
        });

        var dublinCore = new DublinCore();
        dublinCore.setDcValues(dcValues);

        return new AbstractMap.SimpleEntry<>(dublinCore, publication);
    }

    private DcValue generateDcValueForFormat(FormatType formatType, String value) {
        return new DcValueBuilder()
                .withElement(FORMAT)
                .withLanguage(EN_US)
                .withQualifier(formatType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForDescription(DescriptionType descriptionType, String value) {
        return new DcValueBuilder()
                .withElement(DESCRIPTION)
                .withLanguage(EN_US)
                .withQualifier(descriptionType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForIdentifier(IdentifierType uriType, String value) {
        return new DcValueBuilder()
                .withElement(IDENTIFIER)
                .withLanguage(EN_US)
                .withQualifier(uriType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForCreator(CreatorType creatorType, String value) {
        return new DcValueBuilder()
                .withElement(CREATOR)
                .withLanguage(EN_US)
                .withQualifier(creatorType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForDate(DateType dateType, String value) {
        return new DcValueBuilder()
                .withElement(DATE)
                .withLanguage(null)
                .withQualifier(dateType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForCoverage(CoverageType coverageType, String value) {
        return new DcValueBuilder()
                .withElement(COVERAGE)
                .withLanguage(EN_US)
                .withQualifier(coverageType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForContributor(ContributorType contributorType, String contributorName) {
        return new DcValueBuilder()
                .withElement(CONTRIBUTOR)
                .withLanguage(EN_US)
                .withQualifier(contributorType.getTypeName())
                .withValue(contributorName)
                .build();
    }
}