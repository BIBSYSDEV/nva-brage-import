package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

/*
     dc.subject.agrovoc         Agrovoc term.
     dc.subject.classification  Catch-all for value from local classification system; global classification systems 
                                will receive specific qualifier.
     dc.subject.ddc             Dewey Decimal Classification Number.
     dc.subject.hrcs            Health Research Classification System (HRCS).
     dc.subject.humord          Norwegian thesaurus for the subject areas humanities and social sciences (Humord).
     dc.subject.jel             JEL classification system for scholarly literature in the field of economics.
     dc.subject.keyword         Do not use; used for keywords in old items from CRIStin
     dc.subject.lcc             Library of Congress Classification.
     dc.subject.mesh            MEdical Subject Headings.
     dc.subject.nsi             Norwegian Science Index controlled subject.
     dc.subject.nus             Norsk standard for utdanningsgruppering (NUS).
     dc.subject.other           Local controlled vocabulary; global vocabularies will receive specific qualifier.
     dc.subject.realfagstermer  Multilingual, controlled vocabulary of subject headings for the subject areas physical 
                                sciences, mathematics and computer science (Realfagstermer)
     dc.subject                 Uncontrolled index term.
 */
public enum SubjectType implements ElementType {
    AGROVOC("agrovoc"),
    CLASSIFICATION("classification"),
    DDC("ddc"),
    HRCS("hrcs"),
    HUMORD("humord"),
    JEL("jel"),
    KEYWORD("keyword"),
    LCC("lcc"),
    MESH("mesh"),
    NSI("nsi"),
    NUS("nus"),
    OTHER("other"),
    REALFAGSTERMER("realfagstermer"),
    UNQUALIFIED(null);

    public static final String SUBJECT = "subject";
    private final String typeName;

    SubjectType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public boolean isLanguageBased() {
        return true;
    }

    /**
     * Get the equivalent SubjectType by its string representation.
     *
     * @param candidate A string of a SubjectType.
     * @return A corresponding SubjectType
     */
    public static SubjectType getTypeByName(String candidate) throws InvalidQualifierException {
        return (SubjectType) ElementType.getTypeByName(SUBJECT, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
