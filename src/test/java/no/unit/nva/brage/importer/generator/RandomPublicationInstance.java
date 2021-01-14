package no.unit.nva.brage.importer.generator;

import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import no.unit.nva.importbrage.metamodel.types.TypeValue;
import no.unit.nva.model.PublicationDate;
import no.unit.nva.model.instancetypes.PublicationInstance;
import no.unit.nva.model.instancetypes.book.BookAnthology;
import no.unit.nva.model.instancetypes.book.BookMonograph;
import no.unit.nva.model.instancetypes.cartographic.CartographicMap;
import no.unit.nva.model.instancetypes.chapter.ChapterArticle;
import no.unit.nva.model.instancetypes.degree.DegreeBachelor;
import no.unit.nva.model.instancetypes.degree.DegreeMaster;
import no.unit.nva.model.instancetypes.degree.DegreePhd;
import no.unit.nva.model.instancetypes.degree.OtherStudentWork;
import no.unit.nva.model.instancetypes.journal.FeatureArticle;
import no.unit.nva.model.instancetypes.journal.JournalArticle;
import no.unit.nva.model.instancetypes.journal.JournalCorrigendum;
import no.unit.nva.model.instancetypes.journal.JournalLeader;
import no.unit.nva.model.instancetypes.journal.JournalLetter;
import no.unit.nva.model.instancetypes.journal.JournalReview;
import no.unit.nva.model.instancetypes.journal.JournalShortCommunication;
import no.unit.nva.model.instancetypes.report.ReportBasic;
import no.unit.nva.model.instancetypes.report.ReportPolicy;
import no.unit.nva.model.instancetypes.report.ReportResearch;
import no.unit.nva.model.instancetypes.report.ReportWorkingPaper;
import no.unit.nva.model.pages.MonographPages;
import no.unit.nva.model.pages.Pages;
import no.unit.nva.model.pages.Range;


public class RandomPublicationInstance extends RandomPublication {
    public static final String PAGES_TEMPLATE = "%s-%s";
    private final PublicationInstance<? extends Pages> publicationInstance;

    public RandomPublicationInstance(Class<?> instance) {
        this.publicationInstance = filterInstance(instance);
    }

    private PublicationInstance<? extends Pages> filterInstance(Class<?> instance) {
        if (instance.equals(BookAnthology.class)) {
            return generateBookAnthology();
        } else if (instance.equals(BookMonograph.class)) {
            return generateBookMonograph();
        } else if (instance.equals(ChapterArticle.class)) {
            return generateChapterArticle();
        } else if (instance.equals(CartographicMap.class)) {
            return generateCartographicMap();
        } else if (instance.equals(DegreeBachelor.class)) {
            return generateDegreeBachelor();
        } else if (instance.equals(DegreeMaster.class)) {
            return generateDegreeMaster();
        } else if (instance.equals(DegreePhd.class)) {
            return generateDegreePhd();
        } else if (instance.equals(FeatureArticle.class)) {
            return generateFeatureArticle();
        } else if (instance.equals(JournalArticle.class)) {
            return generateJournalArticle();
        } else if (instance.equals(JournalCorrigendum.class)) {
            return generateJournalCorrigendum();
        } else if (instance.equals(JournalLeader.class)) {
            return generateJournalLeader();
        } else if (instance.equals(JournalLetter.class)) {
            return generateJournalLetter();
        } else if (instance.equals(JournalReview.class)) {
            return generateJournalReview();
        } else if (instance.equals(JournalShortCommunication.class)) {
            return generateJournalShortCommunication();
        } else if (instance.equals(ReportBasic.class)) {
            return generateReportBasic();
        } else if (instance.equals(ReportPolicy.class)) {
            return generateReportPolicy();
        } else if (instance.equals(ReportResearch.class)) {
            return generateReportResearch();
        } else if (instance.equals(ReportWorkingPaper.class)) {
            return generateReportWorkingPaper();
        } else if (instance.equals(OtherStudentWork.class)) {
            return generateOtherStudentWork();
        } else {
            throw new RuntimeException("Unsupported Publication context: " + instance.getSimpleName());
        }
    }

    private PublicationInstance<? extends Pages> generateOtherStudentWork() {
        setDcValueType(TypeValue.STUDENT_PAPER_OTHERS);
        return new OtherStudentWork.Builder()
                .withPages(generateMonographPages())
                .withSubmittedDate(generateSubmittedDate())
                .build();
    }

    private PublicationInstance<? extends Pages> generateBookAnthology() {
        setDcValueType(TypeValue.BOOK);
        return new BookAnthology.Builder()
                .withPages(generateMonographPages())
                .withTextbookContent(generateTextbookContent())
                .withPeerReviewed(generatePeerReviewed())
                .build();
    }

    private PublicationInstance<? extends Pages> generateBookMonograph() {
        setDcValueType(TypeValue.BOOK);
        return new BookMonograph.Builder()
                .withPages(generateMonographPages())
                .withPeerReviewed(generatePeerReviewed())
                .withTextbookContent(generateTextbookContent())
                .build();
    }

    private PublicationInstance<? extends Pages> generateChapterArticle() {
        throw new RuntimeException("Unsupported type: Chapter Article");
    }

    private PublicationInstance<? extends Pages> generateCartographicMap() {
        throw new RuntimeException("Unsupported type: Cartographic map");
    }

