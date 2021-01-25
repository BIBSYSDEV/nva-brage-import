package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public interface ElementType {
    String DELIMITER = ", ";

    String getName();

    ElementType[] getValues();

    String getQualifier();

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
    static ElementType getTypeByName(String type,
                                     String candidate,
                                     ElementType[] values,
                                     ElementType unqualifiedType,
                                     String value)
            throws InvalidQualifierException {
        if (cannotBeUnqualifiedButCandidateIsUnqualified(candidate, unqualifiedType)) {
            throw new InvalidQualifierException(type, candidate, getAllowedValues(values), value);
        }
        return isUnqualified(candidate) ? unqualifiedType : getQualifiedElementType(type, candidate, values, value);
    }

    private static ElementType getQualifiedElementType(String type,
                                                       String candidate,
                                                       ElementType[] values,
                                                       String value)
            throws InvalidQualifierException {
        return Arrays.stream(values)
                .filter(qualifier -> nonNull(qualifier.getQualifier()))
                .filter(qualifier -> qualifier.getQualifier().equalsIgnoreCase(candidate))
                .findFirst()
                .orElseThrow(ElementType.getInvalidQualifierExceptionSupplier(type, candidate, values, value));
    }

    /**
     * Returns a list of allowed values for ElementType implementation.
     * @param values The available values.
     * @return A comma-separated string representation of the values.
     */
    static String getAllowedValues(ElementType[] values) {
        return Arrays.stream(values)
                .map(ElementType::getQualifier)
                .collect(joining(DELIMITER));
    }

    private static Supplier<InvalidQualifierException> getInvalidQualifierExceptionSupplier(String type,
                                                                                            String candidate,
                                                                                            ElementType[] values,
                                                                                            String value) {
        return () -> new InvalidQualifierException(type, candidate, getAllowedValues(values), value);
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
