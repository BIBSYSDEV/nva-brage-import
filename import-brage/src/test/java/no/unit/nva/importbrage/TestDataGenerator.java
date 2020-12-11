package no.unit.nva.importbrage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.metamodel.ContributorType;
import no.unit.nva.importbrage.metamodel.CoverageType;
import no.unit.nva.importbrage.metamodel.DateType;
import no.unit.nva.importbrage.metamodel.IdentifierType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static no.unit.nva.importbrage.XmlImport.CONTRIBUTOR;
import static no.unit.nva.importbrage.XmlImport.COVERAGE;
import static no.unit.nva.importbrage.XmlImport.DATE;
import static no.unit.nva.importbrage.XmlImport.IDENTIFIER;

public class TestDataGenerator {

    private static final ObjectMapper mapper = new XmlMapper.Builder(new XmlMapper()).defaultUseWrapper(false).build();
    public static final String DC = "dc";
    public static final String EN_US = "en_US";
    private final DublinCore dublinCore;
    private final List<DcValue> valueList = new ArrayList<>();

    public TestDataGenerator() {
        this.dublinCore = new DublinCore();
        this.dublinCore.setSchema(DC);
    }

    public TestDataGenerator(Map<Enum<? extends Enum<?>>, String> values) {
        this();
        values.forEach(this::extractValueType);
    }

    private void extractValueType(Enum<? extends Enum<?>> type, String value) {
        if (type instanceof ContributorType) {
            addDcValue((ContributorType) type, value);
            return;
        }
        if (type instanceof CoverageType) {
            addDcValue((CoverageType) type, value);
            return;
        }
        if (type instanceof DateType) {
            addDcValue((DateType) type, value);
            return;
        }
        if (type instanceof IdentifierType) {
            addDcValue((IdentifierType) type, value);
            return;
        }
        addUnknownValue(type, value);
    }

    private void addUnknownValue(Enum<? extends Enum<?>> type, String value) {
        valueList.add(getDcValue("unknown", type.name(), value));
    }

    public void addDcValue(ContributorType type, String value) {
        valueList.add(getDcValue(CONTRIBUTOR, type.getTypeName(), value));
    }

    public void addDcValue(CoverageType type, String value) {
        valueList.add(getDcValue(COVERAGE, type.getTypeName(), value));
    }

    public void addDcValue(DateType type, String value) {
        valueList.add(getDcValue(DATE, type.getTypeName(), value));
    }

    public void addDcValue(IdentifierType type, String value) {
        valueList.add(getDcValue(IDENTIFIER, type.getTypeName(), value));
    }

    public void writeXmlFile(File file) throws IOException {
        dublinCore.setDcValues(valueList);
        mapper.writeValue(file, dublinCore);
    }

    protected DcValue getDcValue(String element, String type, String value) {
        var dcValue = new DcValue();
        dcValue.setQualifier(type);
        dcValue.setElement(element);
        dcValue.setLanguage(EN_US);
        dcValue.setValue(value);
        return dcValue;
    }
}
