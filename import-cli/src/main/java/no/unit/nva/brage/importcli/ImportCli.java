package no.unit.nva.brage.importcli;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "import", mixinStandardHelpOptions = true, version = "Import 1.0",
        description = "Prepares data for import to NVA and generates reports")
public class ImportCli implements Callable<Integer> {

    public static final String MISSING_FILE_TEMPLATE = "The file \"%s\" does not exist%n";
    @CommandLine.Option(names = "-i", required = true, description = "An input file in XML")
    private File input;

    @CommandLine.Option(names = "-o", required = true, description = "The directory to write data to")
    private File output;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec commandSpec;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ImportCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        if (!input.exists()) {
            commandSpec.commandLine().getErr().printf(MISSING_FILE_TEMPLATE, input);
            return 1;
        }
        if (!output.exists()) {
            commandSpec.commandLine().getErr().printf(MISSING_FILE_TEMPLATE, output);
            return 1;
        }
        return 0;
    }
}
