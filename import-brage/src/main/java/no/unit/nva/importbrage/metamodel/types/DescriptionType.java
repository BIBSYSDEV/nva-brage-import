package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum DescriptionType implements ElementType {
    ABSTRACT("abstract"),
    DEGREE("degree"),
    EMBARGO("embargo"),
    LOCALCODE("localcode"),
    PROVENANCE("provenance"),
    SPONSORSHIP("sponsorship"),
    TABLEOFCONTENTS("tableofcontents"),
    VERSION("version"),
    UNQUALIFIED("none");

    public static final String DESCRIPTION = "description";
    private final String typeName;

    DescriptionType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return DESCRIPTION;
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
     * Get the equivalent DescriptionType by its string representation.
     *
     * @param candidate A string of a DescriptionType.
     * @return A corresponding DescriptionType
     */
    public static DescriptionType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (DescriptionType) ElementType.getTypeByName(DESCRIPTION, candidate, values(), UNQUALIFIED, value);
    }
}
                           