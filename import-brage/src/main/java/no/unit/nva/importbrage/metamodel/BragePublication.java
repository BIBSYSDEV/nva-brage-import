package no.unit.nva.importbrage.metamodel;

import nva.commons.utils.JacocoGenerated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class BragePublication {
    private List<BrageContributor> contributors;
    private List<BrageCoverage> coverage;
    private List<BrageDate> dates;
    private List<BrageIdentifier> identifiers;
    private List<BrageDescription> descriptions;
    private List<BrageCreator> creators;
    private List<BrageFormat> formats;
    private List<BrageLanguage> languages;
    private List<BrageProvenance> provenances;
    private List<BragePublisher> publishers;
    private List<BrageRelation> relations;
    private List<BrageRights> rights;

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

    /**
     * Add a single contributor.
     * @param brageContributor a contributor.
     */
    public void addContributor(BrageContributor brageContributor) {
        if (isNull(contributors)) {
            contributors = new ArrayList<>();
        }
        contributors.add(brageContributor);
    }

    /**
     * Add a single coverage statement.
     * @param brageCoverage A coverage statement.
     */
    public void addCoverage(BrageCoverage brageCoverage) {
        if (isNull(coverage)) {
            coverage = new ArrayList<>();
        }
        coverage.add(brageCoverage);
    }

    /**
     * Add a single date.
     * @param brageDate A date.
     */
    public void addDate(BrageDate brageDate) {
        if (isNull(dates)) {
            dates = new ArrayList<>();
        }
        dates.add(brageDate);
    }

    /**
     * Add a single identifier.
     * @param brageIdentifier An identifier.
     */
    public void addIdentifier(BrageIdentifier brageIdentifier) {
        if (isNull(identifiers)) {
            identifiers = new ArrayList<>();
        }
        identifiers.add(brageIdentifier);
    }

    /**
     * Adds a single description.
     * @param brageDescription A description.
     */
    public void addDescription(BrageDescription brageDescription) {
        if (isNull(descriptions)) {
            descriptions = new ArrayList<>();
        }
        descriptions.add(brageDescription);
    }

    /**
     * Adds a single creator.
     * @param brageCreator A creator.
     */
    public void addCreator(BrageCreator brageCreator) {
        if (isNull(creators)) {
            creators = new ArrayList<>();
        }
        creators.add(brageCreator);
    }

    /**
     * Adds a single format.
     * @param brageFormat A format.
     */
    public void addFormat(BrageFormat brageFormat) {
        if (isNull(formats)) {
            formats = new ArrayList<>();
        }
        formats.add(brageFormat);
    }

    /**
     * Adds a single language.
     * @param brageLanguage A language.
     */
    public void addLanguage(BrageLanguage brageLanguage) {
        if (isNull(languages)) {
            languages = new ArrayList<>();
        }
        languages.add(brageLanguage);
    }

    /**
     * Adds a single provenance.
     * @param brageProvenance A provenance.
     */
    public void addProvenance(BrageProvenance brageProvenance) {
        if (isNull(provenances)) {
            provenances = new ArrayList<>();
        }
        provenances.add(brageProvenance);
    }

    /**
     * Adds a single publisher.
     * @param bragePublisher A publisher.
     */
    public void addPublisher(BragePublisher bragePublisher) {
        if (isNull(publishers)) {
            publishers = new ArrayList<>();
        }
        publishers.add(bragePublisher);
    }

    /**
     * Adds a single relation.
     * @param brageRelation A relation.
     */
    public void addRelation(BrageRelation brageRelation) {
        if (isNull(relations)) {
            relations = new ArrayList<>();
        }
        relations.add(brageRelation);
    }

    /**
     * Adds a single rights.
     * @param brageRights A rights.
     */
    public void addRights(BrageRights brageRights) {
        if (isNull(rights)) {
            rights = new ArrayList<>();
        }
        rights.add(brageRights);
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
                && Objects.equals(getRights(), that.getRights());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getContributors(), getCoverage(), getDates(), getIdentifiers(), getDescriptions(),
                getCreators(), getFormats(), getLanguages(), getProvenances(), getPublishers(), getRelations(),
                getRights());
    }
}
