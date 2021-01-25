package no.unit.nva.brage.importer;

import no.unit.nva.model.Publication;

public class PathPublicationPair {
    private transient String path;
    private transient Publication publication;

    public PathPublicationPair(String path, Publication publication) {
        this.path = path;
        this.publication = publication;
    }

    public String getPath() {
        return path;
    }

    public Publication getPublication() {
        return publication;
    }
}
