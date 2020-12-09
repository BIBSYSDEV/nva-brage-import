package no.unit.nva.importbrage;


import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCoverage;
import no.unit.nva.importbrage.metamodel.BrageDate;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlImportTest {

    public static final String OVERCOMPLETE_EXAMPLE = "/overcomplete_example.xml";

    @Test
    void xmlImportLoadsData() throws IOException {
        var publication = XmlImport.map(new File(getClass().getResource(OVERCOMPLETE_EXAMPLE).getFile()));
        publication.getContributors().forEach(this::testContributor);
        publication.getCoverage().forEach(this::testCoverage);
        publication.getDates().forEach(this::testDate);
    }

    private void testCoverage(BrageCoverage coverage) {
        assertNotNull(coverage.getType());
        assertNotNull(coverage.getValue());
    }

    private void testContributor(BrageContributor contributor) {
        assertNotNull(contributor.getContributorType());
        assertNotNull(contributor.getValue());
    }

    private void testDate(BrageDate date) {
        assertNotNull(date.getDateType());
        assertNotNull(date.getvalue());
    }
}