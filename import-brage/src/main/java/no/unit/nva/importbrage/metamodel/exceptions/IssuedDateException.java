package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BrageValue;

import java.util.List;
import java.util.stream.Collectors;

public class IssuedDateException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Issued date should have one value, the following were provided: %s";
    public static final String DELIMITER = ", ";

    public IssuedDateException(List<BrageDate> dates) {
        super(String.format(MESSAGE_TEMPLATE, formatDates(dates)));
    }

    private static String formatDates(List<BrageDate> dates) {
        return dates.stream().map(BrageValue::getValue).collect(Collectors.joining(DELIMITER));
    }
}
