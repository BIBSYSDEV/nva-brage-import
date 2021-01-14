package no.unit.nva.importbrage.metamodel.exceptions;

import no.unit.nva.importbrage.metamodel.BrageSource;

import java.util.List;
import java.util.stream.Collectors;

public class JournalVolumeException extends Exception {
        public static final String DELIMITER = ", ";
        public static final String MESSAGE_TEMPLATE = "Journal Volume data contains multiple values: %s";

    public JournalVolumeException(List<BrageSource> sources) {
            super(String.format(MESSAGE_TEMPLATE, formatInput(sources)));
        }

        private static String formatInput(List<BrageSource> sources) {
            return sources.stream().map(BrageSource::getValue).collect(Collectors.joining(DELIMITER));
        }
}
