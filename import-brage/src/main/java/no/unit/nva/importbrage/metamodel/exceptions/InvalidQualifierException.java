package no.unit.nva.importbrage.metamodel.exceptions;

public class InvalidQualifierException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The qualifier for type %s had value %s (allowed values %s)";

    public InvalidQualifierException(String qualifierType, String value, String allowed) {
        super(String.format(MESSAGE_TEMPLATE, qualifierType, value, allowed));
    }
}
