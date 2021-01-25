package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException;
import no.unit.nva.model.Role;
import nva.commons.utils.SingletonCollector;

import java.util.Arrays;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public enum ContributorType implements ElementType {
    ADVISOR("advisor", Role.ADVISOR),
    AUTHOR("author", Role.CREATOR),
    DEPARTMENT("department", null),
    EDITOR("editor", Role.EDITOR),
    ILLUSTRATOR("illustrator", Role.ILLUSTRATOR),
    ORCID("orcid", null),
    OTHER("other", null),
    UNQUALIFIED("none", Role.CREATOR);

    public static final String CONTRIBUTOR = "contributor";
    private final String typeName;
    private final Role role;

    ContributorType(String typeName, Role role) {
        this.typeName = typeName;
        this.role = role;
    }

    public static ContributorType getTypeByRole(Role role) {
        if (Role.CREATOR.equals(role)) {
            return AUTHOR;
        }

        return Arrays.stream(values())
                .filter(ContributorType::isNullMapping)
                .filter(mapping -> mapping.role.equals(role))
                .collect(SingletonCollector.collectOrElse(null));
    }

    private static boolean isNullMapping(ContributorType contributorType) {
        return nonNull(contributorType.role);
    }

    @Override
    public String getName() {
        return CONTRIBUTOR;
    }

    @Override
    public ElementType[] getValues() {
        return values();
    }

    @Override
    public String getQualifier() {
        return typeName;
    }

    /**
     * Get the equivalent ContributorType by its string representation.
     *
     * @param candidate A string of a ContributorType.
     * @return A corresponding ContributorType
     */
    public static ContributorType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (ContributorType) ElementType.getTypeByName(CONTRIBUTOR, candidate, values(), UNQUALIFIED, value);
    }

    /**
     * Maps the Brage ContributorType to an NVA Role.
     * @return NVA Role.
     * @throws UnknownRoleMappingException If the mapping is unknown.
     */
    public Role getNvaMapping(String value) throws UnknownRoleMappingException {
        if (!ORCID.getQualifier().equals(typeName) && isNull(role)) {
            throw new UnknownRoleMappingException(typeName, value);
        }
        return role;
    }
}
