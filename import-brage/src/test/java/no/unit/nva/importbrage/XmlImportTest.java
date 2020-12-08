package no.unit.nva.importbrage;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlImportTest {

    public static final String OVERCOMPLETE_EXAMPLE = "/overcomplete_example.xml";
    public static final String EN_US = "en_US";

    @Test
    void xmlImportLoadsData() throws IOException {
        var dublinCore = XmlImport.map(new File(getClass().getResource(OVERCOMPLETE_EXAMPLE).getFile()));
        var values = dublinCore.getDcValues();
        values.forEach(this::testDcValue);
    }

    private void testDcValue(DcValue value) {
        assertNotNull(value.getValue());
        assertNotNull(value.getElement());
        assertNotNull(value.getQualifier());
        assertTrue(isNullOrDefaultValue(value));
    }

    private boolean isNullOrDefaultValue(DcValue value) {
        return isNull(value.getLanguage()) || value.getLanguage().equals(EN_US);
    }
}