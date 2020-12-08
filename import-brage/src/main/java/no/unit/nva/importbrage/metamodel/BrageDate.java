package no.unit.nva.importbrage.metamodel;

public class BrageDate {
    /*
        dc.date.accessioned	    Do not edit; Date DSpace takes possession of item.
        dc.date.available	    Do not edit; Date or date range item became available to the public.
        dc.date.copyright	    Date of copyright.
        dc.date.created	        Date of creation or manufacture of intellectual content if different from date.issued.
        dc.date.embargoenddate	Do not use; for embargo setter and lifter in items created in Dspace 1.8 or earlier.
                                For imported items only.
        dc.date.issued	        Date of publication or distribution.
        dc.date.submitted	    For theses/dissertations.
        dc.date.updated	        The last time the item was updated via the SWORD interface.
     */

    private DateType dateType;
    private String value;

    public enum DateType {
        AVAILABLE("available"),
        COPYRIGHT("copyright"),
        CREATED("created"),
        EMBARGO_END_DATE("embargoenddate"),
        ISSUED("issued"),
        SUBMITTED("submitted"),
        SWORD_UPDATED("updated");

        private String type;

        DateType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
