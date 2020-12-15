package no.unit.nva.importbrage;

import java.util.List;

public final class DublinCoreBuilder {
    private String schema;
    private List<DcValue> dcValues;

    DublinCoreBuilder() {
    }

    protected static DublinCoreBuilder aDublinCore() {
        return new DublinCoreBuilder();
    }

    protected DublinCoreBuilder withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    protected DublinCoreBuilder withDcValues(List<DcValue> dcValues) {
        this.dcValues = dcValues;
        return this;
    }

    protected DublinCore build() {
        DublinCore dublinCore = new DublinCore();
        dublinCore.setSchema(schema);
        dublinCore.setDcValues(dcValues);
        return dublinCore;
    }
}
