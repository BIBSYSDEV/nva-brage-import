package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum FormatType implements ElementType {
    EXTENT("extent"),
    MEDIUM("medium"),
    MIMETYPE("mimetype"),
    UNQUALIFIED(null);

    public static final String FORMAT = "format";
    private final String typeName;

    FormatType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Returns a comma-separated string representation of possible values.
     * @return A string representation of possible values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent FormatType by its string representation.
     *
     * @param candidate A string of a FormatType.
     * @return A corresponding FormatType
     */
    public static FormatType getTypeByTypeName(String candidate) throws InvalidQualifierException {
        return (FormatType) ElementType.getTypeByName(FORMAT, candidate, values(), UNQUALIFIED);
    }
}
