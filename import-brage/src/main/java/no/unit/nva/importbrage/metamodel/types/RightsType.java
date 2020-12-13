package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum RightsType implements ElementType {
    HOLDER("holder"),
    LICENSE("license"), 
    URI("uri"),
    UNQUALIFIED(null);

    public static final String RIGHTS = "rights";
    private final String typeName;

    RightsType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent RightsType by its string representation.
     *
     * @param candidate A string of a RightsType.
     * @return A corresponding RightsType
     */
    public static RightsType getTypeByName(String candidate) throws InvalidQualifierException {
        return (RightsType) ElementType.getTypeByName(RIGHTS, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
