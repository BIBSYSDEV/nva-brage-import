package no.unit.nva.importbrage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCoverage;
import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BrageDescription;
import no.unit.nva.importbrage.metamodel.BrageIdentifier;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.ContributorType;
import no.unit.nva.importbrage.metamodel.CoverageType;
import no.unit.nva.importbrage.metamodel.DateType;
import no.unit.nva.importbrage.metamodel.DescriptionType;
import no.unit.nva.importbrage.metamodel.IdentifierType;
import nva.commons.utils.log.LogUtils;
import nva.commons.utils.log.TestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                DateType.ACCESSIONED, ANY_DATE,
                IdentifierType.URI, EXAMPLE_URI,
                DescriptionType.ABSTRACT, "A long descriptive text that stands as a description");
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
        assertThat(xmlImport.getErrors().size(), is(equalTo(1)));
    }

    @Test
    void xmlImportReportsBragePublicationWhenCoverageQualifierIsUnknown() throws IOException {
        var dublinCore = generateDublinCoreWithQualifierForElement(COVERAGE, "nonsense");
        var file = getTemporaryFile();
        writeXmlFile(file, dublinCore);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        assertThat(xmlImport.getErrors().size(), is(equalTo(1)));
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
                .withElement("nonsense")
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
            Map<Enum<? extends Enum<?>>, String> data) {
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
            throw new RuntimeException("Cannot generate test data for unknown type");
        });

        var dublinCore = new DublinCore();
        dublinCore.setDcValues(dcValues);

        return new AbstractMap.SimpleEntry<>(dublinCore, publication);
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