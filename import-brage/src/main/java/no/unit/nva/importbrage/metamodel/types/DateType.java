package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

import static no.unit.nva.importbrage.metamodel.types.DateType.DateValueType.DISCARD_VALUE;
import static no.unit.nva.importbrage.metamodel.types.DateType.DateValueType.STRING;
import static no.unit.nva.importbrage.metamodel.types.DateType.DateValueType.TIMESTAMP;

public enum DateType implements ElementType {
    ACCESSIONED("accessioned", TIMESTAMP),
    AVAILABLE("available", TIMESTAMP),
    COPYRIGHT("copyright", DISCARD_VALUE),
    CREATED("created", TIMESTAMP),  // Cristin import dates
    EMBARGO_END_DATE("embargoenddate", STRING),
    ISSUED("issued", STRING),
    SUBMITTED("submitted", STRING), // only on theses
    SWORD_UPDATED("updated", DISCARD_VALUE),
    UNQUALIFIED("none", STRING);

    public static final String DATE = "date";
    private final String type;
    private final DateValueType valueType;

    DateType(String type, DateValueType valueType) {
        this.type = type;
        this.valueType = valueType;
    }

    @Override
    public String getName() {
        return DATE;
    }

    @Override
    public ElementType[] getValues() {
        return values();
    }

    @Override
    public String getQualifier() {
        return type;
    }

    public DateValueType getValueType() {
        return this.valueType;
    }

    /**
     * Get the equivalent DateType by its string representation.
     *
     * @param candidate A string of a DateType.
     * @return A corresponding DateType
     */
    public static DateType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (DateType) ElementType.getTypeByName(DATE, candidate, values(), UNQUALIFIED, value);
    }

    public enum DateValueType {
        TIMESTAMP,
        STRING,
        DISCARD_VALUE
    }
}
