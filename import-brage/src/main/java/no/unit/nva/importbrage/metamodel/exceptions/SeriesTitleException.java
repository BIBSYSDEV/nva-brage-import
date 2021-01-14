package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageRelation;
import no.unit.nva.importbrage.metamodel.BrageValue;

import java.util.List;
import java.util.stream.Collectors;

public class SeriesTitleException extends Exception {

    public static final String DELIMITER = ", ";
    public static final String MESSAGE_TEMPLATE = "There should only be one series title, provided: \"%s\"";

    public SeriesTitleException(List<BrageRelation> seriesTitles) {
        super(String.format(MESSAGE_TEMPLATE, formatInput(seriesTitles)));
    }

    private static String formatInput(List<BrageRelation> seriesTitles) {
        return seriesTitles.stream().map(BrageValue::getValue).collect(Collectors.joining(DELIMITER));
    }
}
