package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

public enum CoverageType {
    SPATIAL("spatial"),
    TEMPORAL("temporal");

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
    public static CoverageType getTypeByName(String typeName) {
        return Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }
}
