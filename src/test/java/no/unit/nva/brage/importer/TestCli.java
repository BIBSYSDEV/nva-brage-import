package no.unit.nva.brage.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.brage.dublincore.DublinCore;

import no.unit.nva.brage.importer.generator.TestPair;
import no.unit.nva.brage.importer.generator.TestPairGenerator;
import no.unit.nva.model.Publication;
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
import nva.commons.utils.JsonUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static no.unit.nva.brage.importer.ImportCli.MISSING_FILE_TEMPLATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCli {

    public static final String FILE_NAME = "input_file.xml";
    public static final String INPUT_PREFIX = "-i=";
    public static final String OUTPUT_PREFIX = "-o=";
    public static final String NON_EXISTING_DIRECTORY = "Non-existing-directory";
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final ObjectMapper mapper = new XmlMapper();
    public static final ObjectMapper jsonObjectMapper = JsonUtils.objectMapper;

    @TempDir
    transient File temporaryInputDirectory;

    @TempDir
    transient File getTemporaryOutputDirectory;

    transient CommandLine commandLine;
    transient StringWriter systemOut;
    transient StringWriter systemErr;

    private static Stream<Arguments> instanceTypeProvider() {
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

    @BeforeEach
    void setup() {
        temporaryInputDirectory.deleteOnExit();
        temporaryInputDirectory.deleteOnExit();
        var importCli = new ImportCli();
        commandLine = new CommandLine(importCli);
        systemOut = new StringWriter();
        commandLine.setOut(new PrintWriter(systemOut));
        systemErr = new StringWriter();
        commandLine.setErr(new PrintWriter(systemErr));
    }

    @Test
    void importCliProducesExpectedOutputFromValidDublinCore() throws URISyntaxException {
        var filename = "one";
        var inputFileArg = getFileArgumentFromResources(filename);
        var outputDirectoryArg = getOutputOptionForExistingDirectory();
        var ownerArg = "--owner=does@not.matter";
        var institutionArg = "--institution=https://example.com/123";
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg, ownerArg, institutionArg);
        var outputFile = outputDirectoryArg.replace(OUTPUT_PREFIX, "") + "/1/1/dublin_core.xml";
        assertThat(exitCode, equalTo(SUCCESS));
        assertTrue(new File(outputFile).exists());
    }

    @Test
    void importCliProducesXExpectedOutputFromValidDublinCore() throws URISyntaxException {
        var filename = "three";
        var inputFileArg = getFileArgumentFromResources(filename);
        var outputDirectoryArg = getOutputOptionForExistingDirectory();
        var ownerArg = "--owner=does@not.matter";
        var institutionArg = "--institution=https://example.com/123";
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg, ownerArg, institutionArg);
        assertThat(exitCode, equalTo(0));
    }

    @ParameterizedTest(name = "CLI can generate publication for type {0}")
    @MethodSource("instanceTypeProvider")
    void importCliProducesExpectedResultFromValidInput(Class<?> instanceType) throws MalformedContributorException, MalformedURLException,
            InvalidIssnException, InvalidIsbnException {
        var testPair = new TestPairGenerator(instanceType).getTestPair();
        var inputArg = generateInput(testPair);
        var outputArg = getOutputOptionForExistingDirectory();
        var ownerArg = "--owner=does@not.matter";
        var institutionArg = "--institution=https://example.com/123";
        int exitCode = commandLine.execute(inputArg, outputArg, ownerArg, institutionArg);
        assertThat(exitCode, equalTo(SUCCESS));
        assertThat(systemErr.toString(), is(emptyString()));
        comparePublication(new PathPublicationPair(outputArg.replace(OUTPUT_PREFIX, "") + "/1/1/dublin_core.xml", testPair.getNvaPublication()));
    }

    private String generateInput(TestPair testPair) {
        var inputDirectory = new File(temporaryInputDirectory.getAbsolutePath(), "1/1");
        inputDirectory.mkdirs();
        writeXml(inputDirectory, testPair.getDublinCore());
        return INPUT_PREFIX + temporaryInputDirectory;
    }

    private String getFileArgumentFromResources(String filename) throws URISyntaxException {
        var url = getClass().getClassLoader().getResource(filename);
        var file = Paths.get(Objects.requireNonNull(url).toURI()).toFile();
        return INPUT_PREFIX + file.getAbsolutePath();
    }

    @Test
    void importCliReportsMissingInputFile() {
        String absolutePath = getNonExistingFilePath();
        var inputFileArg = INPUT_PREFIX + absolutePath;
        var outputDirectoryArg = getOutputOptionForExistingDirectory();
        var ownerArg = "--owner=does@not.matter";
        var institutionArg = "--institution=https://example.com/123";
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg, ownerArg, institutionArg);
        assertThat(exitCode, equalTo(ERROR));
        var expected = String.format(MISSING_FILE_TEMPLATE, absolutePath);
        assertThat(systemErr.toString(), Matchers.containsString(expected));
    }

    @Test
    void importCliReportsMissingOutputDirectory() throws IOException {
        String inputFileArg = generateTemporaryFileReturningArgumentWithFilePath();
        var outputDirectoryArg = OUTPUT_PREFIX + NON_EXISTING_DIRECTORY;
        var ownerArg = "--owner=does@not.matter";
        var institutionArg = "--institution=https://example.com/123";
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg, ownerArg, institutionArg);
        assertThat(exitCode, equalTo(ERROR));
        var expected = String.format(MISSING_FILE_TEMPLATE, NON_EXISTING_DIRECTORY);
        assertThat(systemErr.toString(), Matchers.containsString(expected));
    }

    @Test
    void importCliReadsDirectoryStructure() {
        var publications = generateValidTemporaryFileStructure();
        var inputFileArg = INPUT_PREFIX + temporaryInputDirectory.getAbsolutePath();
        var outputFileArg = getOutputOptionForExistingDirectory();
        var ownerArg = "--owner=does@not.matter";
        var institutionArg = "--institution=https://example.com/123";

        int exitCode = commandLine.execute(inputFileArg, outputFileArg, ownerArg, institutionArg);
        assertThat(exitCode, equalTo(SUCCESS));
        publications.forEach(this::comparePublication);
    }

    private void comparePublication(PathPublicationPair pair) {
        var file = new File(pair.getPath());
        try {
            var actual = jsonObjectMapper.readValue(file, Publication.class);
            var expected = pair.getPublication();
            expected.setOwner(actual.getOwner());
            expected.setPublisher(actual.getPublisher());
            expected.setModifiedDate(actual.getModifiedDate());
            assertThat(actual, equalTo(expected));
        } catch (IOException e) {
            throw new RuntimeException("Could not parse generated Publication");
        }
    }

    private List<PathPublicationPair> generateValidTemporaryFileStructure() {
        var topLevel = generateTopLevelDirectory();
        var collectionLevel = List.of("170000", "170001", "170002", "2800205", "2800176");
        return collectionLevel.stream()
                .map(collection -> generateIntegerBasedDirectories(topLevel, collection))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private File generateTopLevelDirectory() {
        return new File(temporaryInputDirectory, "testing-" + Instant.now());
    }

    private List<PathPublicationPair> generateIntegerBasedDirectories(File topLevel, String collection) {
        String collectionUri = topLevel.getAbsolutePath() + "/" + collection + "/";
        return IntStream.rangeClosed(1, 5)
                .mapToObj(String::valueOf)
                .map(dir -> collectionUri + dir)
                .map(File::new)
                .map(this::addFiles)
                .collect(Collectors.toList());
    }

    private PathPublicationPair addFiles(File file) {
        // TODO insert dublin_core.xml
        return addValidDublinCoreXml(file);
        // TODO insert contents
        // TODO insert handle
        // TODO insert metadata_cristin.xml
        // TODO insert cristin-<int>.xml
        // TODO insert ORE.xml
        // TODO OriginalFile.pdf
        // TODO OriginalFile.pdf.jpg
        // TODO OriginalFile.pdf.txt
    }

    private PathPublicationPair addValidDublinCoreXml(File file) {
        TestPair testPair = generateTestPair();
        var parent = file.mkdirs();
        if (!parent) {
            throw new RuntimeException("Could not generate file structure");
        }
        var fullPath = new File(file.getAbsolutePath() + "/dublin_core.xml");
        writeXml(fullPath, testPair.getDublinCore());
        var expectedPath = fullPath.getAbsolutePath().replace(temporaryInputDirectory.getAbsolutePath(), getTemporaryOutputDirectory.getAbsolutePath());
        return new PathPublicationPair(expectedPath, testPair.getNvaPublication());
    }

    private TestPair generateTestPair() {
        TestPair testPair;
        try {
            testPair = new TestPairGenerator(BookAnthology.class).getTestPair();
        } catch (MalformedContributorException | InvalidIsbnException | MalformedURLException |
                InvalidIssnException e) {
            throw new RuntimeException("Could not generate test pair");
        }
        return testPair;
    }

    private void writeXml(File fullPath, DublinCore dublinCore) {
        try {
            var file = new File(fullPath, "dublin_core.xml");
            mapper.writeValue(file, dublinCore);
        } catch (IOException e) {
            throw new RuntimeException("Could not write Dublin Core XML");
        }
    }

    private String generateTemporaryFileReturningArgumentWithFilePath()
            throws IOException {
        var temporaryFile = new File(temporaryInputDirectory, FILE_NAME);
        var lines = Arrays.asList("<begin>", "middle", "</begin>");
        Files.write(temporaryFile.toPath(), lines);
        return INPUT_PREFIX + temporaryFile.getAbsolutePath();
    }

    private String getNonExistingFilePath() {
        var nonExistingFile = new File(temporaryInputDirectory, FILE_NAME);
        return nonExistingFile.getAbsolutePath();
    }

    private String getOutputOptionForExistingDirectory() {
        return OUTPUT_PREFIX + getTemporaryOutputDirectory.getAbsolutePath();
    }
}
