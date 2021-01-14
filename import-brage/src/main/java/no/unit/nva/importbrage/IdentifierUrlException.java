package no.unit.nva.importbrage;

import no.unit.nva.importbrage.metamodel.BrageIdentifier;
import no.unit.nva.importbrage.metamodel.BrageSource;

import java.util.List;
import java.util.stream.Collectors;

public class IdentifierUrlException extends Exception {

    public static final String DELIMITER = ", ";

    public IdentifierUrlException(List<BrageIdentifier> sources) {
        super(String.format("There should only be one source url, provided: %s", formatInput(sources)));
    }

    private static String formatInput(List<BrageIdentifier> sources) {
        return sources.stream().map(BrageIdentifier::getValue).collect(Collectors.joining(DELIMITER));
    }
}
