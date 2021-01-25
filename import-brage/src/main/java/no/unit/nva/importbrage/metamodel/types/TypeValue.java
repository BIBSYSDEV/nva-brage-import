package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.UnknownTypeValueException;
import nva.commons.utils.JacocoGenerated;

import java.util.function.Supplier;

import static no.unit.nva.importbrage.metamodel.types.TypeBasic.TYPE;

public enum TypeValue implements Filterable {
    ARTISTIC_PRODUCTION("Artistic production"),
    BACHELOR_THESIS("Bachelor thesis"),
    BOOK("Book"),
    CHAPTER("Chapter"),
    CHRONICLE("Chronicle"),
    CONFERENCE_OBJECT("Conference object"),
    DOCTORAL_THESIS("Doctoral thesis"),
    ISSUE("Issue"),
    JOURNAL_ARTICLE("Journal article"),
    JOURNAL_CORRIGENDUM("Journal corrigendum"),
    JOURNAL_LEADER("Journal leader"),
    JOURNAL_LETTER("Journal letter"),
    JOURNAL_REVIEW("Journal review"),
    JOURNAL_SHORT_COMMUNICATION("Journal short communication"),
    LEARNING_OBJECT("Learning object"),
    LECTURE("Lecture"),
    MASTER_THESIS("Master thesis"),
    OTHERS("Others"),
    PEER_REVIEWED("Peer reviewed"),
    POLICY_NOTE("Policy note"),
    PREPRINT("Preprint"),
    REPORT("Report"),
    RESEARCH_REPORT("Research report"),
    STUDENT_PAPER_OTHERS("Student paper, others"),
    WORKING_PAPER("Working paper");

    private final String typeName;

    TypeValue(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @JacocoGenerated
    @Override
    public Filterable[] getValues() {
        return values();
    }

    /**
     * Get the equivalent TypeValue by its string representation.
     *
     * @param candidate A string of a TypeValue.
     * @return A corresponding TypeValue
     */
    public static TypeValue getTypeValueByName(String candidate) throws Exception {
        var exceptionSupplier = exceptionSupplier(candidate);
        return (TypeValue) Filterable.getTypeByName(values(), candidate, exceptionSupplier);
    }

    private static Supplier<UnknownTypeValueException> exceptionSupplier(String candidate) {
        return () -> new UnknownTypeValueException(candidate, Filterable.getAllowedValues(values()));
    }
}
