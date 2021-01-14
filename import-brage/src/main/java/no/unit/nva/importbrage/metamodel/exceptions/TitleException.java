package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageTitle;
import no.unit.nva.importbrage.metamodel.BrageValue;

import java.util.List;
import java.util.stream.Collectors;

public class TitleException extends Exception {

    public static final String DELIMITER = ", ";
    public static final String MESSAGE_TEMPLATE_TOO_MANY = "There should be one title for an item, provided: \"%s\". "
            + "Put alternative titles in Alternative titles";
    public static final String MESSAGE_TOO_FEW = "There should be at least one title for the item";


    public TitleException(List<BrageTitle> mainTitles) {
        super(getMessage(mainTitles));
    }

    private static String getMessage(List<BrageTitle> titles) {
        if (titles.isEmpty()) {
            return MESSAGE_TOO_FEW;
        } else {
            return String.format(MESSAGE_TEMPLATE_TOO_MANY, formatTitles(titles));
        }
    }

    private static String formatTitles(List<BrageTitle> mainTitles) {
        return mainTitles.stream().map(BrageValue::getValue).collect(Collectors.joining(DELIMITER));
    }
}
