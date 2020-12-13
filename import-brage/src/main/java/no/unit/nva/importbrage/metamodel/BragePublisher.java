package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.PublisherType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BragePublisher {
    private final PublisherType publisherType;
    private final String value;

    public BragePublisher(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BragePublisher(String qualifier, String value) throws InvalidQualifierException {
        this(PublisherType.getTypeByName(qualifier), value);
    }

    public BragePublisher(PublisherType publisherType, String value) {
        this.publisherType = publisherType;
        this.value = value;
    }

    public PublisherType getPublisherType() {
        return publisherType;
    }

    public String getValue() {
        return value;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BragePublisher)) {
            return false;
        }
        BragePublisher that = (BragePublisher) o;
        return getPublisherType() == that.getPublisherType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getPublisherType(), getValue());
    }
}
