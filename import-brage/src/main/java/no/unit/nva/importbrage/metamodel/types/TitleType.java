package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

/*
    dc.title.alternative    Varying (or substitute) form of title proper appearing in item, e.g.
                            abbreviation or translation.
    dc.title                Title statement/title proper.
 */
public enum TitleType implements ElementType {
    ALTERNATIVE("alternative"),
    UNQUALIFIED(null);

    public static final String TITLE = "title";
    private final String typeName;

    TitleType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean isLanguageBased() {
        return true;
    }

    /**
     * Get the equivalent TitleType by its string representation.
     *
     * @param candidate A string of a TitleType.
     * @return A corresponding TitleType
     */
    public static TitleType getTypeByName(String candidate) throws InvalidQualifierException {
        return (TitleType) ElementType.getTypeByName(TITLE, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
