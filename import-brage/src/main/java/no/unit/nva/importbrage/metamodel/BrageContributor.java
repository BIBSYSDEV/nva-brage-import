package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.model.Contributor;
import no.unit.nva.model.Identity;
import no.unit.nva.model.NameType;
import no.unit.nva.model.Role;
import no.unit.nva.model.exceptions.MalformedContributorException;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageContributor extends BrageValue {
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

    public BrageContributor(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageContributor(String qualifier, String value) throws InvalidQualifierException {
        this(ContributorType.getTypeByName(qualifier), value);
    }

    public BrageContributor(ContributorType contributorType, String value) {
        super(value);
        this.contributorType = contributorType;
    }

    public ContributorType getContributorType() {
        return contributorType;
    }

    public Contributor getNvaContributor() throws MalformedContributorException, UnknownRoleMappingException {
        var identity = new Identity.Builder()
                .withName(getValue())
                .withNameType(NameType.PERSONAL)
                .build();
        return new Contributor.Builder()
                .withIdentity(identity)
                .withRole(roleMapping())
                .build();
    }

    private Role roleMapping() throws UnknownRoleMappingException {
        switch (contributorType) {
            case AUTHOR:
            case UNQUALIFIED:
                return Role.CREATOR;
            default:
                throw new UnknownRoleMappingException(contributorType.getTypeName());
        }
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
