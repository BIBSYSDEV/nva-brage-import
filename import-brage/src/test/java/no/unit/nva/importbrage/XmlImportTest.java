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
import no.unit.nva.importbrage.metamodel.BrageLanguage;
import no.unit.nva.importbrage.metamodel.BrageProvenance;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.BragePublisher;
import no.unit.nva.importbrage.metamodel.BrageRelation;
import no.unit.nva.importbrage.metamodel.BrageRights;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.importbrage.metamodel.types.CoverageType;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;
import no.unit.nva.importbrage.metamodel.types.ElementType;
import no.unit.nva.importbrage.metamodel.types.FormatType;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import no.unit.nva.importbrage.metamodel.types.LanguageType;
import no.unit.nva.importbrage.metamodel.types.ProvenanceType;
import no.unit.nva.importbrage.metamodel.types.PublisherType;
import no.unit.nva.importbrage.metamodel.types.RelationType;
import no.unit.nva.importbrage.metamodel.types.RightsType;
import nva.commons.utils.log.LogUtils;
import nva.commons.utils.log.TestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException.MESSAGE_TEMPLATE;
import static no.unit.nva.importbrage.metamodel.types.ContributorType.CONTRIBUTOR;
import static no.unit.nva.importbrage.metamodel.types.CoverageType.COVERAGE;
import static no.unit.nva.importbrage.metamodel.types.CreatorType.CREATOR;
import static no.unit.nva.importbrage.metamodel.types.DescriptionType.DESCRIPTION;
import static no.unit.nva.importbrage.metamodel.types.FormatType.FORMAT;
import static no.unit.nva.importbrage.metamodel.types.IdentifierType.IDENTIFIER;
import static no.unit.nva.importbrage.metamodel.types.LanguageType.LANGUAGE;
import static no.unit.nva.importbrage.metamodel.types.ProvenanceType.PROVENANCE;
import static no.unit.nva.importbrage.metamodel.types.PublisherType.PUBLISHER;
import static no.unit.nva.importbrage.metamodel.types.RelationType.RELATION;
import static no.unit.nva.importbrage.metamodel.types.RightsType.RIGHTS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNull;

class XmlImportTest {

    public static final String TEMP_FILE = "complete_record.xml";
    public static final String EXAMPLE_URI = "https://example.org/article/1";
    public static final String RANDY_OLSON = "Randy Olson";
    public static final String NORWAY = "Norway";
    public static final String DATE = "date";
    public static final String ANY_DATE = "2020-02-01";
    private static final String EN_US = "en_US";
    public static final String DC = "dc";
    public static final String DESCRIPTION_STRING = "Something resembling a description!";
    public static final String DELIMITER = ", ";
    public static final String NONSENSE = "nonsense";
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
        var testData = new HashMap<ElementType, String>();
        testData.put(ContributorType.AUTHOR, RANDY_OLSON);
        testData.put(CoverageType.SPATIAL, NORWAY);
        testData.put(CreatorType.UNQUALIFIED, RANDY_OLSON);
        testData.put(DateType.ACCESSIONED, ANY_DATE);
        testData.put(IdentifierType.URI, EXAMPLE_URI);
        testData.put(DescriptionType.ABSTRACT, "A long descriptive text that stands as a description");
        testData.put(FormatType.EXTENT, "232");
        testData.put(LanguageType.ISO, "en");
        testData.put(ProvenanceType.UNQUALIFIED, "Stolen goods");
        testData.put(PublisherType.UNQUALIFIED, "Ratty McFeeson publishing LLC");
        testData.put(RelationType.HAS_PART, "Some other bit");
        testData.put(RightsType.HOLDER, "Mr Holder's older brother");

