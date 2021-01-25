package no.unit.nva.brage.importer;

import no.unit.nva.model.Publication;

import java.net.URI;
import java.util.List;

import static java.util.Objects.nonNull;

public class Mapping {
    private final List<String> errors;
    private final Publication publication;
    private final URI brageUri;

    public Mapping(List<String> errors, Publication publication, URI brageUri) {
        this.errors = errors;
        this.publication = publication;
        this.brageUri = brageUri;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Publication getPublication() {
        return publication;
    }

    public boolean isSuccess() {
        return nonNull(publication);
    }

    public URI getBrageUri() {
        return brageUri;
    }
}
