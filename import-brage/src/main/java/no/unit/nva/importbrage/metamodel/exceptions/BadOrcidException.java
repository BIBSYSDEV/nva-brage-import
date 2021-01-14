package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageContributor;

public class BadOrcidException extends Exception {

    public static final String MESSAGE_TEMPLATE = "An orcid \"%s\" was present, but it is ambiguous "
            + "because there are multiple authors";

    public BadOrcidException(BrageContributor contributor) {
        super(String.format(MESSAGE_TEMPLATE, contributor.getValue()));
    }
}
