package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.model.PublicationDate;

public class RandomPublicationDate {
    public static final String EN_US = "en_US";
    public static final String DATE_TEMPLATE = "%s-%s-%s";
    private final PublicationDate publicationDate;
    private final DcValue dcValue;

    public RandomPublicationDate(DateType dateType) {
        this(generateRandomDate(), dateType);
    }

    private RandomPublicationDate(PublicationDate publicationDate, DateType dateType) {
        this.publicationDate = publicationDate;
        this.dcValue = generateDcValue(publicationDate, dateType);
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    public DcValue getDcValue() {
        return dcValue;
    }

    private static PublicationDate generateRandomDate() {
        return new PublicationDate.Builder()
                .withYear("2020")
                .withMonth("12")
                .withDay("22")
                .build();
    }

    private DcValue generateDcValue(PublicationDate publicationDate, DateType dateType) {
        return new DcValueBuilder()
                .withElement(DateType.DATE)
                .withLanguage(EN_US)
                .withQualifier(dateType.getQualifier())
                .withValue(getFormattedIsoDate(publicationDate))
                .build();
    }

    private String getFormattedIsoDate(PublicationDate publicationDate) {
        return String.format(DATE_TEMPLATE, publicationDate.getYear(),
                publicationDate.getMonth(),
                publicationDate.getDay());
    }
}
