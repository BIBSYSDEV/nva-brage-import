package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum LanguageType implements ElementType {
    ISO("iso"),
    UNQUALIFIED(null);

    private final String typeName;
    public static final String LANGUAGE = "language";

    LanguageType(String typeName) {
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
     * Get the equivalent LanguageType by its string representation.
     *
     * @param candidate A string of a LanguageType.
     * @return A corresponding LanguageType
     */
    public static LanguageType getTypeByName(String candidate) throws InvalidQualifierException {
        return (LanguageType) ElementType.getTypeByName(LANGUAGE, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
