package no.unit.nva.importbrage;

import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import no.unit.nva.brage.nsddbhimiport.JournalParser;
import no.unit.nva.brage.nsddbhimiport.PublisherInfo;
import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCreator;
import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BrageFormat;
import no.unit.nva.importbrage.metamodel.BrageIdentifier;
import no.unit.nva.importbrage.metamodel.BrageLanguageValue;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.BrageRelation;
import no.unit.nva.importbrage.metamodel.BrageSource;
import no.unit.nva.importbrage.metamodel.BrageTitle;
import no.unit.nva.importbrage.metamodel.BrageType;
import no.unit.nva.importbrage.metamodel.BrageValue;
import no.unit.nva.importbrage.metamodel.Language;
import no.unit.nva.importbrage.metamodel.exceptions.AmbiguousDoiException;
import no.unit.nva.importbrage.metamodel.exceptions.ArticleNumberException;
import no.unit.nva.importbrage.metamodel.exceptions.BadOrcidException;
import no.unit.nva.importbrage.metamodel.exceptions.IllegalDateConversionException;
import no.unit.nva.importbrage.metamodel.exceptions.IncorrectlyFormattedSeriesInformationException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidPublicationContextException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidPublicationDateException;
import no.unit.nva.importbrage.metamodel.exceptions.IssuedDateException;
import no.unit.nva.importbrage.metamodel.exceptions.JournalIssueException;
import no.unit.nva.importbrage.metamodel.exceptions.JournalVolumeException;
import no.unit.nva.importbrage.metamodel.exceptions.LanguageException;
import no.unit.nva.importbrage.metamodel.exceptions.PageRangeException;
import no.unit.nva.importbrage.metamodel.exceptions.PagesException;
import no.unit.nva.importbrage.metamodel.exceptions.PublisherException;
import no.unit.nva.importbrage.metamodel.exceptions.SeriesTitleException;
import no.unit.nva.importbrage.metamodel.exceptions.SubmittedDateException;
import no.unit.nva.importbrage.metamodel.exceptions.TitleException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownLanguageException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException;
import no.unit.nva.importbrage.metamodel.exceptions.UnknownTypeMappingException;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;
import no.unit.nva.importbrage.metamodel.types.TypeValue;
import no.unit.nva.model.Contributor;
import no.unit.nva.model.EntityDescription;
import no.unit.nva.model.Publication;
import no.unit.nva.model.PublicationDate;
import no.unit.nva.model.Reference;
import no.unit.nva.model.contexttypes.Book;
import no.unit.nva.model.contexttypes.Chapter;
import no.unit.nva.model.contexttypes.Degree;
import no.unit.nva.model.contexttypes.Journal;
import no.unit.nva.model.contexttypes.PublicationContext;
import no.unit.nva.model.contexttypes.Report;
import no.unit.nva.model.exceptions.InvalidIsbnException;
import no.unit.nva.model.exceptions.InvalidIssnException;
import no.unit.nva.model.exceptions.MalformedContributorException;
import no.unit.nva.model.instancetypes.PublicationInstance;
import no.unit.nva.model.instancetypes.book.BookAnthology;
import no.unit.nva.model.instancetypes.book.BookMonograph;
import no.unit.nva.model.instancetypes.chapter.ChapterArticle;
import no.unit.nva.model.instancetypes.degree.DegreeBachelor;
import no.unit.nva.model.instancetypes.degree.DegreeMaster;
import no.unit.nva.model.instancetypes.degree.DegreePhd;
import no.unit.nva.model.instancetypes.degree.OtherStudentWork;
import no.unit.nva.model.instancetypes.journal.FeatureArticle;
import no.unit.nva.model.instancetypes.journal.JournalArticle;
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
import nva.commons.utils.SingletonCollector;
import nva.commons.utils.attempt.Try;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.pemistahl.lingua.api.Language.BOKMAL;
import static com.github.pemistahl.lingua.api.Language.DANISH;
import static com.github.pemistahl.lingua.api.Language.DUTCH;
import static com.github.pemistahl.lingua.api.Language.ENGLISH;
import static com.github.pemistahl.lingua.api.Language.FINNISH;
import static com.github.pemistahl.lingua.api.Language.FRENCH;
import static com.github.pemistahl.lingua.api.Language.GERMAN;
import static com.github.pemistahl.lingua.api.Language.ICELANDIC;
import static com.github.pemistahl.lingua.api.Language.ITALIAN;
import static com.github.pemistahl.lingua.api.Language.NYNORSK;
import static com.github.pemistahl.lingua.api.Language.SPANISH;
import static com.github.pemistahl.lingua.api.Language.SWEDISH;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static nva.commons.utils.attempt.Try.attempt;

