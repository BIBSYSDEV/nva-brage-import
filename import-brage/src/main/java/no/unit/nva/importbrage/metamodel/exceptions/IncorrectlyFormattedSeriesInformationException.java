package no.unit.nva.importbrage.metamodel.exceptions;

public class IncorrectlyFormattedSeriesInformationException extends Exception {

    public static final String MESSAGE_TEMPLATE = "The series information \"%s\" should have format "
            + "\"Series title;Series number\", e.g. \"Physics annals;99\"";

    public IncorrectlyFormattedSeriesInformationException(String seriesInformation) {
        super(String.format(MESSAGE_TEMPLATE, seriesInformation));
    }
}
