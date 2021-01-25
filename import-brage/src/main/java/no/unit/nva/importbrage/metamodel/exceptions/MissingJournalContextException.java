package no.unit.nva.importbrage.metamodel.exceptions;

public class MissingJournalContextException extends Exception {
    public MissingJournalContextException() {
        super("No identifiable journal information was found in NSD DBH by ISSN or title");
    }
}
