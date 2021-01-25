package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BragePublisher;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherException extends Exception {

    public static final String DELIMITER = ", ";
    public static final String MESSAGE_TEMPLATE = "There should only be one publisher, provided: %s";

    public PublisherException(List<BragePublisher> publishers) {
        super(String.format(MESSAGE_TEMPLATE, formatInput(publishers)));
    }

    private static String formatInput(List<BragePublisher> publishers) {
        return publishers.stream().map(BragePublisher::getValue).collect(Collectors.joining(DELIMITER));
    }
}
