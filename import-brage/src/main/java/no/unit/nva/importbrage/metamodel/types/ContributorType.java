package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public enum ContributorType {
    ADVISOR("advisor"),
    AUTHOR("author"),
    DEPARTMENT("department"),
    EDITOR("editor"),
    ILLUSTRATOR("illustrator"),
    ORCID("orcid"),
    OTHER("other"),
    UNQUALIFIED(null);

    public static final String CONTRIBUTOR = "contributor";
    public static final String DELIMITER = ", ";
    public static final String ALLOWED = getAllowedValues();
    private final String typeName;

    ContributorType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Get the equivalent ContributorType by its string representation.
     *
     * @param typeName A string of a ContributorType.
     * @return A corresponding ContributorType
     */
    public static ContributorType getTypeByName(String typeName) throws InvalidQualifierException {
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
        return () -> new InvalidQualifierException(CONTRIBUTOR, typeName, ALLOWED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return Arrays.stream(values())
                .map(ContributorType::getTypeName)
                .collect(joining(DELIMITER));
    }
}
