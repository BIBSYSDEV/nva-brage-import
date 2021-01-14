package no.unit.nva.importbrage.metamodel.exceptions;

public class UnknownLanguageException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Unknown language code: %s";

    public UnknownLanguageException(String code) {
        super(String.format(MESSAGE_TEMPLATE, code));
    }
}
