package no.unit.nva.brage.dublincore;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.List;

@JacksonXmlRootElement(localName = "dublin_core")
public class DublinCore implements Serializable {
    @JacksonXmlProperty(isAttribute = true, localName = "schema")
    private String schema;

    @JacksonXmlProperty(localName = "dcvalue")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DcValue> dcValues;

    public List<DcValue> getDcValues() {
        return dcValues;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setDcValues(List<DcValue> dcValues) {
        this.dcValues = dcValues;
    }

    public String getSchema() {
        return schema;
    }
}
