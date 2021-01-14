package no.unit.nva.importbrage.metamodel.exceptions;

public class DoiException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The provided DOI \"%s\" should match the pattern "
            + "\"https://doi.org/10.000/123123\"";

    public DoiException(String value) {
        super(String.format(MESSAGE_TEMPLATE, value));
    }
}
