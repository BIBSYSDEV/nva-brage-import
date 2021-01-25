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
    UNQUALIFIED("none");

    public static final String IDENTIFIER = "identifier";
    private final String type;

    IdentifierType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return IDENTIFIER;
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
     * Get the equivalent DateType by its string representation.
     *
     * @param candidate A string of a DateType.
     * @return A corresponding DateType
     */
    public static IdentifierType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (IdentifierType) ElementType.getTypeByName(IDENTIFIER, candidate, values(), UNQUALIFIED, value);
    }
}
