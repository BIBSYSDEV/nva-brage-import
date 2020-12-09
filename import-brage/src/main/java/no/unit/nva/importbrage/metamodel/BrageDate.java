package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;

import java.util.Arrays;
import java.util.Locale;

public class BrageDate {
    /*
        Generated from XML like:

          <dcvalue element="date" qualifier="accessioned">2020-12-07T16:46:42Z</dcvalue>
          <dcvalue element="date" qualifier="available">2020-12-07T16:46:42Z</dcvalue>
          <dcvalue element="date" qualifier="issued">2020-12-07</dcvalue>

        Values according to:

            dc.date.accessioned      Do not edit; Date DSpace takes possession of item.
            dc.date.available        Do not edit; Date or date range item became available to the public.
            dc.date.copyright        Date of copyright.
            dc.date.created          Date of creation or manufacture of intellectual content if different
                                     from date.issued.
            dc.date.embargoenddate   Do not use; for embargo setter and lifter in items created in
                                     Dspace 1.8 or earlier.
                                     For imported items only.
            dc.date.issued           Date of publication or distribution.
            dc.date.submitted        For theses/dissertations.
            dc.date.updated          The last time the item was updated via the SWORD interface.
     */

    private DateType dateType;
    private String value;

    public BrageDate(DcValue value) {
        this(value.getQualifier(), value.getValue());
    }

    public BrageDate(String dateType, String value) {
        this(DateType.getTypeByName(dateType), value);
    }

    public BrageDate(DateType dateType, String value) {
        this.dateType = dateType;
        this.value = value;
    }

    public DateType getDateType() {
        return dateType;
    }

    public String getvalue() {
        return value;
    }

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
         * @param typeName A string of a DateType.
         * @return A corresponding DateType
         */
        public static DateType getTypeByName(String typeName) {
            return Arrays.stream(values())
                    .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                    .findFirst().orElseThrow();
        }
    }
}
