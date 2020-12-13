package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum DateType implements ElementType {
    ACCESSIONED("accessioned"),
    AVAILABLE("available"),
    COPYRIGHT("copyright"),
    CREATED("created"),
    EMBARGO_END_DATE("embargoenddate"),
    ISSUED("issued"),
    SUBMITTED("submitted"),
    SWORD_UPDATED("updated"),
    UNQUALIFIED(null);

    public static final String DATE = "date";
    private final String type;

    DateType(String type) {
        this.type = type;
    }

    @Override
    public String getTypeName() {
        return type;
    }

    @Override
    public boolean isLanguageBased() {
        return false;
    }

    /**
     * Get the equivalent DateType by its string representation.
     *
     * @param candidate A string of a DateType.
     * @return A corresponding DateType
     */
    public static DateType getTypeByName(String candidate) throws InvalidQualifierException {
        return (DateType) ElementType.getTypeByName(DATE, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
