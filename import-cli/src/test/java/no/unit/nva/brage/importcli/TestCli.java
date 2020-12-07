import no.unit.nva.brage.importcli.ImportCli;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestCli {

    @Test
    void importCliPrintsOutput() {
        var importCli = new ImportCli();
        CommandLine commandLine = new CommandLine(importCli);
        var stringWriter = new StringWriter();
        commandLine.setOut(new PrintWriter(stringWriter));
        assertThat()
    }
}
