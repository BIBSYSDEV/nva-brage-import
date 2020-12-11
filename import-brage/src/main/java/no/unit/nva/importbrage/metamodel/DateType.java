package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

import static java.util.Objects.isNull;

public enum DateType {
    ACCESSIONED("accessioned"),
    AVAILABLE("available"),
    COPYRIGHT("copyright"),
    CREATED("created"),
    EMBARGO_END_DATE("embargoenddate"),
    ISSUED("issued"),
    SUBMITTED("submitted"),
    SWORD_UPDATED("updated"),
    UNQUALIFIED(null);

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
        return isNull(typeName) || typeName.isEmpty() ? UNQUALIFIED
                : Arrays.stream(values())
                .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
    }
}
