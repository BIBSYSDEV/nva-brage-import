package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

import static java.util.Objects.nonNull;
import static no.unit.nva.importbrage.metamodel.types.SourceType.ARTICLE_NUMBER;
import static no.unit.nva.importbrage.metamodel.types.SourceType.ISSUE;
import static no.unit.nva.importbrage.metamodel.types.SourceType.JOURNAL;
import static no.unit.nva.importbrage.metamodel.types.SourceType.PAGE_NUMBER;
import static no.unit.nva.importbrage.metamodel.types.SourceType.VOLUME;

public class BrageSource extends BrageLanguageValue {
    private final SourceType sourceType;

    public BrageSource(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageSource(String qualifier, String value, String language) throws InvalidQualifierException {
        this(SourceType.getTypeByName(qualifier, value), value, language);
    }

    public BrageSource(SourceType sourceType, String value, String language) {
        super(value, language);
        this.sourceType = sourceType;
    }

    public boolean isPageNumber() {
        return PAGE_NUMBER.equals(sourceType);
    }

    public boolean isVolume() {
        return VOLUME.equals(sourceType);
    }

    public boolean isArticleNumber() {
        return ARTICLE_NUMBER.equals(sourceType);
    }

    public boolean isIssue() {
        return ISSUE.equals(sourceType);
    }

    public boolean isJournalTitle() {
        return JOURNAL.equals(sourceType);
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    @Override
    public String getValue() {
        var value = super.getValue();
        if (this.isPageNumber() && nonNull(value)) {
            return value.replace("–", "-");
        }
        return value;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageSource)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageSource that = (BrageSource) o;
        return getSourceType() == that.getSourceType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSourceType());
    }
}
