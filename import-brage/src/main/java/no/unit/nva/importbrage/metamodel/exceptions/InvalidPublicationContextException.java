package no.unit.nva.importbrage.metamodel.exceptions;

public class InvalidPublicationContextException extends Exception {
    public InvalidPublicationContextException() {
        super("No publication context information was provided");
    }
}
