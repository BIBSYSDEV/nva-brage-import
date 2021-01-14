package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum FormatType implements ElementType {
    EXTENT("extent"),
    MEDIUM("medium"),
    MIMETYPE("mimetype"),
    UNQUALIFIED("none");

    public static final String FORMAT = "format";
    private final String typeName;

    FormatType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return FORMAT;
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
     * Get the equivalent FormatType by its string representation.
     *
     * @param candidate A string of a FormatType.
     * @return A corresponding FormatType
     */
    public static FormatType getTypeByTypeName(String candidate, String value) throws InvalidQualifierException {
        return (FormatType) ElementType.getTypeByName(FORMAT, candidate, values(), UNQUALIFIED, value);
    }
}
