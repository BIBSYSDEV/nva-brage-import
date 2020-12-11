package no.unit.nva.importbrage;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;


@SuppressWarnings("PMD.UnusedPrivateField")
public class DcValue {
    @JacksonXmlProperty(localName = "element", isAttribute = true)
    private String element;
    @JacksonXmlProperty(localName = "qualifier", isAttribute = true)
    private String qualifier;
    @JacksonXmlProperty(localName = "language", isAttribute = true)
    private String language;
    @JacksonXmlText
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

    public String getLanguage() {
        return language;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
