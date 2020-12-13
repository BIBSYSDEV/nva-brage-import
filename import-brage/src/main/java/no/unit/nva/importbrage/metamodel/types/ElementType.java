package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public interface ElementType {
    String DELIMITER = ", ";

    String getTypeName();

    boolean isLanguageBased();

    /**
     * Gets a ElementType implementation by its string name.
     *
     * @param type The ElementType implementation string name.
     * @param candidate The candidate string name.
     * @param values The potential values an ElementType implementation can have.
     * @param unqualifiedType The Unqualified type of the ElementType implementation, if present, or null.
     * @return An ElementType.
     * @throws InvalidQualifierException If the candidate does not match a valid ElementType in the values.
     */
    static ElementType getTypeByName(String type, String candidate, ElementType[] values, ElementType unqualifiedType)
            throws InvalidQualifierException {
        if (isNull(unqualifiedType) && isNullOrEmpty(candidate)) {
            throw new InvalidQualifierException(type, candidate, getAllowedValues(values));
        }
        return isNullOrEmpty(candidate) ? unqualifiedType
                : Arrays.stream(values)
                .filter(value -> nonNull(value.getTypeName()))
                .filter(value -> value.getTypeName().equals(candidate.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(ElementType.getInvalidQualifierExceptionSupplier(type, candidate, values));
    }

    static boolean isNullOrEmpty(String candidate) {
        return isNull(candidate) || candidate.isEmpty();
    }

    /**
     * Returns a list of allowed values for ElementType implementation.
     * @param values The available values.
     * @return A comma-separated string representation of the values.
     */
    static String getAllowedValues(ElementType[] values) {
        return Arrays.stream(values)
                .map(ElementType::getTypeName)
                .collect(joining(DELIMITER));
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String type,
                                                                                            String candidate,
                                                                                            ElementType[] values) {
        return () -> new InvalidQualifierException(type, candidate, getAllowedValues(values));
    }

}
