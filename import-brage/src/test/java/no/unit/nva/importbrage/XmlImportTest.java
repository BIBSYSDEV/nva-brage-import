package no.unit.nva.importbrage;


import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCoverage;
import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BrageIdentifier;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.ContributorType;
import no.unit.nva.importbrage.metamodel.CoverageType;
import no.unit.nva.importbrage.metamodel.DateType;
import no.unit.nva.importbrage.metamodel.IdentifierType;
import nva.commons.utils.log.LogUtils;
import nva.commons.utils.log.TestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static final String FAKE_HANDLE = "https://fakehandle/1/1";
    public static TestAppender logger;

    @TempDir
    File testDirectory;

    @BeforeEach
    void setup() {
        logger = LogUtils.getTestingAppender(XmlImport.class);
    }

    @Test
    void xmlImportLoadsData() throws IOException {
        var testValues = Map.of(
                ContributorType.AUTHOR, "Jimmy Olson",
                IdentifierType.ISBN, "1231231231233",
                CoverageType.SPATIAL, "The moon",
                DateType.ACCESSIONED, "2001-01-22T01:01:01.231");
        var file = generateTemporaryFileWithCompleteRecord(testValues);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNotNull(publication);
        testAllValues(testValues, publication);
    }

    @Test
    void xmlImportReturnsNullOnUnknownTypes() throws IOException {
        var testValues = Map.of(UnknownType.UNKNOWN_TYPE, "Irrelevant",
                IdentifierType.URI, FAKE_HANDLE);
        var file = generateTemporaryFileWithCompleteRecord(testValues);
        var xmlImport = new XmlImport();
        var publication = xmlImport.map(file);
        assertNull(publication);
    }

    @Test
    void xmlImportLogsErrorWhenDcValueHasUnknownType() throws IOException {
        var testValues = Map.of(UnknownType.UNKNOWN_TYPE, "Irrelevant",
                IdentifierType.URI, FAKE_HANDLE);
        var file = generateTemporaryFileWithCompleteRecord(testValues);
        var xmlImport = new XmlImport();
        xmlImport.map(file);
        assertThat(logger.getMessages(), containsString(file.getAbsolutePath()));
    }

    @Test
    void xmlImportCreatesErrorListWhenDcValueHasUnknownType() throws IOException {
        var testValues = Map.of(UnknownType.UNKNOWN_TYPE, "Irrelevant",
                IdentifierType.URI, FAKE_HANDLE);
        var file = generateTemporaryFileWithCompleteRecord(testValues);
        var xmlImport = new XmlImport();
        xmlImport.map(file);
        var errors = xmlImport.getErrors();
        assertThat(errors, is(not(empty())));
    }

    private void testAllValues(Map<Enum<? extends Enum<?>>, String> testValues, BragePublication publication) {
        List<AbstractMap.SimpleEntry<ContributorType, String>> expectedContributors
                = new ArrayList<>();
        List<AbstractMap.SimpleEntry<CoverageType, String>> expectedCoverage = new ArrayList<>();
        List<AbstractMap.SimpleEntry<DateType, String>> expectedDates = new ArrayList<>();
        List<AbstractMap.SimpleEntry<IdentifierType, String>> expectedIdentifiers = new ArrayList<>();

        testValues.forEach((k, v) -> {
            if (k instanceof ContributorType) {
                expectedContributors.add(new AbstractMap.SimpleEntry<>((ContributorType) k, v));
            }
            if (k instanceof CoverageType) {
                expectedCoverage.add(new AbstractMap.SimpleEntry<>((CoverageType) k, v));
            }
            if (k instanceof DateType) {
                expectedDates.add(new AbstractMap.SimpleEntry<>((DateType) k, v));
            }
            if (k instanceof IdentifierType) {
                expectedIdentifiers.add(new AbstractMap.SimpleEntry<>((IdentifierType) k, v));
            }
        });
        testContributors(expectedContributors, publication.getContributors());
        testCoverage(expectedCoverage, publication.getCoverage());
        testDates(expectedDates, publication.getDates());
        testIdentifiers(expectedIdentifiers, publication.getIdentifiers());
    }

    private void testIdentifiers(
            List<AbstractMap.SimpleEntry<IdentifierType, String>> expectedIdentifiers,
            List<BrageIdentifier> identifiers) {
        var expectedContributorList = expectedIdentifiers.stream()
                .map(current -> new BrageIdentifier(current.getKey(), current.getValue()))
                .collect(Collectors.toList());
        assertThat(identifiers, equalTo(expectedContributorList));
    }

    private void testDates(List<AbstractMap.SimpleEntry<DateType, String>> expectedDates,
                           List<BrageDate> dates) {
        var expectedContributorList = expectedDates.stream()
                .map(current -> new BrageDate(current.getKey(), current.getValue()))
                .collect(Collectors.toList());
        assertThat(dates, equalTo(expectedContributorList));
    }

    private void testCoverage(List<AbstractMap.SimpleEntry<CoverageType, String>> expectedCoverage,
                              List<BrageCoverage> coverage) {
        var expectedContributorList = expectedCoverage.stream()
                .map(current -> new BrageCoverage(current.getKey(), current.getValue()))
                .collect(Collectors.toList());
        assertThat(coverage, equalTo(expectedContributorList));
    }

    private void testContributors(
            List<AbstractMap.SimpleEntry<ContributorType, String>> expectedContributors,
            List<BrageContributor> contributors) {
        var expectedContributorList = expectedContributors.stream()
                .map(current -> new BrageContributor(current.getKey(), current.getValue()))
                .collect(Collectors.toList());
        assertThat(contributors, equalTo(expectedContributorList));
    }

    private File generateTemporaryFileWithCompleteRecord(Map<Enum<? extends Enum<?>>, String> values)
            throws IOException {
        TestDataGenerator data = new TestDataGenerator(values);
        File file = new File(testDirectory, TEMP_FILE);
        data.writeXmlFile(file);
        return file;
    }
}