package no.unit.nva.importbrage.metamodel;

import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageLanguageValue extends BrageValue {
    private final String language;

    public BrageLanguageValue(String value, String language) {
        super(value);
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageLanguageValue)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageLanguageValue that = (BrageLanguageValue) o;
        return Objects.equals(getLanguage(), that.getLanguage());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLanguage());
    }
}
