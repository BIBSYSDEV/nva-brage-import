package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.UnknownTypeValueException;

import java.util.Arrays;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public interface Filterable {

    String DELIMITER = ", ";

    String getTypeName();

    Filterable[] getValues();

    static Filterable getTypeByName(Filterable[] values,
                                    String candidate,
                                    Supplier<UnknownTypeValueException> exceptionSupplier)
            throws Exception {
        return Arrays.stream(values)
                .filter(value -> nonNull(value.getTypeName()))
                .filter(value -> value.getTypeName().equalsIgnoreCase(candidate))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }

    /**
     * Returns a list of allowed values for ElementType implementation.
     * @param values The available values.
     * @return A comma-separated string representation of the values.
     */
    static String getAllowedValues(TypeValue[] values) {
        return Arrays.stream(values)
                .map(Filterable::getTypeName)
                .collect(joining(DELIMITER));
    }
}
