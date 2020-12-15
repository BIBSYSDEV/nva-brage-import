package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
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
        if (cannotBeUnqualifiedButCandidateIsUnqualified(candidate, unqualifiedType)) {
            throw new InvalidQualifierException(type, candidate, getAllowedValues(values));
        }
        return isUnqualified(candidate) ? unqualifiedType : getQualifiedElementType(type, candidate, values);
    }

    private static ElementType getQualifiedElementType(String type, String candidate, ElementType[] values)
            throws InvalidQualifierException {
        return Arrays.stream(values)
                .filter(value -> nonNull(value.getTypeName()))
                .filter(value -> value.getTypeName().equalsIgnoreCase(candidate))
                .findFirst()
                .orElseThrow(ElementType.getInvalidQualifierExceptionSupplier(type, candidate, values));
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

    private static boolean cannotBeUnqualifiedButCandidateIsUnqualified(String candidate, ElementType unqualifiedType) {
        return canNotBeUnqualified(unqualifiedType) && isUnqualified(candidate);
    }

    private static boolean canNotBeUnqualified(ElementType unqualifiedType) {
        return isNull(unqualifiedType);
    }

    static boolean isUnqualified(String candidate) {
        return isNull(candidate) || candidate.isEmpty();
    }
}