    private DegreeBachelor generateDegreeBachelor() {
        setDcValueType(TypeValue.BACHELOR_THESIS);
        return new DegreeBachelor.Builder()
                .withSubmittedDate(generateSubmittedDate())
                .withPages(generateMonographPages())
                .build();
    }

    private PublicationInstance<? extends Pages> generateDegreeMaster() {
        setDcValueType(TypeValue.MASTER_THESIS);
        return new DegreeMaster.Builder()
                .withSubmittedDate(generateSubmittedDate())
                .withPages(generateMonographPages())
                .build();
    }

    private PublicationInstance<? extends Pages> generateDegreePhd() {
        setDcValueType(TypeValue.DOCTORAL_THESIS);
        return new DegreePhd.Builder()
                .withSubmittedDate(generateSubmittedDate())
                .withPages(generateMonographPages())
                .build();
    }

    private PublicationInstance<? extends Pages> generateFeatureArticle() {
        setDcValueType(TypeValue.CHRONICLE);
        return new FeatureArticle.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .build();
    }

    private PublicationInstance<? extends Pages> generateJournalArticle() {
        setDcValueType(TypeValue.JOURNAL_ARTICLE);
        return new JournalArticle.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .withPeerReviewed(generatePeerReviewed()) // we cannot set this at the moment
                .build();
    }

    private PublicationInstance<? extends Pages> generateJournalCorrigendum() {
        setDcValueType(TypeValue.JOURNAL_CORRIGENDUM);
        return new JournalCorrigendum.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .build();
    }

    private PublicationInstance<? extends Pages> generateJournalLeader() {
        setDcValueType(TypeValue.JOURNAL_LEADER);
        return new JournalLeader.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .build();
    }

    private PublicationInstance<? extends Pages> generateJournalLetter() {
        setDcValueType(TypeValue.JOURNAL_LETTER);
        return new JournalLetter.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .build();
    }

    private PublicationInstance<? extends Pages> generateJournalReview() {
        setDcValueType(TypeValue.JOURNAL_REVIEW);
        return new JournalReview.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .build();
    }

    private PublicationInstance<? extends Pages> generateJournalShortCommunication() {
        setDcValueType(TypeValue.JOURNAL_SHORT_COMMUNICATION);
        return new JournalShortCommunication.Builder()
                .withPages(generateRange())
                .withArticleNumber(generateArticleNumber())
                .withIssue(generateJournalIssue())
                .withVolume(generateJournalVolume())
                .build();
    }

    private PublicationInstance<? extends Pages> generateReportBasic() {
        setDcValueType(TypeValue.REPORT);
        return new ReportBasic.Builder()
                .withPages(generateMonographPages())
                .build();
    }

    private PublicationInstance<? extends Pages> generateReportPolicy() {
        setDcValueType(TypeValue.POLICY_NOTE);
        return new ReportPolicy.Builder()
                .withPages(generateMonographPages())
                .build();
    }

    private PublicationInstance<? extends Pages> generateReportResearch() {
        setDcValueType(TypeValue.RESEARCH_REPORT);
        return new ReportResearch.Builder()
                .withPages(generateMonographPages())
                .build();
    }

    private ReportWorkingPaper generateReportWorkingPaper() {
        setDcValueType(TypeValue.WORKING_PAPER);
        return new ReportWorkingPaper.Builder()
                .withPages(generateMonographPages())
                .build();
    }

    private MonographPages generateMonographPages() {
        String pages = "123";
        addDcValue(SourceType.SOURCE, SourceType.PAGE_NUMBER.getQualifier(), EN_US, pages);
        return new MonographPages.Builder()
                .withPages(pages)
                .build();
    }


    private Range generateRange() {
        String begin = "1";
        String end = "14";
        String pages = String.format(PAGES_TEMPLATE, begin, end);
        addDcValue(SourceType.SOURCE, SourceType.PAGE_NUMBER.getQualifier(), EN_US, pages);
        return new Range.Builder()
                .withBegin(begin)
                .withEnd(end)
                .build();
    }

    private PublicationDate generateSubmittedDate() {
        var submittedDate = new RandomPublicationDate(DateType.SUBMITTED);
        addDcValue(submittedDate.getDcValue());
        return submittedDate.getPublicationDate();
    }

    public PublicationInstance<? extends Pages> getPublicationInstance() {
        return this.publicationInstance;
    }

    private String generateJournalVolume() {
        var volume = "762";
        addDcValue(SourceType.SOURCE, SourceType.VOLUME.getQualifier(), EN_US, volume);
        return volume;
    }

    private String generateJournalIssue() {
        var issue = "226";
        addDcValue(SourceType.SOURCE, SourceType.ISSUE.getQualifier(), EN_US, issue);
        return issue;
    }

    private String generateArticleNumber() {
        var articleNumber = "654241";
        addDcValue(SourceType.SOURCE, SourceType.ARTICLE_NUMBER.getQualifier(), EN_US, articleNumber);
        return articleNumber;
    }

    /**
     * Brage does not support this.
     * @return false
     */
    private boolean generateTextbookContent() {
        return false;
    }

    /**
     * This essentially isn't possible to ascertain at this level because
     * Brage applies peer-reviewed as a type.
     * @return false
     */
    private boolean generatePeerReviewed() {
        addDcValue(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(), EN_US, TypeValue.PEER_REVIEWED.getTypeName());
        return true;
    }
}
