package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

/*
     dc.type    Nature or genre of content.
 */
public enum TypeBasic implements ElementType {
    UNQUALIFIED(null);

    public static final String TYPE = "type";
    private final String typeName;

    TypeBasic(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean isLanguageBased() {
        return true;
    }

    /**
     * Get the equivalent TypeBasic by its string representation.
     *
     * @param candidate A string of a TypeBasic.
     * @return A corresponding TypeBasic
     */
    public static TypeBasic getTypeByName(String candidate) throws InvalidQualifierException {
        return (TypeBasic) ElementType.getTypeByName(TYPE, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
