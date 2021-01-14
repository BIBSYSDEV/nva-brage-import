package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DublinCore;
import no.unit.nva.model.Publication;

public class TestPair {
    private final DublinCore dublinCore;
    private final Publication nvaPublication;

    public TestPair(DublinCore dublinCore, Publication nvaPublication) {
        this.dublinCore = dublinCore;
        this.nvaPublication = nvaPublication;
    }

    public DublinCore getDublinCore() {
        return dublinCore;
    }

    public Publication getNvaPublication() {
        return nvaPublication;
    }
}
