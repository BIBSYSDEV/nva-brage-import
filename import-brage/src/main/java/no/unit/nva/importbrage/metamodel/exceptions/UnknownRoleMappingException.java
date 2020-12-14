package no.unit.nva.importbrage.metamodel.exceptions;

public class UnknownRoleMappingException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The type %s is an unknown mapping";

    public UnknownRoleMappingException(String type) {
        super(String.format(MESSAGE_TEMPLATE, type));
    }
}
