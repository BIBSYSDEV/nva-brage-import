package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public enum DescriptionType {
    ABSTRACT("abstract"),
    DEGREE("degree"),
    EMBARGO("embargo"),
    LOCALCODE("localcode"),
    PROVENANCE("provenance"),
    SPONSORSHIP("sponsorship"),
    TABLEOFCONTENTS("tableofcontents"),
    VERSION("version"),
    UNQUALIFIED(null);

    public static final String DESCRIPTION = "description";
    public static final String ALLOWED = getAllowedValues();
    public static final String DELIMITER = ", ";
    private final String typeName;

    DescriptionType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Get the equivalent DescriptionType by its string representation.
     *
     * @param typeName A string of a DescriptionType.
     * @return A corresponding DescriptionType
     */
    public static DescriptionType getTypeByName(String typeName) throws InvalidQualifierException {
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> nonNull(value.getTypeName()))
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(getInvalidQualifierExceptionSupplier(typeName));
    }

    public String getTypeName() {
        return typeName;
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String typeName) {
        return () -> new InvalidQualifierException(DESCRIPTION, typeName, ALLOWED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return Arrays.stream(values())
                .map(DescriptionType::getTypeName)
                .collect(joining(DELIMITER));
    }
}
                           