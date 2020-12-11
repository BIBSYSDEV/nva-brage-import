package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

public enum DateType {
    ACCESSIONED("accessioned"),
    AVAILABLE("available"),
    COPYRIGHT("copyright"),
    CREATED("created"),
    EMBARGO_END_DATE("embargoenddate"),
    ISSUED("issued"),
    SUBMITTED("submitted"),
    SWORD_UPDATED("updated");

    private final String type;

    DateType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return type;
    }

    /**
     * Get the equivalent DateType by its string representation.
     *
     * @param typeName A string of a DateType.
     * @return A corresponding DateType
     */
    public static DateType getTypeByName(String typeName) {
        return Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }
}
