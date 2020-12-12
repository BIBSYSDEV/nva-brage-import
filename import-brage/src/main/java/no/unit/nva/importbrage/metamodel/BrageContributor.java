package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageContributor {
    /*
      Generated from an XML element like:

          <dcvalue element="contributor" qualifier="advisor">Olsen,&#x20;Ole</dcvalue>

      Values according to:

          dc.contributor                A person, organization, or service responsible for the content of the resource.
                                        Catch-all for unspecified contributors.
          dc.contributor.advisor        Use primarily for thesis advisor.
          dc.contributor.author         Use for author.
          dc.contributor.department     The department where an item was created (used for import from DiVA).
          dc.contributor.editor         Use for editor.
          dc.contributor.illustrator    Use for illustrator.
          dc.contributor.orcid          Use for Orcid identifier of a contributor
          dc.contributor.other
     */

    private final ContributorType contributorType;
    private final String value;

    public BrageContributor(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageContributor(String qualifier, String value) throws InvalidQualifierException {
        this(ContributorType.getTypeByName(qualifier), value);
    }

    public BrageContributor(ContributorType contributorType, String value) {
        this.contributorType = contributorType;
        this.value = value;
    }

    public ContributorType getContributorType() {
        return contributorType;
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
        if (!(o instanceof BrageContributor)) {
            return false;
        }
        BrageContributor that = (BrageContributor) o;
        return getContributorType() == that.getContributorType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getContributorType(), getValue());
    }
}
