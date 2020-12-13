package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageCreator extends BrageValue {

    /*
      Generated from XML like:

          <dcvalue element="creator" language="en_US">Randy&#x20;Olsen</dcvalue>

      Values according to:

          dc.creator    Do not use; only for harvested metadata.
    */

    private final CreatorType creatorType;

    public BrageCreator(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageCreator(String creatorType, String value) throws InvalidQualifierException {
        this(CreatorType.getTypeByName(creatorType), value);
    }

    public BrageCreator(CreatorType creatorType, String value) {
        super(value);
        this.creatorType = creatorType;
    }

    public CreatorType getCreatorType() {
        return creatorType;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageCreator)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageCreator that = (BrageCreator) o;
        return getCreatorType() == that.getCreatorType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCreatorType());
    }
}
