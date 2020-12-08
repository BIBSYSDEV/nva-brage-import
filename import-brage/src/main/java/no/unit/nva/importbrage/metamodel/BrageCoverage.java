package no.unit.nva.importbrage.metamodel;

import java.util.Arrays;
import java.util.Locale;

public class BrageCoverage {
    /*
      Generated from XML like:

          <dcvalue element="coverage" qualifier="spatial" language="en_US">Norway,&#x20;Oslo</dcvalue>

      Values according to:

          dc.coverage.spatial   Spatial characteristics of content.
          dc.coverage.temporal  Temporal characteristics of content.
    */

    private CoverageType type;
    private String value;

    public BrageCoverage(String type, String value) {
        this(CoverageType.getTypeByName(type), value);
    }

    public BrageCoverage(CoverageType type, String value) {
        this.type = type;
        this.value = value;
    }

    public CoverageType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public enum CoverageType {
        SPATIAL("spatial"),
        TEMPORAL("temporal");

        String type;

        CoverageType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        /**
         * Get the equivalent CoverageType by its string representation.
         * @param typeName A string of a CoverageType.
         * @return A corresponding CoverageType
         */
        public static CoverageType getTypeByName(String typeName) {
            return Arrays.stream(values())
                    .filter(value -> value.getType().equals(typeName.toLowerCase(Locale.ROOT)))
                    .findFirst().orElseThrow();
        }
    }
}
