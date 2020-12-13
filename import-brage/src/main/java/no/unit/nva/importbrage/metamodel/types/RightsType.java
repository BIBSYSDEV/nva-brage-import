package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum RightsType implements ElementType {
    HOLDER("holder", true),
    LICENSE("license", true),
    URI("uri", false),
    UNQUALIFIED(null, true);

    public static final String RIGHTS = "rights";
    private final String typeName;
    private final boolean languageBased;

    RightsType(String typeName, boolean languageBased) {
        this.typeName = typeName;
        this.languageBased = languageBased;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean isLanguageBased() {
        return this.languageBased;
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