public class BragePublicationConverter {
    public static final String DELIMITER = ", ";
    public static final String MISSING_MAPPING_ERROR_TEMPLATE = "The type value \"%s\" has not been added to "
            + "the mapping filter";
    public static final String ISBN = "ISBN";
    public static final String EMPTY_STRING = "";
    private final JournalParser journalParser;

    public static final String ORCID_FAILED_MESSAGE = "Processing ORCID on contributor \"%s\" failed";
    public static final LanguageDetector LANGUAGE_DETECTOR = LanguageDetectorBuilder.fromLanguages(BOKMAL,
            DANISH, DUTCH, ENGLISH, FINNISH, FRENCH, GERMAN, ICELANDIC, ITALIAN, NYNORSK, SPANISH, SWEDISH).build();
    public static final int SINGLETON = 1;
    private boolean peerReviewed = false;
    private boolean preprint = false;
    private final List<Exception> failures = new ArrayList<>();
    private final BragePublication bragePublication;
    private final Publication nvaPublication;

    public BragePublicationConverter(BragePublication bragePublication, JournalParser journalParser) {
        this.bragePublication = bragePublication;
        this.journalParser = journalParser;
        this.nvaPublication = asNvaPublication();
    }

    public List<Exception> getFailures() {
        return failures;
    }

    public Publication getNvaPublication() {
        return nvaPublication;
    }

    private Publication asNvaPublication() {
        var entityDescription = new EntityDescription.Builder()
                .withAlternativeTitles(extractAlternativeTitles())
                .withContributors(extractContributors())
                .withDescription(extractDescription())
                .withAbstract(extractAbstract())
                .withLanguage(extractLanguage())
                .withDate(extractPublicationDate())
                .withMainTitle(extractMainTitle())
                .withReference(extractReference())
                .build();

        return failures.isEmpty()
                ? new Publication.Builder()
                .withEntityDescription(entityDescription)
                .build()
                : null;
    }

