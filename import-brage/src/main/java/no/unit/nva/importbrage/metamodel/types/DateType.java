package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public enum DateType {
    ACCESSIONED("accessioned"),
    AVAILABLE("available"),
    COPYRIGHT("copyright"),
    CREATED("created"),
    EMBARGO_END_DATE("embargoenddate"),
    ISSUED("issued"),
    SUBMITTED("submitted"),
    SWORD_UPDATED("updated"),
    UNQUALIFIED(null);

    public static final String DATE = "date";
    public static final String ALLOWED = getAllowedValues();
    public static final String DELIMITER = ", ";
    private final String type;

    DateType(String type) {
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
    public static DateType getTypeByName(String typeName) throws InvalidQualifierException {
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> nonNull(value.getTypeName()))
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(getInvalidQualifierExceptionSupplier(typeName));
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String typeName) {
        return () -> new InvalidQualifierException(DATE, typeName, ALLOWED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return Arrays.stream(values())
                .map(DateType::getTypeName)
                .collect(joining(DELIMITER));
    }
}
