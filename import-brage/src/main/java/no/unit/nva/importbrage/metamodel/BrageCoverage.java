package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.CoverageType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageCoverage extends BrageLanguageValue {
    /*
      Generated from XML like:

          <dcvalue element="coverage" qualifier="spatial" language="en_US">Norway,&#x20;Oslo</dcvalue>

      Values according to:

          dc.coverage.spatial   Spatial characteristics of content.
          dc.coverage.temporal  Temporal characteristics of content.
    */

    private final CoverageType type;

    public BrageCoverage(String type, String value, String language) throws InvalidQualifierException {
        this(CoverageType.getTypeByName(type, value), value, language);
    }

    public BrageCoverage(CoverageType type, String value, String language) {
        super(value, language);
        this.type = type;
    }

    public BrageCoverage(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public CoverageType getType() {
        return type;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageCoverage)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageCoverage that = (BrageCoverage) o;
        return getType() == that.getType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }
}
