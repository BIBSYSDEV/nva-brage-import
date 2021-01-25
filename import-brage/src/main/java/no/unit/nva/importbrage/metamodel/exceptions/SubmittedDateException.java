package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageDate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SubmittedDateException extends Exception {

    public static final String DELIMITER = ", ";
    public static final String MESSAGE_TEMPLATE = "The submitted date should have a single value, provided: %s";

    public SubmittedDateException(List<BrageDate> submittedDates) {
        super(String.format(MESSAGE_TEMPLATE, formatInput(submittedDates)));
    }

    @NotNull
    private static String formatInput(List<BrageDate> submittedDates) {
        return submittedDates.stream()
                .map(BrageDate::getValue)
                .collect(Collectors.joining(DELIMITER));
    }
}
