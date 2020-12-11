package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageCoverage {
    /*
      Generated from XML like:

          <dcvalue element="coverage" qualifier="spatial" language="en_US">Norway,&#x20;Oslo</dcvalue>

      Values according to:

          dc.coverage.spatial   Spatial characteristics of content.
          dc.coverage.temporal  Temporal characteristics of content.
    */

    private final CoverageType type;
    private final String value;

    public BrageCoverage(String type, String value) {
        this(CoverageType.getTypeByName(type), value);
    }

    public BrageCoverage(CoverageType type, String value) {
        this.type = type;
        this.value = value;
    }

    public BrageCoverage(DcValue value) {
        this(value.getQualifier(), value.getValue());
    }

    public CoverageType getType() {
        return type;
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
        if (!(o instanceof BrageCoverage)) {
            return false;
        }
        BrageCoverage that = (BrageCoverage) o;
        return getType() == that.getType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getType(), getValue());
    }
}
