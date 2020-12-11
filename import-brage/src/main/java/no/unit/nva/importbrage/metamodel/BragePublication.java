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
    private List<Object> creators;

    public BragePublication() {

    }

    public List<BrageContributor> getContributors() {
        return contributors;
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

    public List<BrageCoverage> getCoverage() {
        return coverage;
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

    public List<BrageDate> getDates() {
        return dates;
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

    public List<BrageIdentifier> getIdentifiers() {
        return identifiers;
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

    public List<BrageDescription> getDescriptions() {
        return descriptions;
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
                && Objects.equals(creators, that.creators);
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getContributors(), getCoverage(), getDates(),
                getIdentifiers(), getDescriptions(), creators);
    }
}
