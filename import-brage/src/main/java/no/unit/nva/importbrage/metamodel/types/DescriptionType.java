package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

import static java.util.Objects.isNull;

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
    public static DescriptionType getTypeByName(String typeName) {
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }

    public String getTypeName() {
        return typeName;
    }

}
                           