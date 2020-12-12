package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum IdentifierType implements ElementType {
    CITATION("citation"),
    CRISTIN("cristin"),
    DOI("doi"),
    ISBN("isbn"),
    ISMN("ismn"),
    ISSN("issn"),
    OTHER("other"),
    PMID("pmid"),
    SLUG("slug"),
    URI("uri"),
    URN("urn"),
    UNQUALIFIED(null);

    public static final String IDENTIFIER = "identifier";
    private final String type;

    IdentifierType(String type) {
        this.type = type;
    }

    @Override
    public String getTypeName() {
        return type;
    }

    /**
     * Get the equivalent DateType by its string representation.
     *
     * @param candidate A string of a DateType.
     * @return A corresponding DateType
     */
    public static IdentifierType getTypeByName(String candidate) throws InvalidQualifierException {
        return (IdentifierType) ElementType.getTypeByName(IDENTIFIER, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
