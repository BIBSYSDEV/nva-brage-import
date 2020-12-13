package no.unit.nva.importbrage.metamodel;

import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageValue {
    private final String value;

    public BrageValue(String value) {
        this.value = value;
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
        if (!(o instanceof BrageValue)) {
            return false;
        }
        BrageValue that = (BrageValue) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
