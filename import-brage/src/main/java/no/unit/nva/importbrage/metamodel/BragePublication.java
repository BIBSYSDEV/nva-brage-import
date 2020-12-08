package no.unit.nva.importbrage.metamodel;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class BragePublication {
    private List<BrageContributor> contributors;
    private BrageCoverage coverage;

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

    public void setCoverage(BrageCoverage brageCoverage) {
        this.coverage = brageCoverage;
    }

    public BrageCoverage getCoverage() {
        return coverage;
    }
}
