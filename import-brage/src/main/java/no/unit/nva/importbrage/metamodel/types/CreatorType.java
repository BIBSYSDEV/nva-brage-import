package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;

public enum CreatorType {
    UNQUALIFIED(null);

    private static final String ALLOWED = getAllowedValues();
    public static final String CREATOR = "creator";
    public static final String DELIMITER = ", ";
    private final String typeName;

    CreatorType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent CreatorType by its string representation.
     *
     * @param typeName A string of a CreatorType.
     * @return A corresponding CreatorType
     */
    public static CreatorType getTypeByName(String typeName) throws InvalidQualifierException {
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(Objects::isNull)
                .findFirst().orElseThrow(getInvalidQualifierExceptionSupplier(typeName));
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String typeName) {
        return () -> new InvalidQualifierException(CREATOR, typeName, ALLOWED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return Arrays.stream(values())
                .map(CreatorType::getTypeName)
                .collect(joining(DELIMITER));
    }
}
