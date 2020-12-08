package no.unit.nva.importbrage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.metamodel.BrageContributor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class XmlImport {

    private static final ObjectMapper objectMapper = new XmlMapper();
    public static final String CONTRIBUTOR = "contributor";

    private XmlImport() {

    }

    public static List<BrageContributor> map(File file) throws IOException {
        var dublinCore = objectMapper.readValue(file, DublinCore.class);
        return dublinCore.getDcValues().stream()
                .filter(value -> value.getElement().equals(CONTRIBUTOR))
                .map(BrageContributor::new)
                .collect(Collectors.toList());
    }
}
