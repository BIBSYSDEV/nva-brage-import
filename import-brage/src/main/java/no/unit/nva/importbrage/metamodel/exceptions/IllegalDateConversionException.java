package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.types.DateType;

public class IllegalDateConversionException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The date string \"%s\" cannot be converted for type \"%s\"";

    public IllegalDateConversionException(String value, DateType type) {
        super(String.format(MESSAGE_TEMPLATE, value, type));
    }
}
