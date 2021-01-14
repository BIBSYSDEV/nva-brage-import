package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageLanguage;

import java.util.List;
import java.util.stream.Collectors;

public class LanguageException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Multiple languages are registered \"%s\", "
            + "where only one main language is allowed.";
    public static final String DELIMITER = ", ";

    public LanguageException(List<BrageLanguage> languages) {
        super(String.format(MESSAGE_TEMPLATE, getLanguageValues(languages)));
    }

    private static String getLanguageValues(List<BrageLanguage> languages) {
        return languages.stream().map(BrageLanguage::getValue).collect(Collectors.joining(DELIMITER));
    }
}
