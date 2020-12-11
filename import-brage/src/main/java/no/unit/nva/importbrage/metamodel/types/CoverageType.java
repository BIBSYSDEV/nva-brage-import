package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;

public enum CoverageType {
    SPATIAL("spatial"),
    TEMPORAL("temporal");

    public static final String COVERAGE = "coverage";
    public static final String DELIMITER = ", ";
    public static final String ALLOWED = getAllowedValues();

    String type;

    CoverageType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return type;
    }

    /**
     * Get the equivalent CoverageType by its string representation.
     *
     * @param typeName A string of a CoverageType.
     * @return A corresponding CoverageType
     */
    public static CoverageType getTypeByName(String typeName) throws InvalidQualifierException {
        if (isNull(typeName) || typeName.isEmpty()) {
            throw new InvalidQualifierException(COVERAGE, typeName, ALLOWED);
        }
        return Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(getInvalidQualifierExceptionSupplier(typeName));
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String typeName) {
        return () -> new InvalidQualifierException(COVERAGE, typeName, ALLOWED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return Arrays.stream(values())
                .map(CoverageType::getTypeName)
                .collect(joining(DELIMITER));
    }
}
