package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum LanguageType implements ElementType {
    ISO("iso"),
    UNQUALIFIED("none");

    private final String typeName;
    public static final String LANGUAGE = "language";

    LanguageType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return LANGUAGE;
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
     * Get the equivalent LanguageType by its string representation.
     *
     * @param candidate A string of a LanguageType.
     * @return A corresponding LanguageType
     */
    public static LanguageType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (LanguageType) ElementType.getTypeByName(LANGUAGE, candidate, values(), UNQUALIFIED, value);
    }
}
