package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

public enum IdentifierType {
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
    URN("urn");

    private final String type;

    IdentifierType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return type;
    }

    /**
     * Get the equivalent DateType by its string representation.
     *
     * @param typeName A string of a DateType.
     * @return A corresponding DateType
     */
    public static IdentifierType getTypeByName(String typeName) {
        return Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }
}
