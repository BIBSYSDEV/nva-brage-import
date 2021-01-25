package no.unit.nva.brage.importer;

import no.unit.nva.model.Organization;
import no.unit.nva.model.Publication;
import nva.commons.utils.JacocoGenerated;
import nva.commons.utils.JsonUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@CommandLine.Command(name = "import", mixinStandardHelpOptions = true, version = "Import 1.0",
        description = "Prepares data for import to NVA and generates reports")
public class ImportCli implements Callable<Integer> {

    public static final String MISSING_FILE_TEMPLATE = "The file \"%s\" does not exist%n";
    public static final String EMPTY_DIRECTORY_ERROR = "The input directory \"%s\" was empty";
    @CommandLine.Option(names = "-i", required = true, description = "An input file in XML")
    private transient File input;

    @CommandLine.Option(names = "-o", required = true, description = "The directory to write data to")
    private transient File output;

    @CommandLine.Option(names = "--owner", required = true, description = "The owner of the documents")
    private transient String owner;

    @CommandLine.Option(names = "--institution", required = true, description = "The owner institution of the documents")
    private transient String ownerInstitution;

    @CommandLine.Spec
    protected transient CommandLine.Model.CommandSpec commandSpec;
    private final List<Error> errorReport = new ArrayList<>();
    private int totalFiles;

    @SuppressWarnings("PMD.DoNotCallSystemExit")
    @JacocoGenerated
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ImportCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        System.out.println("Initializing import");
        if (!input.exists()) {
            commandSpec.commandLine().getErr().printf(MISSING_FILE_TEMPLATE, input);
            return 1;
        }
        if (!output.exists()) {
            commandSpec.commandLine().getErr().printf(MISSING_FILE_TEMPLATE, output);
            return 1;
        }
        var topLevel = input.listFiles();
        if (isNull(topLevel)) {
            commandSpec.commandLine().getErr().printf(EMPTY_DIRECTORY_ERROR, input);
            return 1;
        }
        var secondLevel = Arrays.stream(topLevel)
                .map(File::listFiles)
                .filter(Objects::nonNull)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .map(File::listFiles)
                .filter(Objects::nonNull)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .filter(file -> file.getName().contains("dublin_core.xml"))
                .collect(Collectors.toList());

        if (secondLevel.isEmpty()) {
            commandSpec.commandLine().getErr().printf("Structure of directory \"%s\" was not as expected", input);
            return 1;
        } else {
            totalFiles = secondLevel.size();
            commandSpec.commandLine().getOut().printf("Parsing " + totalFiles + " files");
        }
        var start = Instant.now();
        for (File file : secondLevel) {
            try {
                if (ignoreNonMetadataFiles(file)) {
                    continue;
                }
                Integer outputCode = generateOutput(file);
                if (nonNull(outputCode)) {
                    return outputCode;
                }
            } catch (IOException e) {
                errorReport.add(new Error(null, null, List.of(String.format("Could not read file: %s", file.getAbsolutePath()))));
            }
        }
        var end = Instant.now();
        commandSpec.commandLine().getOut().println("Completed processing, took " + (end.getEpochSecond() - start.getEpochSecond()) + " seconds");
        generateErrorReport();
        return 0;
    }

    private boolean ignoreNonMetadataFiles(File file) {
        return !file.getName().equals("dublin_core.xml");
    }

    private Publication updatePublication(Publication publication) {
        return publication.copy()
                .withOwner(owner)
                .withPublisher(new Organization.Builder().withId(URI.create(ownerInstitution)).build())
                .withModifiedDate(Instant.now())
                .build();
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private Integer generateOutput(File file) throws IOException {
        commandSpec.commandLine().getOut().println("Parsing file " + file.getCanonicalPath());
        var outputFile = generateOutputFile(file);
        var parent = createParentOutputDirectories(outputFile);
        if (isNull(parent)) {
            return 1;
        }
        var publication = generatePublication(file);
        if (nonNull(publication)) {
            return writePublicationOutput(publication, outputFile);
        } else {
            commandSpec.commandLine().getOut().println("FAILED");
            return null;
        }
    }

    private File generateOutputFile(File file) {
        return new File(file.getAbsolutePath().replace(input.getAbsolutePath(), output.getAbsolutePath()));
    }

    private Publication generatePublication(File file) throws IOException {
        var xmlImport = new XmlImport();
        var mapping = xmlImport.map(file);
        if (mapping.isSuccess()) {
            return updatePublication(mapping.getPublication());}
        else {
            errorReport.add(new Error(mapping.getBrageUri(), file, new ArrayList<>(mapping.getErrors())));
            return null;
        }
    }

    private Integer writePublicationOutput(Publication publication, File outputFile) {
        try {
            JsonUtils.objectMapper.writeValue(outputFile, publication);
            commandSpec.commandLine().getOut().println("SUCCEEDED");
            return null;
        } catch (IOException e) {
            commandSpec.commandLine().getErr().printf("Could not write publication to \"%s\"", outputFile.getAbsolutePath());
            return 1;
        }
    }

    private void generateErrorReport() {
        String value;
        if (errorReport.isEmpty()) {
            value = "No errors";
        } else {
            value = formatErrorReport();
        }
        var errorReport = new File(output.getAbsolutePath() + "/" + "errors.html");
        try {
            Files.write(Path.of(errorReport.getAbsolutePath()), value.getBytes(UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Could not write error report");
        }
    }

    private String formatErrorReport() {
        var references = errorReport.stream()
                .map(this::formatHtmlOutput)
                .collect(Collectors.joining());
        return "<html><body><b>Parsed " + totalFiles + "</b><br><b>There were " + errorReport.size() + " errors</><br>" + references + "</body></html>";
    }

    private String formatHtmlOutput(Error value) {
        return "<a href=\"" + value.getHandle() + "\">Brage document</a> | <a href=\""
                + getInput(value.getInputFile()) + "\">Input</a> | <a href=\""
                + getOutput(value.getInputFile()) + "\">Output</a><br>\n"
                + value.getHtmlFormattedError() + "<br>\n";
    }

    private String getOutput(File inputFile) {

        return inputFile.getAbsolutePath().replace(inputFile.getParentFile().getParentFile().getParent(), ".");
    }

    private String getInput(File inputFile) {
        return inputFile.getAbsolutePath().replace(inputFile.getParentFile().getParentFile().getParent(), "../" + input.getName());
    }

    private File createParentOutputDirectories(File outputFile) {
        var parent = outputFile.getParentFile();
        var createdDirectory = parent.mkdirs();

        if (!createdDirectory && !parent.exists()) {
            commandSpec.commandLine().getErr().printf("Could not create directory structure for \"%s\"", parent.getAbsolutePath());
            return null;
        }
        return parent;
    }
}
