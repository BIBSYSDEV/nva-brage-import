package no.unit.nva.importbrage.metamodel.exceptions;

public class UnknownTypeValueException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The field \"type\" has value \"%s\", "
            + "which is not one of the allowed values: %s";

    public UnknownTypeValueException(String candidate, String allowedValues) {
        super(String.format(MESSAGE_TEMPLATE, candidate, allowedValues));
    }
}
