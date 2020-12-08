package no.unit.nva.importbrage;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.List;

@JacksonXmlRootElement(localName = "dublin_core")
public class DublinCore implements Serializable {
    @SuppressWarnings("PMD.UnusedPrivateField")
    @JacksonXmlProperty(isAttribute = true, localName = "schema")
    private String schema;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "dcvalue")
    private List<DcValue> dcValues;


    public List<DcValue> getDcValues() {
        return dcValues;
    }
}
