package no.unit.nva.importbrage.metamodel.exceptions;

import java.util.List;

public class PagesException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Source.pagenumber or Format.extent should contain a string of type "
            + "\"123\" or \"1-23\", whereas the following was found: %s";
    public static final String DELIMITER = ", ";

    public PagesException(List<String> pages) {
        super(String.format(MESSAGE_TEMPLATE, formatInput(pages)));
    }

    private static String formatInput(List<String> pages) {
        return String.join(DELIMITER, pages);
    }
}
