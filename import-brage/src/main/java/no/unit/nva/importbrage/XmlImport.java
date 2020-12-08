package no.unit.nva.importbrage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public final class XmlImport {

    private static final ObjectMapper objectMapper = new XmlMapper();

    private XmlImport() {

    }

    public static DublinCore map(File file) throws IOException {
        return objectMapper.readValue(file, DublinCore.class);
    }
}
