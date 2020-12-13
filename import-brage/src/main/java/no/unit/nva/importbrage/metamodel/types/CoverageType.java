package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum CoverageType implements ElementType {
    SPATIAL("spatial"),
    TEMPORAL("temporal");

    public static final String COVERAGE = "coverage";

    String type;

    CoverageType(String type) {
        this.type = type;
    }

    @Override
    public String getTypeName() {
        return type;
    }

    @Override
    public boolean isLanguageBased() {
        return true;
    }

    /**
     * Get the equivalent CoverageType by its string representation.
     *
     * @param candidate A string of a CoverageType.
     * @return A corresponding CoverageType
     */
    public static CoverageType getTypeByName(String candidate) throws InvalidQualifierException {
        return (CoverageType) ElementType.getTypeByName(COVERAGE, candidate, values(), null);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
