package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum PublisherType implements ElementType {
    UNQUALIFIED(null);

    public static final String PUBLISHER = "publisher";
    private final String typeName;

    PublisherType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent PublisherType by its string representation.
     *
     * @param candidate A string of a PublisherType.
     * @return A corresponding PublisherType
     */
    public static PublisherType getTypeByName(String candidate) throws InvalidQualifierException {
        return (PublisherType) ElementType.getTypeByName(PUBLISHER, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
