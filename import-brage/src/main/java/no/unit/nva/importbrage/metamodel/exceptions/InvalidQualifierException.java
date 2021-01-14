package no.unit.nva.importbrage.metamodel.exceptions;

public class InvalidQualifierException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The %s with value \"%s\" has qualifier \"%s\", allowed values: %s";

    public InvalidQualifierException(String qualifierType, String qualifiedValue, String allowed, String value) {
        super(String.format(MESSAGE_TEMPLATE, qualifierType, value, qualifiedValue, allowed));
    }
}
