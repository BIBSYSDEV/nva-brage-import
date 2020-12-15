package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.DateType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageDate extends BrageValue {
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
            dc.date                  Use qualified form if possible.
     */

    private final DateType dateType;

    public BrageDate(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageDate(String dateType, String value) throws InvalidQualifierException {
        this(DateType.getTypeByName(dateType), value);
    }

    public BrageDate(DateType dateType, String value) {
        super(value);
        this.dateType = dateType;
    }

    public DateType getDateType() {
        return dateType;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageDate)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageDate brageDate = (BrageDate) o;
        return getDateType() == brageDate.getDateType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDateType());
    }
}
