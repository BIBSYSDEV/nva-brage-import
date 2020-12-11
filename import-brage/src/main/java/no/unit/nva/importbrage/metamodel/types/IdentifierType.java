package no.unit.nva.importbrage.metamodel.types;

import java.util.Arrays;
import java.util.Locale;

import static java.util.Objects.isNull;

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
    URN("urn"),
    UNQUALIFIED(null);

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
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }
}
