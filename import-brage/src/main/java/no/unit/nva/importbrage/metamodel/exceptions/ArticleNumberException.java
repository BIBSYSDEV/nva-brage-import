package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageSource;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleNumberException extends Exception {

    public static final String DELIMITER = ", ";
    public static final String MESSAGE_TEMPLATE = "Article numbers should be a single value, provided: %s";

    public ArticleNumberException(List<BrageSource> sources) {
        super(String.format(MESSAGE_TEMPLATE, formatInput(sources)));
    }

    private static String formatInput(List<BrageSource> sources) {
        return sources.stream()
                .map(BrageSource::getValue)
                .collect(Collectors.joining(DELIMITER));
    }
}
