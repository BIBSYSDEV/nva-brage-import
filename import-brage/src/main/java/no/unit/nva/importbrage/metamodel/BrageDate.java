package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.IllegalDateConversionException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidPublicationDateException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidTimestampException;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.model.PublicationDate;
import nva.commons.utils.JacocoGenerated;

import java.time.Instant;
import java.util.Objects;

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

public class BrageDate extends BrageValue {
    public static final String DATE_ELEMENT_DELIMITER = "-";
    public static final String ISO_DATE_REGEX = "^\\d{4}(-\\d{2}(-\\d{2})?)?$";
    public static final int YEAR_INDEX = 0;
    public static final int YEAR_ONLY = 1;
    public static final int MONTH_INDEX = 1;
    public static final int YEAR_MONTH_ONLY = 3;
    public static final int DAY_INDEX = 2;

    private final DateType dateType;

    public BrageDate(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageDate(String dateType, String value) throws InvalidQualifierException {
        this(DateType.getTypeByName(dateType, value), value);
    }

    public BrageDate(DateType dateType, String value) {
        super(value);
        this.dateType = dateType;
    }

    public boolean isSubmittedDate() {
        return dateType.equals(DateType.SUBMITTED);
    }

    public DateType getDateType() {
        return dateType;
    }

    /**
     * Converts BrageDate to Instant.
     * @return Instant representation of BrageDate.
     * @throws InvalidTimestampException If the input is not a valid timestamp.
     * @throws IllegalDateConversionException If the DateType cannot be converted to an Instant.
     */
    public Instant asInstant() throws InvalidTimestampException, IllegalDateConversionException {
        String value = getValue();
        if (isNotInstantDate()) {
            throw new IllegalDateConversionException(value, dateType);
        }
        try {
            return Instant.parse(value);
        } catch (Exception e) {
            throw new InvalidTimestampException(value, dateType);
        }
    }

    /**
     * Converts a BrageDate tp an NVA PublicationDate.
     * @return PublicationDate
     * @throws InvalidPublicationDateException If the input cannot be converted to an NVA PublicationDate.
     * @throws IllegalDateConversionException If the DateType cannot be converted to an NVA PublicationDate.
     */
    public PublicationDate asPublicationDate() throws InvalidPublicationDateException, IllegalDateConversionException {
        if (isNotStringDate()) {
            throw new IllegalDateConversionException(getValue(), dateType);
        }
        return convertToPublicationDate();
    }

    public boolean isIssuedDate() {
        return DateType.ISSUED.equals(dateType);
    }

    private PublicationDate convertToPublicationDate() throws InvalidPublicationDateException {
        validate(getValue());

        var rawDate = getValue().split(DATE_ELEMENT_DELIMITER);

        return new PublicationDate.Builder()
                .withYear(getYear(rawDate))
                .withMonth(getMonth(rawDate))
                .withDay(getDay(rawDate))
                .build();
    }

    private String getDay(String[] rawDate) {
        return rawDate.length == YEAR_MONTH_ONLY ? rawDate[DAY_INDEX] : null;
    }

    private String getYear(String[] rawDate) {
        return rawDate[YEAR_INDEX];
    }

    private String getMonth(String[] rawDate) {
        return rawDate.length > YEAR_ONLY ? rawDate[MONTH_INDEX] : null;
    }

    private boolean isNotInstantDate() {
        return !getDateType().getValueType().equals(DateType.DateValueType.TIMESTAMP);
    }

    private boolean isNotStringDate() {
        return !getDateType().getValueType().equals(DateType.DateValueType.STRING);
    }

    private void validate(String value) throws InvalidPublicationDateException {
        if (!value.matches(ISO_DATE_REGEX)) {
            throw new InvalidPublicationDateException(value, dateType);
        }
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