    private Map<String, String> extractAlternativeTitles() {
        // TODO fix key clash problem and problems with language matches
        return bragePublication.getTitles().stream()
                .filter(BrageTitle::isAlternativeTitle)
                .map(BrageValue::getValue)
                .map(this::getValueMap)
                .flatMap(s -> s.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, String> getValueMap(String value) {
        var detectedLanguage = LANGUAGE_DETECTOR.detectLanguageOf(value).getIsoCode639_1().toString();
        return Map.of(detectedLanguage, value);
    }

    private String extractMainTitle() {
        var titles = bragePublication.getTitles();
        try {
            return titles.stream()
                    .filter(BrageTitle::isMainTitle)
                    .map(BrageTitle::getValue)
                    .collect(SingletonCollector.collect());
        } catch (IllegalStateException exception) {
            failures.add(new TitleException(titles));
            return null;
        }
    }

    private PublicationDate extractPublicationDate() {
        try {
            var dates = bragePublication.getDates().stream()
                    .filter(BrageDate::isIssuedDate)
                    .collect(Collectors.toList());
            if (dates.size() > SINGLETON) {
                throw new IssuedDateException(dates);
            }
            return dates.isEmpty() ? null : dates.get(0).asPublicationDate();
        } catch (InvalidPublicationDateException | IssuedDateException | IllegalDateConversionException e) {
            failures.add(e);
            return null;
        }
    }

    private URI extractLanguage() {
        var languages = bragePublication.getLanguages();

        if (languages.isEmpty()) {
            return null;
        }

        try {
            var language = languages.stream()
                    .map(BrageLanguageValue::getValue)
                    .collect(SingletonCollector.tryCollect())
                    .orElseThrow(failure -> new LanguageException(languages));

            return getLanguage(language);
        } catch (UnknownLanguageException | LanguageException e) {
            failures.add(e);
            return null;
        }
    }

    private URI getLanguage(String language) throws UnknownLanguageException {
        return Language.getByCode(language).getUri();
    }

    private String extractAbstract() {
        return extractDescription(DescriptionType.ABSTRACT);
    }

    private String extractDescription() {
        return extractDescription(DescriptionType.UNQUALIFIED);
    }

    private String extractDescription(DescriptionType type) {
        return bragePublication.getDescriptions().stream()
                .filter(description -> type.equals(description.getDescriptionType()))
                .map(BrageValue::getValue)
                .collect(Collectors.joining("#"));
    }

    private List<Contributor> extractContributors() {
        var brageContributors = bragePublication.getContributors();
        var brageCreators = bragePublication.getCreators();
        extractAndCompareCreators(brageContributors, brageCreators);
        var orcid = brageContributors.stream()
                .filter(BrageContributor::isOrcid)
                .findAny()
                .orElse(null);
        try {
            validateOrcidContributorPair(brageContributors, orcid);
            var nvaContributors = new ArrayList<Contributor>();
            if (nonNull(orcid)) {
                nvaContributors.add(getOrcidContributor(brageContributors, orcid));
            } else {
                for (BrageContributor contributor : bragePublication.getContributors()) {
                    nvaContributors.add(contributor.getNvaContributor());
                }
            }
            return nvaContributors;
        } catch (BadOrcidException | MalformedContributorException | UnknownRoleMappingException e) {
            failures.add(e);
            return null;
        }
    }

    private void extractAndCompareCreators(List<BrageContributor> brageContributors, List<BrageCreator> brageCreators) {
        var creatorContributors = new ArrayList<BrageContributor>();
        for (BrageCreator creator : brageCreators) {
            if (isNotAnExistingContributor(brageContributors, creator)) {
                BrageContributor brageContributor = creatorToContributor(creator);
                creatorContributors.add(brageContributor);
            }
        }
        brageContributors.addAll(creatorContributors);
    }

    private BrageContributor creatorToContributor(BrageCreator brageCreator) {
        return new BrageContributor(ContributorType.AUTHOR, brageCreator.getValue());
    }

    private boolean isNotAnExistingContributor(List<BrageContributor> brageContributors, BrageCreator creator) {
        return brageContributors.stream()
                .noneMatch(contributor -> contributor.getValue().equals(creator.getValue()));
    }

    private void validateOrcidContributorPair(List<BrageContributor> brageContributors, BrageContributor orcid) throws
            BadOrcidException {
        if (nonNull(orcid) && brageContributors.size() != 2) {
            throw new BadOrcidException(orcid);
        }
    }

    private Contributor getOrcidContributor(List<BrageContributor> brageContributors, BrageContributor orcid) throws
            UnknownRoleMappingException, MalformedContributorException {
        var mainContributor = brageContributors.stream()
                .filter(this::isNotOrcid)
                .findFirst();
        if (mainContributor.isPresent()) {
            var contributor = mainContributor.get().getNvaContributor();
            contributor.getIdentity().setOrcId(orcid.getValue());
            return contributor;
        } else {
            throw new IllegalStateException(String.format(ORCID_FAILED_MESSAGE, orcid));
        }
    }

    private boolean isNotOrcid(BrageContributor contributor) {
        return !contributor.isOrcid();
    }

    private Reference extractReference() {
        setPeerReviewed();
        setPreprint();
        var attempts = bragePublication.getTypes().stream()
                .map(BrageType::getValue)
                .map(typeValue -> attempt((type) -> getReference((TypeValue) type)).apply(typeValue))
                .collect(Collectors.toList());

        var referenceFailures = attempts.stream()
                .filter(Try::isFailure).map(Try::getException)
                .collect(Collectors.toList());
        failures.addAll(referenceFailures);
        var reference = attempts.stream()
                .filter(Try::isSuccess).map(Try::get)
                .filter(Objects::nonNull)
                .collect(SingletonCollector.collectOrElse(null));
        return referenceFailures.isEmpty() ? reference : null;
    }

    private Reference getReference(TypeValue typeValue) throws InvalidIsbnException, UnknownTypeMappingException,
            AmbiguousDoiException, InvalidIssnException, SeriesTitleException,
            IncorrectlyFormattedSeriesInformationException, MalformedURLException, IdentifierUrlException,
            PublisherException, PagesException, InvalidPublicationDateException, SubmittedDateException,
            IllegalDateConversionException, PageRangeException, JournalVolumeException, JournalIssueException,
            ArticleNumberException, IssuedDateException, InvalidPublicationContextException {
        if (TypeValue.ARTISTIC_PRODUCTION.equals(typeValue)) {
            throw new UnknownTypeMappingException(typeValue.getTypeName());
        } else if (TypeValue.BACHELOR_THESIS.equals(typeValue)) {
            return generateDegreeBachelor();
        } else if (TypeValue.BOOK.equals(typeValue)) {
            return generateBook();
        } else if (TypeValue.CHAPTER.equals(typeValue)) {
            return generateChapter();
        } else if (TypeValue.CHRONICLE.equals(typeValue)) {
            return generateFeatureArticle();
        } else if (TypeValue.CONFERENCE_OBJECT.equals(typeValue)) {
            throw new UnknownTypeMappingException(typeValue.getTypeName());
        } else if (TypeValue.DOCTORAL_THESIS.equals(typeValue)) {
            return generateDegreePhd();
        } else if (TypeValue.ISSUE.equals(typeValue)) {
            throw new UnknownTypeMappingException(typeValue.getTypeName());
        } else if (TypeValue.JOURNAL_ARTICLE.equals(typeValue)) {
            return generateJournalArticle();
        } else if (TypeValue.JOURNAL_LEADER.equals(typeValue)) {
            return generateJournalLeader();
        } else if (TypeValue.JOURNAL_LETTER.equals(typeValue)) {
            return generateJournalLetter();
        } else if (TypeValue.JOURNAL_REVIEW.equals(typeValue)) {
            return generateJournalReview();
        } else if (TypeValue.JOURNAL_SHORT_COMMUNICATION.equals(typeValue)) {
            return generateJournalShortCommunication();
        } else if (TypeValue.LEARNING_OBJECT.equals(typeValue)) {
            throw new UnknownTypeMappingException(typeValue.getTypeName());
        } else if (TypeValue.LECTURE.equals(typeValue)) {
            throw new UnknownTypeMappingException(typeValue.getTypeName());
        } else if (TypeValue.MASTER_THESIS.equals(typeValue)) {
            return generateDegreeMaster();
        } else if (TypeValue.REPORT.equals(typeValue)) {
            return generateReportBasic();
        } else if (TypeValue.RESEARCH_REPORT.equals(typeValue)) {
            return generateReportResearch();
        } else if (TypeValue.STUDENT_PAPER_OTHERS.equals(typeValue)) {
            return generateOtherStudentWork();
        } else if (TypeValue.WORKING_PAPER.equals(typeValue)) {
            return generateReportWorkingPaper();
        } else if (TypeValue.POLICY_NOTE.equals(typeValue)) {
            return generateReportPolicy();
        } else if (TypeValue.PEER_REVIEWED.equals(typeValue)) {
            return null;
        } else {
            throw new RuntimeException(String.format(MISSING_MAPPING_ERROR_TEMPLATE, typeValue.getTypeName()));
        }
    }

    private Reference generateFeatureArticle() throws ArticleNumberException, PageRangeException, JournalIssueException,
            InvalidIssnException, AmbiguousDoiException, JournalVolumeException, MalformedURLException, InvalidPublicationContextException {
        var instance = new FeatureArticle.Builder()
                .withArticleNumber(extractArticleNumber())
                .withPages(extractRange())
                .withIssue(extractIssue())
                .withVolume(extractVolume())
                .build();
        return generateReference(generateJournalContext(), instance);
    }

    private String extractVolume() throws JournalVolumeException {
        var sources = bragePublication.getSources().stream()
                .filter(BrageSource::isVolume)
                .collect(Collectors.toList());
        if (sources.size() > 1) {
            throw new JournalVolumeException(sources);
        }

        return sources.isEmpty() ? null : sources.get(0).getValue();
    }

    private String extractIssue() throws JournalIssueException {
        var sources = bragePublication.getSources().stream()
                .filter(BrageSource::isIssue)
                .collect(Collectors.toList());
        if (sources.size() > 1) {
            throw new JournalIssueException(sources);
        }

        return sources.isEmpty() ? null : sources.get(0).getValue();
    }

    private Range extractRange() throws PageRangeException {
        var sources = bragePublication.getSources().stream()
                .filter(BrageSource::isPageNumber)
                .map(BrageSource::getValue)
                .collect(Collectors.toList());
        if (sources.size() > 1) {
            throw new PageRangeException(sources);
        }
        if (sources.isEmpty()) {
            return null;
        }

        var value = sources.get(0);
        if (isNull(value)) {
            return null;
        }
        var parts = value.split("-");

        if (parts.length != 2) {
            throw new PageRangeException(value);
        }

        return new Range.Builder()
                .withBegin(parts[0])
                .withEnd(parts[1])
                .build();
    }

    private String extractArticleNumber() throws ArticleNumberException {
        var sources = bragePublication.getSources().stream()
                .filter(BrageSource::isArticleNumber)
                .collect(Collectors.toList());
        if (sources.size() > 1) {
            throw new ArticleNumberException(sources);
        }

        return sources.isEmpty() ? null : sources.get(0).getValue();
    }

    private Reference generateReportPolicy() throws PagesException, PublisherException, InvalidIssnException,
            InvalidIsbnException, IncorrectlyFormattedSeriesInformationException, IdentifierUrlException,
            MalformedURLException, SeriesTitleException, AmbiguousDoiException {
        var instance = new ReportPolicy.Builder()
                .withPages(extractMonographPages())
                .build();
        return generateReference(getReportContext(), instance);
    }

    private Reference generateReportWorkingPaper() throws InvalidIssnException, InvalidIsbnException,
            AmbiguousDoiException, SeriesTitleException, IncorrectlyFormattedSeriesInformationException,
            PagesException, IdentifierUrlException, PublisherException, MalformedURLException {
        var instance = new ReportWorkingPaper.Builder()
                .withPages(extractMonographPages())
                .build();
        return generateReference(getReportContext(), instance);
    }

    private Reference generateOtherStudentWork() throws InvalidIsbnException, AmbiguousDoiException,
            IncorrectlyFormattedSeriesInformationException, MalformedURLException, PublisherException,
            SeriesTitleException, IdentifierUrlException, PagesException, InvalidPublicationDateException,
            SubmittedDateException, IllegalDateConversionException {
        var instance = new OtherStudentWork.Builder()
                .withSubmittedDate(extractSubmittedDate())
                .withPages(extractMonographPages())
                .build();
        return generateReference(generateDegreeContext(), instance);
    }

    private Reference generateReportResearch() throws PagesException, IncorrectlyFormattedSeriesInformationException,
            SeriesTitleException, InvalidIssnException, InvalidIsbnException, AmbiguousDoiException,
            IdentifierUrlException, PublisherException, MalformedURLException {
        var instance = new ReportResearch.Builder()
                .withPages(extractMonographPages())
                .build();
        return generateReference(getReportContext(), instance);
    }

    private Reference generateReportBasic() throws InvalidIsbnException, InvalidIssnException, AmbiguousDoiException,
            PagesException, SeriesTitleException, IncorrectlyFormattedSeriesInformationException,
            IdentifierUrlException, PublisherException, MalformedURLException {
        var instance = new ReportBasic.Builder()
                .withPages(extractMonographPages())
                .build();
        return generateReference(getReportContext(), instance);
    }

    private Report getReportContext() throws InvalidIssnException, InvalidIsbnException, SeriesTitleException,
            IncorrectlyFormattedSeriesInformationException, PublisherException, MalformedURLException,
            IdentifierUrlException {
        var series = extractSeriesInformation();
        return new Report.Builder()
                .withSeriesNumber(series.getSeriesNumber())
                .withSeriesTitle(series.getSeriesTitle())
                .withPeerReviewed(peerReviewed)
                .withIsbnList(getIsbns())
                .withLevel(null)
                .withOnlineIssn(null)
                .withPrintIssn(null)
                .withOpenAccess(false)
                .withPublisher(extractPublisher())
                .withUrl(extractUrl())
                .build();
    }

    private Reference generateDegreeMaster() throws InvalidIsbnException, AmbiguousDoiException, PagesException,
            InvalidPublicationDateException, SubmittedDateException, IllegalDateConversionException,
            IncorrectlyFormattedSeriesInformationException, MalformedURLException, PublisherException,
            SeriesTitleException, IdentifierUrlException {
        var instance = new DegreeMaster.Builder()
                .withSubmittedDate(extractSubmittedDate())
                .withPages(extractMonographPages())
                .build();
        return generateReference(generateDegreeContext(), instance);
    }

    private PublicationDate extractSubmittedDate() throws InvalidPublicationDateException,
            IllegalDateConversionException, SubmittedDateException {
        var submittedDates = bragePublication.getDates().stream()
                .filter(BrageDate::isSubmittedDate)
                .collect(Collectors.toList());
        if (submittedDates.size() > 1) {
            throw new SubmittedDateException(submittedDates);
        }
        return submittedDates.isEmpty() ? null : submittedDates.get(0).asPublicationDate();
    }

    private Reference generateJournalArticle() throws AmbiguousDoiException, InvalidIssnException,
            ArticleNumberException, JournalIssueException, JournalVolumeException, PageRangeException,
            MalformedURLException, InvalidPublicationContextException {
        var instance = new JournalArticle.Builder()
                .withArticleNumber(extractArticleNumber())
                .withIssue(extractIssue())
                .withVolume(extractVolume())
                .withPages(extractRange())
                .withPeerReviewed(peerReviewed)
                .build();
        return generateReference(generateJournalContext(), instance);
    }

    private Reference generateJournalLeader() throws InvalidIssnException, MalformedURLException,
            ArticleNumberException, JournalIssueException, PageRangeException, JournalVolumeException,
            AmbiguousDoiException, InvalidPublicationContextException {
        var instance = new JournalLeader.Builder()
                .withArticleNumber(extractArticleNumber())
                .withIssue(extractIssue())
                .withPages(extractRange())
                .withVolume(extractVolume())
                .build();
        return generateReference(generateJournalContext(), instance);
    }

    private Reference generateJournalLetter() throws InvalidIssnException, MalformedURLException,
            ArticleNumberException, JournalIssueException, PageRangeException, JournalVolumeException,
            AmbiguousDoiException, InvalidPublicationContextException {
        var instance = new JournalLetter.Builder()
                .withArticleNumber(extractArticleNumber())
                .withIssue(extractIssue())
                .withPages(extractRange())
                .withVolume(extractVolume())
                .build();
        return generateReference(generateJournalContext(), instance);
    }

    private Reference generateJournalReview() throws InvalidIssnException, MalformedURLException,
            ArticleNumberException, JournalIssueException, PageRangeException, JournalVolumeException,
            AmbiguousDoiException, InvalidPublicationContextException {
        var instance = new JournalReview.Builder()
                .withArticleNumber(extractArticleNumber())
                .withIssue(extractIssue())
                .withPages(extractRange())
                .withVolume(extractVolume())
                .build();
        return generateReference(generateJournalContext(), instance);
    }

    private Reference generateJournalShortCommunication() throws InvalidIssnException, MalformedURLException,
            ArticleNumberException, JournalIssueException, PageRangeException, JournalVolumeException,
            AmbiguousDoiException, InvalidPublicationContextException {
        var instance = new JournalShortCommunication.Builder()
                .withArticleNumber(extractArticleNumber())
                .withIssue(extractIssue())
                .withPages(extractRange())
                .withVolume(extractVolume())
                .build();
        return generateReference(generateJournalContext(), instance);
    }

    private Journal generateJournalContext() throws InvalidIssnException, MalformedURLException,
            InvalidPublicationContextException {
        var issns = extractIssns();
        PublisherInfo publisherInfo = null;

        if (!issns.isEmpty()) {
            publisherInfo = getPublisherInfo(issns);
        }

        if (isNull(publisherInfo)) {
            var journalTitle = extractJournalTitle();
            publisherInfo = getPublisherInfo(journalTitle);
        }

        var publicationDate = extractPublicationDate();
        var publicationYear = nonNull(publicationDate) ? publicationDate.getYear() : null;
        var levels = nonNull(publisherInfo) ? publisherInfo.getLevel() : null;
        var level = nonNull(publicationYear) && nonNull(levels) ? levels.get(publicationYear) : null;

        if (nonNull(publisherInfo)) {
            return new Journal.Builder()
                    .withTitle(publisherInfo.getTitle())
                    .withPrintIssn(publisherInfo.getPrintIssn())
                    .withOnlineIssn(publisherInfo.getOnlineIssn())
                    .withLevel(level)
                    .withOpenAccess(publisherInfo.getOpenAccess())
                    .withPeerReviewed(peerReviewed)
                    .withUrl(URI.create(publisherInfo.getUrl()).toURL())
                    .build();
        } else {
            throw new InvalidPublicationContextException();
        }
    }

    private String extractJournalTitle() {
        return bragePublication.getSources().stream()
                .filter(BrageSource::isJournalTitle)
                .map(BrageSource::getValue)
                .collect(SingletonCollector.collectOrElse(null));
    }

    private PublisherInfo getPublisherInfo(List<String> issns) {
        PublisherInfo publisherInfo = null;
        for (String issn : issns) {
            if (isNull(publisherInfo)) {
                publisherInfo = journalParser.byIssn(issn);
            }
        }
        return publisherInfo;
    }

    private PublisherInfo getPublisherInfo(String journalTitle) {
        return journalParser.byTitle(journalTitle);
    }
    private List<String> extractIssns() {
        return bragePublication.getIdentifiers().stream()
                .filter(BrageIdentifier::isIssn)
                .map(BrageIdentifier::getValue)
                .collect(Collectors.toList());
    }

    private Reference generateDegreePhd() throws AmbiguousDoiException, InvalidIsbnException, PagesException,
            InvalidPublicationDateException, SubmittedDateException, IllegalDateConversionException,
            IncorrectlyFormattedSeriesInformationException, MalformedURLException, PublisherException,
            SeriesTitleException, IdentifierUrlException {
        var context = generateDegreeContext();
        var instance = new DegreePhd.Builder()
                .withSubmittedDate(extractSubmittedDate())
                .withPages(extractMonographPages())
                .build();
        return generateReference(context, instance);
    }

    private Degree generateDegreeContext() throws InvalidIsbnException, MalformedURLException, IdentifierUrlException,
            PublisherException, SeriesTitleException, IncorrectlyFormattedSeriesInformationException {
        var series = extractSeriesInformation();
        return new Degree.Builder()
                .withPublisher(extractPublisher())
                .withSeriesNumber(series.getSeriesNumber())
                .withSeriesTitle(series.getSeriesTitle())
                .withUrl(extractUrl())
                .withIsbnList(getIsbns())
                .build();
    }

    private List<String> getIsbns() {
        List<BrageIdentifier> identifiers = bragePublication.getIdentifiers();
        if (isNull(identifiers) || identifiers.isEmpty()) {
            return emptyList();
        }
        return identifiers.stream()
                .filter(BrageIdentifier::isIsbn)
                .map(BrageValue::getValue)
                .map(value -> value.replace(ISBN, EMPTY_STRING))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private Reference generateReference(PublicationContext context, PublicationInstance<? extends Pages> instance)
            throws AmbiguousDoiException {

        return new Reference.Builder()
                .withPublishingContext(context)
                .withPublicationInstance(instance)
                .withDoi(getDoi())
                .build();
    }

    private URI getDoi() throws AmbiguousDoiException {
        var dois = bragePublication.getIdentifiers().stream()
                .filter(BrageIdentifier::isDoi)
                .map(attempt(BrageIdentifier::asDoi))
                .collect(Collectors.toList());

        if (dois.isEmpty()) {
            return null;
        }

        if (dois.size() == SINGLETON && dois.get(0).isSuccess()) {
            return dois.get(0).get();
        } else {
            dois.forEach(this::addExceptionToFailureList);
            throw new AmbiguousDoiException(collectDoiValuesAsCommaString(dois));
        }
    }

    private String collectDoiValuesAsCommaString(List<Try<URI>> dois) {
        return dois.stream().map(doi -> doi.get().toString()).collect(Collectors.joining(DELIMITER));
    }

    private void addExceptionToFailureList(Try<URI> attempt) {
        if (attempt.isFailure()) {
            failures.add(attempt.getException());
        }
    }

    private Reference generateChapter() throws AmbiguousDoiException {
        var context = new Chapter.Builder().build();
        var instance = new ChapterArticle.Builder()
                .withPeerReviewed(peerReviewed)
                .build();
        return generateReference(context, instance);
    }

    private Reference generateDegreeBachelor() throws InvalidIsbnException, AmbiguousDoiException,
            InvalidPublicationDateException, SubmittedDateException, IllegalDateConversionException, PagesException,
            IncorrectlyFormattedSeriesInformationException, MalformedURLException, PublisherException,
            SeriesTitleException, IdentifierUrlException {
        var instance = new DegreeBachelor.Builder()
                .withPages(extractMonographPages())
                .withSubmittedDate(extractSubmittedDate())
                .build();
        return generateReference(generateDegreeContext(), instance);
    }

    private Reference generateBook() throws InvalidIsbnException, AmbiguousDoiException, SeriesTitleException,
            IncorrectlyFormattedSeriesInformationException, MalformedURLException, IdentifierUrlException,
            PublisherException, PagesException {

        var seriesInformation = extractSeriesInformation();
        var context = new Book.Builder()
                .withSeriesTitle(seriesInformation.getSeriesTitle())
                .withSeriesNumber(seriesInformation.getSeriesNumber())
                .withUrl(extractUrl())
                .withPublisher(extractPublisher())
                .withPeerReviewed(peerReviewed)
//                .withOpenAccess(extractOpenAccess())
//                .withLevel(extractLevel())
                .withIsbnList(getIsbns())
                .build();
        PublicationInstance<? extends Pages> instance;
        boolean edited = bragePublication.getContributors().stream()
                .anyMatch(BrageContributor::isEditor);
        if (edited) {
            try {
                instance = new BookAnthology.Builder()
                        .withPages(extractMonographPages())
                        .withPeerReviewed(peerReviewed)
                        .build();
            } catch (PagesException e) {
                failures.add(e);
                instance = null;
            }
        } else {
            instance = new BookMonograph.Builder()
                    .withPages(extractMonographPages())
                    .withPeerReviewed(peerReviewed)
                    .build();
        }
        return generateReference(context, instance);
    }

    private MonographPages extractMonographPages() throws PagesException {
        var sourcePages = bragePublication.getSources().stream()
                .filter(BrageSource::isPageNumber)
                .collect(Collectors.toList());
        var formatPages = bragePublication.getFormats().stream()
                .filter(BrageFormat::isExtent)
                .collect(Collectors.toList());

        if (sourcePages.isEmpty() && formatPages.isEmpty()) {
            return null;
        }

        if (sourcePages.size() == SINGLETON) {
            return new MonographPages.Builder().withPages(sourcePages.get(0).getValue()).build();
        } else if (formatPages.size() == SINGLETON) {
            return new MonographPages.Builder().withPages(formatPages.get(0).getValue()).build();
        } else {
            var allPages = new ArrayList<String>();
            sourcePages.forEach(source -> allPages.add(source.getValue()));
            formatPages.forEach(format -> allPages.add(format.getValue()));
            throw new PagesException(allPages);
        }
    }

    private String extractPublisher() throws PublisherException {
        var publishers = bragePublication.getPublishers();
        if (publishers.size() > SINGLETON) {
            throw new PublisherException(publishers);
        }
        return publishers.isEmpty() ? null : publishers.get(0).getValue();
    }

    private URL extractUrl() throws MalformedURLException, IdentifierUrlException {
        var identifiers = bragePublication.getIdentifiers().stream()
                .filter(BrageIdentifier::isUri)
                .collect(Collectors.toList());
        if (identifiers.size() > SINGLETON) {
            throw new IdentifierUrlException(identifiers);
        }
        return identifiers.isEmpty() ? null : URI.create(identifiers.get(0).getValue()).toURL();
    }

    private SeriesInformation extractSeriesInformation() throws SeriesTitleException,
            IncorrectlyFormattedSeriesInformationException {
        var seriesTitles = bragePublication.getRelations().stream()
                .filter(BrageRelation::isPartOfSeries)
                .collect(Collectors.toList());
        if (seriesTitles.size() > SINGLETON) {
            throw new SeriesTitleException(seriesTitles);
        }
        var seriesInformation = seriesTitles.isEmpty() ? null : seriesTitles.get(0).getValue();
        String[] parts;
        if (nonNull(seriesInformation)) {
            parts = seriesInformation.split(";");
        } else {
            return new SeriesInformation(null, null);
        }
        if (parts.length != 2) {
            throw new IncorrectlyFormattedSeriesInformationException(seriesInformation);
        }
        return new SeriesInformation(parts[0], parts[SINGLETON]);
    }

    private void setPeerReviewed() {
        peerReviewed = bragePublication.getTypes().stream()
                .anyMatch(BrageType::isPeerReviewed);
    }

    private void setPreprint() {
        preprint = bragePublication.getTypes().stream()
                .anyMatch(BrageType::isPreprint);
    }
}
