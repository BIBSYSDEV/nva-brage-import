package no.unit.nva.brage.importer;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class Error {
    private final URI uri;
    private final List<String> errors;

    public Error(URI uri, List<String> errors) {
        this.uri = uri;
        this.errors = errors;
    }

    public URI getUri() {
        return uri;
    }

    public String getHtmlFormattedError() {
        return errors.stream()
                .map(error -> "<span style=\"margin-left:20px\">" + error + "</span>")
                .collect(Collectors.joining("<br>\n"));
    }
}
