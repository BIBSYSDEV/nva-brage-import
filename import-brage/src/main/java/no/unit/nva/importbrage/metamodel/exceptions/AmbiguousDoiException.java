package no.unit.nva.importbrage.metamodel.exceptions;

public class AmbiguousDoiException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Multiple DOIs exist that cannot be disambiguated: %s";

    public AmbiguousDoiException(String dois) {
        super(String.format(MESSAGE_TEMPLATE, dois));
    }
}
