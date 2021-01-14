package no.unit.nva.brage.importer;


import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.dublincore.DublinCore;

import java.util.List;

public final class DublinCoreBuilder {
    private transient String schema;
    private transient List<DcValue> dcValues;

    public DublinCoreBuilder() {
    }

    protected static DublinCoreBuilder aDublinCore() {
        return new DublinCoreBuilder();
    }

    public DublinCoreBuilder withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public DublinCoreBuilder withDcValues(List<DcValue> dcValues) {
        this.dcValues = dcValues;
        return this;
    }

    public DublinCore build() {
        DublinCore dublinCore = new DublinCore();
        dublinCore.setSchema(schema);
        dublinCore.setDcValues(dcValues);
        return dublinCore;
    }
}
