package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum SourceType implements ElementType {
    ARTICLE_NUMBER("articlenumber"),
    ISSUE("issue"),
    JOURNAL("journal"),
    PAGE_NUMBER("pagenumber"),
    URI("uri"),
    VOLUME("volume"),
    UNQUALIFIED(null);

    public static final String SOURCE = "source";
    private final String typeName;

    SourceType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent SourceType by its string representation.
     *
     * @param candidate A string of a SourceType.
     * @return A corresponding SourceType
     */
    public static SourceType getTypeByName(String candidate) throws InvalidQualifierException {
        return (SourceType) ElementType.getTypeByName(SOURCE, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
