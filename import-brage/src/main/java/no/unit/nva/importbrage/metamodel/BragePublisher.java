package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.PublisherType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BragePublisher extends BrageLanguageValue {
    private final PublisherType publisherType;

    public BragePublisher(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BragePublisher(String qualifier, String value, String language) throws InvalidQualifierException {
        this(PublisherType.getTypeByName(qualifier, value), value, language);
    }

    public BragePublisher(PublisherType publisherType, String value, String language) {
        super(value, language);
        this.publisherType = publisherType;
    }

    public PublisherType getPublisherType() {
        return publisherType;
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
        if (!super.equals(o)) {
            return false;
        }
        BragePublisher that = (BragePublisher) o;
        return getPublisherType() == that.getPublisherType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPublisherType());
    }
}
