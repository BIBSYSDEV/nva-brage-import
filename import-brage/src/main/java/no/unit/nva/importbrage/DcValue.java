package no.unit.nva.importbrage;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("PMD.UnusedPrivateField")
@JacksonXmlRootElement(localName = "dcvalue")
@XmlAccessorType(XmlAccessType.FIELD)
public class DcValue {
    @JacksonXmlProperty(localName = "element")
    private String element;
    @JacksonXmlProperty(localName = "qualifier")
    private String qualifier;
    @JacksonXmlProperty(localName = "language")
    private String language;
    @JacksonXmlText()
    private String value;


    public String getElement() {
        return element;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getValue() {
        return value;
    }
}
