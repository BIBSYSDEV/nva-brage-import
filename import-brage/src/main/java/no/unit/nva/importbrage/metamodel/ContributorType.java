package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

import static java.util.Objects.isNull;

public enum ContributorType {
    ADVISOR("advisor"),
    AUTHOR("author"),
    DEPARTMENT("department"),
    EDITOR("editor"),
    ILLUSTRATOR("illustrator"),
    ORCID("orcid"),
    OTHER("other"),
    UNQUALIFIED(null);

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
    public static ContributorType getTypeByName(String typeName) {
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }

    public String getTypeName() {
        return typeName;
    }
}
