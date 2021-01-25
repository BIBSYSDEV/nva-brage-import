package no.unit.nva.importbrage.metamodel;

import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class BrageValue {
    private final String value;

    public BrageValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return nonNull(value) ? value.trim() : null;
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
