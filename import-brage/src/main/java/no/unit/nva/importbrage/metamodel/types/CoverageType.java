package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum CoverageType implements ElementType {
    SPATIAL("spatial"),
    TEMPORAL("temporal");

    public static final String COVERAGE = "coverage";
    public static final ElementType UNQUALIFIED_TYPE_NOT_ALLOWED = null;

    String type;

    CoverageType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return COVERAGE;
    }

    @Override
    public ElementType[] getValues() {
        return values();
    }

    @Override
    public String getQualifier() {
        return type;
    }

    /**
     * Get the equivalent CoverageType by its string representation.
     *
     * @param candidate A string of a CoverageType.
     * @return A corresponding CoverageType
     */
    public static CoverageType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (CoverageType) ElementType.getTypeByName(COVERAGE,
                candidate,
                values(),
                UNQUALIFIED_TYPE_NOT_ALLOWED,
                value);
    }
}
