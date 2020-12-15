package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum ProvenanceType implements ElementType {
    UNQUALIFIED(null);

    public static final String PROVENANCE = "provenance";
    private final String typeName;

    ProvenanceType(String typeName) {
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
     * Get the equivalent ProvenanceType by its string representation.
     *
     * @param candidate A string of a ProvenanceType.
     * @return A corresponding ProvenanceType
     */
    public static ProvenanceType getTypeByName(String candidate) throws InvalidQualifierException {
        return (ProvenanceType) ElementType.getTypeByName(PROVENANCE, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
