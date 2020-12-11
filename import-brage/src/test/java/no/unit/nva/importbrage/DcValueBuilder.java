package no.unit.nva.importbrage;

public final class DcValueBuilder {
    private String element;
    private String qualifier;
    private String language;
    private String value;

    DcValueBuilder() {
    }

    protected static DcValueBuilder dcValue() {
        return new DcValueBuilder();
    }

    protected DcValueBuilder withElement(String element) {
        this.element = element;
        return this;
    }

    protected DcValueBuilder withQualifier(String qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    protected DcValueBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    protected DcValueBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    protected DcValue build() {
        DcValue dcValue = new DcValue();
        dcValue.setElement(element);
        dcValue.setQualifier(qualifier);
        dcValue.setLanguage(language);
        dcValue.setValue(value);
        return dcValue;
    }
}
