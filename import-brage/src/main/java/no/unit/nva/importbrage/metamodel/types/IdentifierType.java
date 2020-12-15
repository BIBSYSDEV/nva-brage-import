package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum IdentifierType implements ElementType {
    CITATION("citation", true),
    CRISTIN("cristin", false),
    DOI("doi", false),
    ISBN("isbn", false),
    ISMN("ismn", false),
    ISSN("issn", false),
    OTHER("other", false),
    PMID("pmid", false),
    SLUG("slug", false),
    URI("uri", false),
    URN("urn", false),
    UNQUALIFIED(null, false);

    public static final String IDENTIFIER = "identifier";
    private final String type;
    private final boolean languageBased;

    IdentifierType(String type, boolean languageBased) {
        this.type = type;
        this.languageBased = languageBased;
    }

    @Override
    public String getTypeName() {
        return type;
    }

    @Override
    public boolean isLanguageBased() {
        return this.languageBased;
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
