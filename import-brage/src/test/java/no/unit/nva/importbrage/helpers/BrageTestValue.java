package no.unit.nva.importbrage.helpers;

import no.unit.nva.importbrage.metamodel.types.ElementType;

public class BrageTestValue {
    private final ElementType qualifier;
    private final String value;
    private final String language;

    public BrageTestValue(ElementType qualifier, String value, String language) {
        this.qualifier = qualifier;
        this.value = value;
        this.language = language;
    }

    public ElementType getQualifier() {
        return qualifier;
    }

    public String getValue() {
        return value;
    }

    public String getLanguage() {
        return language;
    }
}
