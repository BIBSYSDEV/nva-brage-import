package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

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

    public static final String IDENTIFIER = "identifier";
    public static final String ALLOWED = getAllowedValues();
    public static final String DELIMITER = ", ";
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
    public static IdentifierType getTypeByName(String typeName) throws InvalidQualifierException {
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> nonNull(value.getTypeName()))
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(getInvalidQualifierExceptionSupplier(typeName));
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String typeName) {
        return () -> new InvalidQualifierException(IDENTIFIER, typeName, ALLOWED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return Arrays.stream(values())
                .map(IdentifierType::getTypeName)
                .collect(joining(DELIMITER));
    }
}
