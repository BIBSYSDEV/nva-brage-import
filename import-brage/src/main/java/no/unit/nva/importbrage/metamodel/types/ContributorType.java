package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException;
import no.unit.nva.model.Role;

import static java.util.Objects.isNull;

public enum ContributorType implements ElementType {
    ADVISOR("advisor", Role.ADVISOR),
    AUTHOR("author", Role.CREATOR),
    DEPARTMENT("department", null),
    EDITOR("editor", Role.EDITOR),
    ILLUSTRATOR("illustrator", Role.ILLUSTRATOR),
    ORCID("orcid", null),
    OTHER("other", null),
    UNQUALIFIED(null, Role.CREATOR);

    public static final String CONTRIBUTOR = "contributor";
    private final String typeName;
    private final Role role;

    ContributorType(String typeName, Role role) {
        this.typeName = typeName;
        this.role = role;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean isLanguageBased() {
        return false;
    }

    /**
     * Get the equivalent ContributorType by its string representation.
     *
     * @param candidate A string of a ContributorType.
     * @return A corresponding ContributorType
     */
    public static ContributorType getTypeByName(String candidate) throws InvalidQualifierException {
        return (ContributorType) ElementType.getTypeByName(CONTRIBUTOR, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }

    /**
     * Maps the Brage ContributorType to an NVA Role.
     * @return NVA Role.
     * @throws UnknownRoleMappingException If the mapping is unknown.
     */
    public Role getNvaMapping() throws UnknownRoleMappingException {
        if (isNull(role)) {
            throw new UnknownRoleMappingException(typeName);
        }
        return role;
    }
}
