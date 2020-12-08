package no.unit.nva.importbrage;


import no.unit.nva.importbrage.metamodel.BrageContributor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlImportTest {

    public static final String OVERCOMPLETE_EXAMPLE = "/overcomplete_example.xml";
    public static final Set<String> LANGUAGE_VALUES = Set.of("*", "en", "en_US");

    @Test
    void xmlImportLoadsData() throws IOException {
        var contributors = XmlImport.map(new File(getClass().getResource(OVERCOMPLETE_EXAMPLE).getFile()));
        contributors.forEach(this::testContributor);
    }

    private void testContributor(BrageContributor value) {
        assertNotNull(value.getContributorType());
        assertNotNull(value.getValue());
    }

    private boolean isNullOrDefaultValue(DcValue value) {
        return isNull(value.getLanguage()) || LANGUAGE_VALUES.contains(value.getLanguage());
    }
}