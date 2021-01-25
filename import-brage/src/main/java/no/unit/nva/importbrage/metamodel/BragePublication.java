package no.unit.nva.importbrage.metamodel;

import nva.commons.utils.JacocoGenerated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"PMD.GodClass", "PMD.TooManyFields"})
public class BragePublication {
    private final List<BrageContributor> contributors = new ArrayList<>();
    private final List<BrageCoverage> coverage = new ArrayList<>();
    private final List<BrageDate> dates = new ArrayList<>();
    private final List<BrageIdentifier> identifiers = new ArrayList<>();
    private final List<BrageDescription> descriptions = new ArrayList<>();
    private final List<BrageCreator> creators = new ArrayList<>();
    private final List<BrageFormat> formats = new ArrayList<>();
    private final List<BrageLanguage> languages = new ArrayList<>();
    private final List<BrageProvenance> provenances = new ArrayList<>();
    private final List<BragePublisher> publishers = new ArrayList<>();
    private final List<BrageRelation> relations = new ArrayList<>();
    private final List<BrageRights> rights = new ArrayList<>();
    private final List<BrageSource> sources = new ArrayList<>();
    private final List<BrageSubject> subjects = new ArrayList<>();
    private final List<BrageTitle> titles = new ArrayList<>();
    private final List<BrageType> types = new ArrayList<>();


    public BragePublication() {

    }

    public List<BrageContributor> getContributors() {
        return contributors;
    }

    public List<BrageCoverage> getCoverage() {
        return coverage;
    }

    public List<BrageCreator> getCreators() {
        return creators;
    }

    public List<BrageDescription> getDescriptions() {
        return descriptions;
    }

    public List<BrageDate> getDates() {
        return dates;
    }

    public List<BrageFormat> getFormats() {
        return formats;
    }

    public List<BrageIdentifier> getIdentifiers() {
        return identifiers;
    }

    public List<BrageLanguage> getLanguages() {
        return languages;
    }

    public List<BrageProvenance> getProvenances() {
        return provenances;
    }

    public List<BragePublisher> getPublishers() {
        return publishers;
    }

    public List<BrageRelation> getRelations() {
        return relations;
    }

    public List<BrageRights> getRights() {
        return rights;
    }

    public List<BrageSource> getSources() {
        return sources;
    }

    public List<BrageSubject> getSubjects() {
        return subjects;
    }

    public List<BrageTitle> getTitles() {
        return titles;
    }

    public List<BrageType> getTypes() {
        return types;
    }

    /**
     * Add a single contributor.
     *
     * @param brageContributor a contributor.
     */
    public void addContributor(BrageContributor brageContributor) {
        
        contributors.add(brageContributor);
    }

    /**
     * Add a single coverage statement.
     *
     * @param brageCoverage A coverage statement.
     */
    public void addCoverage(BrageCoverage brageCoverage) {
        coverage.add(brageCoverage);
    }

    /**
     * Add a single date.
     *
     * @param brageDate A date.
     */
    public void addDate(BrageDate brageDate) {
        dates.add(brageDate);
    }

    /**
     * Add a single identifier.
     *
     * @param brageIdentifier An identifier.
     */
    public void addIdentifier(BrageIdentifier brageIdentifier) {
        identifiers.add(brageIdentifier);
    }

    /**
     * Adds a single description.
     *
     * @param brageDescription A description.
     */
    public void addDescription(BrageDescription brageDescription) {
        descriptions.add(brageDescription);
    }

    /**
     * Adds a single creator.
     *
     * @param brageCreator A creator.
     */
    public void addCreator(BrageCreator brageCreator) {
        creators.add(brageCreator);
    }

    /**
     * Adds a single format.
     *
     * @param brageFormat A format.
     */
    public void addFormat(BrageFormat brageFormat) {
        formats.add(brageFormat);
    }

    /**
     * Adds a single language.
     *
     * @param brageLanguage A language.
     */
    public void addLanguage(BrageLanguage brageLanguage) {
        languages.add(brageLanguage);
    }

    /**
     * Adds a single provenance.
     *
     * @param brageProvenance A provenance.
     */
    public void addProvenance(BrageProvenance brageProvenance) {
        provenances.add(brageProvenance);
    }

    /**
     * Adds a single publisher.
     *
     * @param bragePublisher A publisher.
     */
    public void addPublisher(BragePublisher bragePublisher) {
        publishers.add(bragePublisher);
    }

    /**
     * Adds a single relation.
     *
     * @param brageRelation A relation.
     */
    public void addRelation(BrageRelation brageRelation) {
        relations.add(brageRelation);
    }

    /**
     * Adds a single rights.
     *
     * @param brageRights A rights.
     */
    public void addRights(BrageRights brageRights) {
        rights.add(brageRights);
    }

    /**
     * Adds a single source.
     *
     * @param brageSource A source.
     */
    public void addSource(BrageSource brageSource) {
        sources.add(brageSource);
    }

    /**
     * Adds a single subject.
     *
     * @param brageSubject A subject.
     */
    public void addSubject(BrageSubject brageSubject) {
        subjects.add(brageSubject);
    }

    /**
     * Adds a single title.
     *
     * @param brageTitle A title.
     */
    public void addTitle(BrageTitle brageTitle) {
        titles.add(brageTitle);
    }

    /**
     * Adds a single type.
     *
     * @param brageType A type.
     */
    public void addType(BrageType brageType) {
        types.add(brageType);
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BragePublication)) {
            return false;
        }
        BragePublication that = (BragePublication) o;
        return Objects.equals(getContributors(), that.getContributors())
                && Objects.equals(getCoverage(), that.getCoverage())
                && Objects.equals(getDates(), that.getDates())
                && Objects.equals(getIdentifiers(), that.getIdentifiers())
                && Objects.equals(getDescriptions(), that.getDescriptions())
                && Objects.equals(getCreators(), that.getCreators())
                && Objects.equals(getFormats(), that.getFormats())
                && Objects.equals(getLanguages(), that.getLanguages())
                && Objects.equals(getProvenances(), that.getProvenances())
                && Objects.equals(getPublishers(), that.getPublishers())
                && Objects.equals(getRelations(), that.getRelations())
                && Objects.equals(getRights(), that.getRights())
                && Objects.equals(getSources(), that.getSources())
                && Objects.equals(getSubjects(), that.getSubjects())
                && Objects.equals(getTitles(), that.getTitles())
                && Objects.equals(getTypes(), that.getTypes());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getContributors(), getCoverage(), getDates(), getIdentifiers(), getDescriptions(),
                getCreators(), getFormats(), getLanguages(), getProvenances(), getPublishers(), getRelations(),
                getRights(), getSources(), getSubjects(), getTitles(), getTypes());
    }
}
