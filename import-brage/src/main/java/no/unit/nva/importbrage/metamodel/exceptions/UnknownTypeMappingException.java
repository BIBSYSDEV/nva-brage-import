package no.unit.nva.importbrage.metamodel.exceptions;

public class UnknownTypeMappingException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Field type contains type \"%s\" that has no known mapping";

    public UnknownTypeMappingException(String typeName) {
        super(String.format(MESSAGE_TEMPLATE, typeName));
    }
}
