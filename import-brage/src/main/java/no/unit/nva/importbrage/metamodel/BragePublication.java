package no.unit.nva.importbrage.metamodel;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class BragePublication {
    private List<BrageContributor> contributors;
    private List<BrageCoverage> coverage;
    private List<BrageDate> dates;
    private List<BrageIdentifier> identifiers;

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
    public void setDate(BrageDate brageDate) {
        if (isNull(dates)) {
            dates = new ArrayList<>();
        }
        dates.add(brageDate);
    }

    public List<BrageDate> getDates() {
        return this.dates;
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
        return this.identifiers;
    }
}
