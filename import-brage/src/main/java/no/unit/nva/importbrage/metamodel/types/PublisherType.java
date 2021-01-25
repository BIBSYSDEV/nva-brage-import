package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum PublisherType implements ElementType {
    UNQUALIFIED("none");

    public static final String PUBLISHER = "publisher";
    private final String typeName;

    PublisherType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return PUBLISHER;
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
     * Get the equivalent PublisherType by its string representation.
     *
     * @param candidate A string of a PublisherType.
     * @return A corresponding PublisherType
     */
    public static PublisherType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (PublisherType) ElementType.getTypeByName(PUBLISHER, candidate, values(), UNQUALIFIED, value);
    }
}
