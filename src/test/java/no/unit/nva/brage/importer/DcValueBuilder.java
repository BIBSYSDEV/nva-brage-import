package no.unit.nva.brage.importer;

import no.unit.nva.brage.dublincore.DcValue;

public final class DcValueBuilder {
    private transient String element;
    private transient String qualifier;
    private transient String language;
    private transient String value;

    public DcValueBuilder() {
    }

    protected static DcValueBuilder dcValue() {
        return new DcValueBuilder();
    }

    public DcValueBuilder withElement(String element) {
        this.element = element;
        return this;
    }

    public DcValueBuilder withQualifier(String qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public DcValueBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    public DcValueBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public DcValue build() {
        DcValue dcValue = new DcValue();
        dcValue.setElement(element);
        dcValue.setQualifier(qualifier);
        dcValue.setLanguage(language);
        dcValue.setValue(value);
        return dcValue;
    }
}
