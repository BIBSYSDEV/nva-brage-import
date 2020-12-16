package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.types.DateType;

public class InvalidTimestampException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Could not generate \"%s\" with value \"%s\" as it does not match "
            + "pattern \"YYYY-MM-DDTHH-mm-ss.nnnZ\"";

    public InvalidTimestampException(String value, DateType valueType) {
        super(String.format(MESSAGE_TEMPLATE, valueType, value));
    }
}
