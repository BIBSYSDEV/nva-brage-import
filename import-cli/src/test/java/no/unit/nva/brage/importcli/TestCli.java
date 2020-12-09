package no.unit.nva.brage.importcli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Arrays;

import static no.unit.nva.brage.importcli.ImportCli.MISSING_FILE_TEMPLATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TestCli {

    public static final String FILE_NAME = "input_file.xml";
    public static final String INPUT_PREFIX = "-i=";
    public static final String OUTPUT_PREFIX = "-o=";
    public static final String NON_EXISTING_DIRECTORY = "Non-existing-directory";
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    @TempDir
    File temporaryInputDirectory;

    @TempDir
    File getTemporaryOutputDirectory;

    CommandLine commandLine;
    StringWriter systemOut;
    StringWriter systemErr;

    @BeforeEach
    void setup() {
        var importCli = new ImportCli();
        commandLine = new CommandLine(importCli);
        systemOut = new StringWriter();
        commandLine.setOut(new PrintWriter(systemOut));
        systemErr = new StringWriter();
        commandLine.setErr(new PrintWriter(systemErr));
    }

    @Test
    void importCliTakesInputFileArgumentAndOutputFolderArgument() throws IOException {
        String inputFileArg = generateTemporaryFileReturningArgumentWithFilePath();
        var outputDirectoryArg = getOutputOptionForExistingDirectory();
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg);
        assertThat(exitCode, equalTo(SUCCESS));
    }

    @Test
    void importCliReportsMissingInputFile() {
        String absolutePath = getNonExistingFilePath();
        var inputFileArg = INPUT_PREFIX + absolutePath;
        var outputDirectoryArg = getOutputOptionForExistingDirectory();
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg);
        assertThat(exitCode, equalTo(ERROR));
        var expected = String.format(MISSING_FILE_TEMPLATE, absolutePath);
        assertThat(systemErr.toString(), containsString(expected));
    }

    @Test
    void importCliReportsMissingOutputDirectory() throws IOException {
        String inputFileArg = generateTemporaryFileReturningArgumentWithFilePath();
        var outputDirectoryArg = OUTPUT_PREFIX + NON_EXISTING_DIRECTORY;
        int exitCode = commandLine.execute(inputFileArg, outputDirectoryArg);
        assertThat(exitCode, equalTo(ERROR));
        var expected = String.format(MISSING_FILE_TEMPLATE, NON_EXISTING_DIRECTORY);
        assertThat(systemErr.toString(), containsString(expected));
    }

    private String generateTemporaryFileReturningArgumentWithFilePath() throws IOException {
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
