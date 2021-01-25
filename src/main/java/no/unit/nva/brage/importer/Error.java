package no.unit.nva.brage.importer;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class Error {
    private final URI handle;
    private final File inputFile;
    private final List<String> errors;

    public Error(URI handle, File inputFile, List<String> errors) {
        this.handle = handle;
        this.inputFile = inputFile;
        this.errors = errors;
    }

    public URI getHandle() {
        return handle;
    }

    public File getInputFile() {
        return inputFile;
    }
    public String getHtmlFormattedError() {
        return errors.stream()
                .map(error -> "<span style=\"margin-left:20px\">" + error + "</span>")
                .collect(Collectors.joining("<br>\n"));
    }
}
