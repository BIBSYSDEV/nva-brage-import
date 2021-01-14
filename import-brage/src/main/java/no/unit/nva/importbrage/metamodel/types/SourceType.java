package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum SourceType implements ElementType {
    ARTICLE_NUMBER("articlenumber"),
    ISSUE("issue"),
    JOURNAL("journal"),
    PAGE_NUMBER("pagenumber"),
    URI("uri"),
    VOLUME("volume"),
    UNQUALIFIED("none");

    public static final String SOURCE = "source";
    private final String typeName;

    SourceType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return SOURCE;
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
     * Get the equivalent SourceType by its string representation.
     *
     * @param candidate A string of a SourceType.
     * @return A corresponding SourceType
     */
    public static SourceType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (SourceType) ElementType.getTypeByName(SOURCE, candidate, values(), UNQUALIFIED, value);
    }
}
