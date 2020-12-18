package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.types.DateType;

public class InvalidPublicationDateException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Could not generate \"%s\" with value \"%s\" as it does not match "
            + "pattern \"YYYY-MM-DD\"";

    public InvalidPublicationDateException(String value, DateType dateType) {
        super(String.format(MESSAGE_TEMPLATE, dateType, value));
    }
}
