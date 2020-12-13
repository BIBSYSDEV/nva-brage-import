package no.unit.nva.importbrage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.helpers.BrageTestValue;
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
import no.unit.nva.importbrage.metamodel.BrageSource;
import no.unit.nva.importbrage.metamodel.BrageSubject;
import no.unit.nva.importbrage.metamodel.BrageTitle;
import no.unit.nva.importbrage.metamodel.BrageType;
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
import no.unit.nva.importbrage.metamodel.types.SourceType;
import no.unit.nva.importbrage.metamodel.types.SubjectType;
import no.unit.nva.importbrage.metamodel.types.TitleType;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
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
import java.util.List;
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
import static no.unit.nva.importbrage.metamodel.types.SourceType.SOURCE;
import static no.unit.nva.importbrage.metamodel.types.SubjectType.SUBJECT;
import static no.unit.nva.importbrage.metamodel.types.TitleType.TITLE;
import static no.unit.nva.importbrage.metamodel.types.TypeBasic.TYPE;
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
    public static final String DELIMITER = ", ";
    public static final String NONSENSE = "nonsense";
    public static final String DESCRIPTION_EXAMPLE = "A long descriptive text that stands as a description";
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

        var testData = new ArrayList<BrageTestValue>();
        testData.add(new BrageTestValue(ContributorType.AUTHOR, RANDY_OLSON, null));
        testData.add(new BrageTestValue(CoverageType.SPATIAL, NORWAY, EN_US));
        testData.add(new BrageTestValue(CreatorType.UNQUALIFIED, RANDY_OLSON, null));
        testData.add(new BrageTestValue(DateType.ACCESSIONED, ANY_DATE, null));
        testData.add(new BrageTestValue(IdentifierType.URI, EXAMPLE_URI, null));
        testData.add(new BrageTestValue(DescriptionType.ABSTRACT, DESCRIPTION_EXAMPLE, EN_US));
        testData.add(new BrageTestValue(FormatType.EXTENT, "232", null));
        testData.add(new BrageTestValue(LanguageType.ISO, "en", null));
        testData.add(new BrageTestValue(ProvenanceType.UNQUALIFIED, "Stolen goods", null));
        testData.add(new BrageTestValue(PublisherType.UNQUALIFIED, "Ratty McFeeson publishing LLC", null));
        testData.add(new BrageTestValue(RelationType.HAS_PART, "Some other bit", null));
        testData.add(new BrageTestValue(RightsType.HOLDER, "Mr Holder's older brother", null));
        testData.add(new BrageTestValue(SourceType.ARTICLE_NUMBER, "1234", null));
        testData.add(new BrageTestValue(SubjectType.AGROVOC, "Blenny", null));
        testData.add(new BrageTestValue(TitleType.UNQUALIFIED, "Marco's lovely hat soaked in sangria", null));
        testData.add(new BrageTestValue(TypeBasic.UNQUALIFIED, "Hat studies", null));

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
    void xmlImportGeneratesBragePublicationWhenElementTypesArePresent(String value, ElementType type)
            throws IOException {
        var testPair = generateSimpleTestPair(type, value);
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
        elementTypes.addAll(Arrays.asList(SourceType.values()));
        elementTypes.addAll(Arrays.asList(SubjectType.values()));
        elementTypes.addAll(Arrays.asList(TitleType.values()));
        elementTypes.addAll(Arrays.asList(TypeBasic.values()));

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
            PROVENANCE, PUBLISHER, RELATION, RIGHTS, SOURCE, SUBJECT, TITLE, TYPE})
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
        var testPair = generateSimpleTestPair(CreatorType.UNQUALIFIED, RANDY_OLSON);
        BragePublication publication = getBragePublication(testPair.getKey());
        assertThat(publication, is(equalTo(testPair.getValue())));
    }

    private AbstractMap.SimpleEntry<DublinCore, BragePublication> generateSimpleTestPair(
            ElementType type, String value) {
        var language = type.isLanguageBased() ? EN_US : null;
        return generateTestPair(
                List.of(new BrageTestValue(type, value, language)));
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
            case SOURCE:
                return SourceType.getAllowedValues();
            case SUBJECT:
                return SubjectType.getAllowedValues();
            case TITLE:
                return TitleType.getAllowedValues();
            case TYPE:
                return TypeBasic.getAllowedValues();
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
            List<BrageTestValue> data) {
        List<DcValue> dcValues = new ArrayList<>();
        BragePublication publication = new BragePublication();

        data.forEach((element) -> {
            var type = element.getQualifier();
            var value = element.getValue();
            var language = element.getLanguage();
            if (type instanceof ContributorType) {
                var contributorType = (ContributorType) type;
                dcValues.add(generateDcValueForContributor(contributorType, value));
                publication.addContributor(new BrageContributor(contributorType, value));
                return;
            }
            if (type instanceof CoverageType) {
                var coverageType = (CoverageType) type;
                dcValues.add(generateDcValueForCoverage(coverageType, value, language));
                publication.addCoverage(new BrageCoverage(coverageType, value, EN_US));
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
                dcValues.add(generateDcValueForDescription(descriptionType, value, language));
                publication.addDescription(new BrageDescription(descriptionType, value, language));
                return;
            }
            if (type instanceof IdentifierType) {
                var identifierType = (IdentifierType) type;
                dcValues.add(generateDcValueForIdentifier(identifierType, value, language));
                publication.addIdentifier(new BrageIdentifier(identifierType, value, language));
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
                dcValues.add(generateDcValueForLanguage(languageType, value, language));
                publication.addLanguage(new BrageLanguage(languageType, value, language));
                return;
            }
            if (type instanceof ProvenanceType) {
                var provenanceType = (ProvenanceType) type;
                dcValues.add(generateDcValueForProvenance(provenanceType, value, language));
                publication.addProvenance(new BrageProvenance(provenanceType, value, language));
                return;
            }
            if (type instanceof PublisherType) {
                var publisherType = (PublisherType) type;
                dcValues.add(generateDcValueForPublisher(publisherType, value, language));
                publication.addPublisher(new BragePublisher(publisherType, value, language));
                return;
            }
            if (type instanceof RelationType) {
                var relationType = (RelationType) type;
                dcValues.add(generateDcValueForRelation(relationType, value, language));
                publication.addRelation(new BrageRelation(relationType, value, language));
                return;
            }
            if (type instanceof RightsType) {
                var rightsType = (RightsType) type;
                dcValues.add(generateDcValueForRights(rightsType, value, language));
                publication.addRights(new BrageRights(rightsType, value, language));
                return;
            }
            if (type instanceof SourceType) {
                var sourceType = (SourceType) type;
                dcValues.add(generateDcValueForSource(sourceType, value, language));
                publication.addSource(new BrageSource(sourceType, value, language));
                return;
            }
            if (type instanceof SubjectType) {
                var subjectType = (SubjectType) type;
                dcValues.add(generateDcValueForSubject(subjectType, value, language));
                publication.addSubject(new BrageSubject(subjectType, value, language));
                return;
            }
            if (type instanceof TitleType) {
                var titleType = (TitleType) type;
                dcValues.add(generateDcValueForTitle(titleType, value, language));
                publication.addTitle(new BrageTitle(titleType, value, language));
                return;
            }
            if (type instanceof TypeBasic) {
                var typeBasic = (TypeBasic) type;
                dcValues.add(generateDcValueForType(typeBasic, value, language));
                publication.addType(new BrageType(typeBasic, value, language));
                return;
            }
            throw new RuntimeException("Cannot generate test data for unknown type");
        });

        var dublinCore = new DublinCore();
        dublinCore.setDcValues(dcValues);

        return new AbstractMap.SimpleEntry<>(dublinCore, publication);
    }

    private DcValue generateDcValueForType(TypeBasic typeBasic, String value, String language) {
        return getDcValue(value, TYPE, language, typeBasic.getTypeName());
    }

    private DcValue generateDcValueForTitle(TitleType titleType, String value, String language) {
        return getDcValue(value, TITLE, language, titleType.getTypeName());
    }

    private DcValue generateDcValueForSubject(SubjectType subjectType, String value, String language) {
        return getDcValue(value, SUBJECT, language, subjectType.getTypeName());
    }

    private DcValue generateDcValueForSource(SourceType sourceType, String value, String language) {
        return getDcValue(value, SOURCE, language, sourceType.getTypeName());
    }

    private DcValue generateDcValueForRights(RightsType rightsType, String value, String language) {
        return getDcValue(value, RIGHTS, language, rightsType.getTypeName());
    }

    private DcValue generateDcValueForRelation(RelationType relationType, String value, String language) {
        return getDcValue(value, RELATION, language, relationType.getTypeName());
    }

    private DcValue generateDcValueForPublisher(PublisherType publisherType, String value, String language) {
        return getDcValue(value, PUBLISHER, language, publisherType.getTypeName());
    }

    private DcValue generateDcValueForLanguage(LanguageType languageType, String value, String language) {
        return getDcValue(value, LANGUAGE, language, languageType.getTypeName());
    }

    private DcValue generateDcValueForFormat(FormatType formatType, String value) {
        return getDcValue(value, FORMAT, null, formatType.getTypeName());
    }

    private DcValue generateDcValueForDescription(DescriptionType descriptionType, String value, String language) {
        return getDcValue(value, DESCRIPTION, language, descriptionType.getTypeName());
    }

    private DcValue generateDcValueForIdentifier(IdentifierType uriType, String value, String language) {
        return getDcValue(value, IDENTIFIER, language, uriType.getTypeName());
    }

    private DcValue generateDcValueForCreator(CreatorType creatorType, String value) {
        return getDcValue(value, CREATOR, null, creatorType.getTypeName());
    }

    private DcValue generateDcValueForDate(DateType dateType, String value) {
        return getDcValue(value, DATE, null, dateType.getTypeName());
    }

    private DcValue generateDcValueForCoverage(CoverageType coverageType, String value, String language) {
        return getDcValue(value, COVERAGE, language, coverageType.getTypeName());
    }

    private DcValue generateDcValueForContributor(ContributorType contributorType, String contributorName) {
        return getDcValue(contributorName, CONTRIBUTOR, null, contributorType.getTypeName());
    }

    private DcValue generateDcValueForProvenance(ProvenanceType provenanceType, String value, String language) {
        return getDcValue(value, PROVENANCE, language, provenanceType.getTypeName());
    }

    private DcValue getDcValue(String value, String subject, String enUs, String typeName) {
        return new DcValueBuilder()
                .withElement(subject)
                .withLanguage(enUs)
                .withQualifier(typeName)
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