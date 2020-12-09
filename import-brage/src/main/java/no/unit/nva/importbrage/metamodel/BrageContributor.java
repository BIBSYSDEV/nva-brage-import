package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Arrays;
import java.util.Locale;

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


    public BrageContributor(DcValue value) {
        this(value.getQualifier(), value.getValue());
    }

    public BrageContributor(String qualifier, String value) {
        this(ContributorType.getTypeByName(qualifier), StringEscapeUtils.unescapeHtml4(value));
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

    public enum ContributorType {
        ADVISOR("advisor"),
        AUTHOR("author"),
        DEPARTMENT("department"),
        EDITOR("editor"),
        ILLUSTRATOR("illustrator"),
        ORCID("orcid"),
        OTHER("other");

        private String typeName;

        ContributorType(String typeName) {
            this.typeName = typeName;
        }

        /**
         * Get the equivalent ContributorType by its string representation.
         * @param typeName A string of a ContributorType.
         * @return A corresponding ContributorType
         */
        public static ContributorType getTypeByName(String typeName) {
            return Arrays.stream(values())
                    .filter(value -> value.getTypeName().equals(typeName.toLowerCase(Locale.ROOT)))
                    .findFirst().orElseThrow();
        }

        public String getTypeName() {
            return typeName;
        }
    }
}
