package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum ProvenanceType implements ElementType {
    UNQUALIFIED("none");

    public static final String PROVENANCE = "provenance";
    private final String typeName;

    ProvenanceType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return PROVENANCE;
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
     * Get the equivalent ProvenanceType by its string representation.
     *
     * @param candidate A string of a ProvenanceType.
     * @return A corresponding ProvenanceType
     */
    public static ProvenanceType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (ProvenanceType) ElementType.getTypeByName(PROVENANCE, candidate, values(), UNQUALIFIED, value);
    }
}
