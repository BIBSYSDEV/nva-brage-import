package no.unit.nva.importbrage;


import no.unit.nva.importbrage.metamodel.BrageContributor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlImportTest {

    public static final String OVERCOMPLETE_EXAMPLE = "/overcomplete_example.xml";

    @Test
    void xmlImportLoadsData() throws IOException {
        var contributors = XmlImport.map(new File(getClass().getResource(OVERCOMPLETE_EXAMPLE).getFile()));
        contributors.forEach(this::testContributor);
    }

    private void testContributor(BrageContributor value) {
        assertNotNull(value.getContributorType());
        assertNotNull(value.getValue());
    }
}