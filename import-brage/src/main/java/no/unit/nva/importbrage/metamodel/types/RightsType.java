package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum RightsType implements ElementType {
    HOLDER("holder"),
    LICENSE("license"),
    URI("uri"),
    UNQUALIFIED("none");

    public static final String RIGHTS = "rights";
    private final String typeName;

    RightsType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return RIGHTS;
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
     * Get the equivalent RightsType by its string representation.
     *
     * @param candidate A string of a RightsType.
     * @return A corresponding RightsType
     */
    public static RightsType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (RightsType) ElementType.getTypeByName(RIGHTS, candidate, values(), UNQUALIFIED, value);
    }
}
