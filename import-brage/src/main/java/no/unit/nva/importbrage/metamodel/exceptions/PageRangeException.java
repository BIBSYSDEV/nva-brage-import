package no.unit.nva.importbrage.metamodel.exceptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PageRangeException extends Exception {

    public static final String DELIMITER = ", ";
    public static final String MESSAGE_TEMPLATE_TOO_MANY = "Page ranges should have a single value, provided: %s";
    public static final String MESSAGE_TEMPLATE_BADLY_STRUCTURED = "Page ranges should be structured like \"1-23\", "
            + "provided: %s";

    public PageRangeException(List<String> sources) {
        super(String.format(MESSAGE_TEMPLATE_TOO_MANY, formatInput(sources)));
    }

    public PageRangeException(String value) {
        super(String.format(MESSAGE_TEMPLATE_BADLY_STRUCTURED, value));
    }

    @NotNull
    private static String formatInput(List<String> sources) {
        return String.join(DELIMITER, sources);
    }
}
