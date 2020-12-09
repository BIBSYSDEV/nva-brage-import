package no.unit.nva.importbrage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCoverage;
import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BragePublication;

import java.io.File;
import java.io.IOException;

public final class XmlImport {

    private static final ObjectMapper objectMapper = new XmlMapper();
    public static final String CONTRIBUTOR = "contributor";
    public static final String COVERAGE = "coverage";
    public static final String DATE = "date";

    private XmlImport() {

    }

    /**
     * Returns a BragePublication from a Brage qualified Dublin Core xml export.
     * @param file An XML file.
     * @return A list of contributor objects.
     * @throws IOException If the file cannot be found.
     */
    public static BragePublication map(File file) throws IOException {
        var dublinCore = objectMapper.readValue(file, DublinCore.class);
        var bragePublication = new BragePublication();
        dublinCore.getDcValues().forEach(value -> updateBragePublication(value, bragePublication));
        return bragePublication;
    }

    private static void updateBragePublication(DcValue value, BragePublication publication) {
        var element = value.getElement();
        if (CONTRIBUTOR.equals(element)) {
            publication.addContributor(new BrageContributor(value));
        }
        if (COVERAGE.equals(element)) {
            publication.addCoverage(new BrageCoverage(value));
        }
        if (DATE.equals(element)) {
            publication.setDate(new BrageDate(value));
        }
    }
}
