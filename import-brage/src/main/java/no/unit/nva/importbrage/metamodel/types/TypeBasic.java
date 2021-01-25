package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

/*
     dc.type    Nature or genre of content.
 */
public enum TypeBasic implements ElementType {
    UNQUALIFIED("none");

    public static final String TYPE = "type";
    private final String typeName;

    TypeBasic(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return TYPE;
    }

    @Override
    public ElementType[] getValues() {
        return values();
    }

    @Override
    public String getQualifier() {
        return typeName;
    }

    /**
     * Get the equivalent TypeBasic by its string representation.
     *
     * @param candidate A string of a TypeBasic.
     * @return A corresponding TypeBasic
     */
    public static TypeBasic getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (TypeBasic) ElementType.getTypeByName(TYPE, candidate, values(), UNQUALIFIED, value);
    }
}
