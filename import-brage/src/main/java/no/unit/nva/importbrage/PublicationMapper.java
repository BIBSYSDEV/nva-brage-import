package no.unit.nva.importbrage;

import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.model.Publication;
import nva.commons.utils.SingletonCollector;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class PublicationMapper {
    public static Publication fromBragePublication(BragePublication bragePublication) {
        var dates = bragePublication.getDates();
        var nvaPublication = new Publication.Builder()
                .withCreatedDate(getCreatedDate(dates))
                .withModifiedDate(Instant.now())
                .withIdentifier(UUID.randomUUID())
                .withPublishedDate(null)
                .build();
        var identifiers = bragePublication.getIdentifiers();

        return null;
    }

    private static Instant getCreatedDate(java.util.List<BrageDate> dates) {
        var timeString = dates.stream()
                .filter(date -> date.getDateType().equals(DateType.ACCESSIONED))
                .collect(SingletonCollector.collect()).getValue();
        return Timestamp.valueOf(timeString).toInstant();

    }
}
