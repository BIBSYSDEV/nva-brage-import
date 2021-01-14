package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

/*
    dc.title.alternative    Varying (or substitute) form of title proper appearing in item, e.g.
                            abbreviation or translation.
    dc.title                Title statement/title proper.
 */
public enum TitleType implements ElementType {
    ALTERNATIVE("alternative"),
    UNQUALIFIED("none");

    public static final String TITLE = "title";
    private final String typeName;

    TitleType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return TITLE;
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
     * Get the equivalent TitleType by its string representation.
     *
     * @param candidate A string of a TitleType.
     * @return A corresponding TitleType
     */
    public static TitleType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (TitleType) ElementType.getTypeByName(TITLE, candidate, values(), UNQUALIFIED, value);
    }
}