        var testPair = generateTestPair(testData);
        BragePublication publication = getBragePublication(testPair.getKey());
        assertThat(publication, equalTo(testPair.getValue()));
    }

    @Test
    void xmlImportReturnsNullOnUnknownTypes() throws IOException {
        var testValues = generateDcValuesWithUnknownType();
        BragePublication publication = getBragePublication(testValues);
        assertNull(publication);
    }

    @Test
    void xmlImportLogsErrorWhenDcValueHasUnknownType() throws IOException {
        var testValues = generateDcValuesWithUnknownType();
        File file = generateXmlFile(testValues);
        var xmlImport = new XmlImport();
        xmlImport.map(file);
        assertThat(logger.getMessages(), containsString(file.getAbsolutePath()));
    }

    @Test
    void xmlImportCreatesErrorListWhenDcValueHasUnknownType() throws IOException {
        var testValues = generateDcValuesWithUnknownType();
        var xmlImport = new XmlImport();
        File file = generateXmlFile(testValues);
        xmlImport.map(file);
        var errors = xmlImport.getErrors();
        assertThat(errors, is(not(empty())));
    }

    @SuppressWarnings("unused") // we use "name" in the display name
    @ParameterizedTest(name = "XmlImport allows name {0} — {1}")
    @MethodSource("elementTypeProvider")
    void xmlImportGeneratesBragePublicationWhenElementTypesArePresent(String name, ElementType type)
            throws IOException {
        var testPair = generateTestPair(Map.of(type, DESCRIPTION_STRING));
        BragePublication publication = getBragePublication(testPair.getKey());
        assertThat(publication, equalTo(testPair.getValue()));
    }

    static Stream<Arguments> elementTypeProvider() {
        Stream.Builder<Arguments> argumentsBuilder = Stream.builder();
        var elementTypes = new ArrayList<ElementType>(Arrays.asList(ContributorType.values()));
        elementTypes.addAll(Arrays.asList(CoverageType.values()));
        elementTypes.addAll(Arrays.asList(CreatorType.values()));
        elementTypes.addAll(Arrays.asList(DateType.values()));
        elementTypes.addAll(Arrays.asList(DescriptionType.values()));
        elementTypes.addAll(Arrays.asList(FormatType.values()));
        elementTypes.addAll(Arrays.asList(IdentifierType.values()));
        elementTypes.addAll(Arrays.asList(LanguageType.values()));
        elementTypes.addAll(Arrays.asList(ProvenanceType.values()));
        elementTypes.addAll(Arrays.asList(PublisherType.values()));
        elementTypes.addAll(Arrays.asList(RelationType.values()));
        elementTypes.addAll(Arrays.asList(RightsType.values()));

        for (ElementType elementType : elementTypes) {
            argumentsBuilder.add(Arguments.of(elementType.getClass().getSimpleName(), elementType));
        }
        return argumentsBuilder.build();
    }

    @ParameterizedTest(name = "XmlImport disallows unqualified Coverage for \"{0}\"")
    @NullAndEmptySource
    void xmlImportReportsBragePublicationWhenCoverageIsUnqualified(String type) throws IOException {
        var dublinCore = generateDublinCoreWithQualifierForElement(COVERAGE, type);
        File file = generateXmlFile(dublinCore);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        var actual = String.join(DELIMITER, xmlImport.getErrors());
        String expectedMessage = String.format(MESSAGE_TEMPLATE, COVERAGE, type, CoverageType.getAllowedValues());
        assertThat(actual, containsString(expectedMessage));
    }

    @ParameterizedTest(name = "XmlImport creates report when qualifier is unknown for {0}")
    @ValueSource(strings = {CONTRIBUTOR, COVERAGE, CREATOR, DATE, DESCRIPTION, FORMAT, IDENTIFIER, LANGUAGE,
            PROVENANCE, PUBLISHER, RELATION, RIGHTS})
    void xmlImportReportsBragePublicationWhenQualifierIsUnknown(String type) throws IOException {
        var dublinCore = generateDublinCoreWithQualifierForElement(type, NONSENSE);
        File file = generateXmlFile(dublinCore);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        var actual = String.join(DELIMITER, xmlImport.getErrors());
        String expectedMessage = String.format(MESSAGE_TEMPLATE, type, NONSENSE, getAllowedValues(type));
        assertThat(actual, containsString(expectedMessage));
    }

    @Test
    void xmlImportGeneratesBragePublicationWhenCreatorIsUnqualified() throws IOException {
        var testPair = generateTestPair(Map.of(CreatorType.UNQUALIFIED, RANDY_OLSON));
        BragePublication publication = getBragePublication(testPair.getKey());
        assertThat(publication, is(equalTo(testPair.getValue())));
    }

    @Test
    void xmlImportReportsErrorsWhenCreatorIsQualified() throws IOException {
        var testPair = generateDublinCoreWithQualifierForElement(CREATOR, NONSENSE);
        File file = generateXmlFile(testPair);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
        var actual = String.join(DELIMITER, xmlImport.getErrors());
        var expected = String.format(MESSAGE_TEMPLATE, CREATOR, NONSENSE, CreatorType.getAllowedValues());
        assertThat(actual, containsString(expected));
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
            case LANGUAGE:
                return LanguageType.getAllowedValues();
            case PROVENANCE:
                return ProvenanceType.getAllowedValues();
            case PUBLISHER:
                return PublisherType.getAllowedValues();
            case RELATION:
                return RelationType.getAllowedValues();
            case RIGHTS:
                return RightsType.getAllowedValues();
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
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
            Map<ElementType, String> data) {
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
            if (type instanceof LanguageType) {
                var languageType = (LanguageType) type;
                dcValues.add(generateDcValueForLanguage(languageType, value));
                publication.addLanguage(new BrageLanguage(languageType, value));
                return;
            }
            if (type instanceof ProvenanceType) {
                var provenanceType = (ProvenanceType) type;
                dcValues.add(generateDcValueForProvenance(provenanceType, value));
                publication.addProvenance(new BrageProvenance(provenanceType, value));
                return;
            }
            if (type instanceof PublisherType) {
                var publisherType = (PublisherType) type;
                dcValues.add(generateDcValueForPublisher(publisherType, value));
                publication.addPublisher(new BragePublisher(publisherType, value));
                return;
            }
            if (type instanceof RelationType) {
                var relationType = (RelationType) type;
                dcValues.add(generateDcValueForRelation(relationType, value));
                publication.addRelation(new BrageRelation(relationType, value));
                return;
            }
            if (type instanceof RightsType) {
                var rightsType = (RightsType) type;
                dcValues.add(generateDcValueForRights(rightsType, value));
                publication.addRights(new BrageRights(rightsType, value));
                return;
            }
            throw new RuntimeException("Cannot generate test data for unknown type");
        });

        var dublinCore = new DublinCore();
        dublinCore.setDcValues(dcValues);

        return new AbstractMap.SimpleEntry<>(dublinCore, publication);
    }

    private DcValue generateDcValueForRights(RightsType rightsType, String value) {
        return new DcValueBuilder()
                .withElement(RIGHTS)
                .withLanguage(EN_US)
                .withQualifier(rightsType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForRelation(RelationType relationType, String value) {
        return new DcValueBuilder()
                .withElement(RELATION)
                .withLanguage(EN_US)
                .withQualifier(relationType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForPublisher(PublisherType publisherType, String value) {
        return new DcValueBuilder()
                .withElement(PUBLISHER)
                .withLanguage(EN_US)
                .withQualifier(publisherType.getTypeName())
                .withValue(value)
                .build();
    }

    private DcValue generateDcValueForLanguage(LanguageType languageType, String value) {
        return new DcValueBuilder()
                .withElement(LANGUAGE)
                .withLanguage(EN_US)
                .withQualifier(languageType.getTypeName())
                .withValue(value)
                .build();
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

    private DcValue generateDcValueForProvenance(ProvenanceType provenanceType, String value) {
        return new DcValueBuilder()
                .withElement(PROVENANCE)
                .withLanguage(EN_US)
                .withQualifier(provenanceType.getTypeName())
                .withValue(value)
                .build();
    }

    private BragePublication generateBragePublication(File file) throws IOException {
        var xmlImport = new XmlImport();
        return xmlImport.map(file);
    }

    private File generateXmlFile(DublinCore key) throws IOException {
        var file = getTemporaryFile();
        writeXmlFile(file, key);
        return file;
    }

    private BragePublication getBragePublication(DublinCore key) throws IOException {
        File file = generateXmlFile(key);
        return generateBragePublication(file);
    }
}