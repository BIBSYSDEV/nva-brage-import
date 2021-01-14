package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum CreatorType implements ElementType {
    UNQUALIFIED("none");

    public static final String CREATOR = "creator";
    private final String typeName;

    CreatorType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return CREATOR;
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
     * Get the equivalent CreatorType by its string representation.
     *
     * @param candidate A string of a CreatorType.
     * @return A corresponding CreatorType
     */
    public static CreatorType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (CreatorType) ElementType.getTypeByName(CREATOR, candidate, values(), UNQUALIFIED, value);
    }
}
