package no.unit.nva.importbrage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.importbrage.metamodel.BrageContributor;
import no.unit.nva.importbrage.metamodel.BrageCoverage;
import no.unit.nva.importbrage.metamodel.BrageCreator;
import no.unit.nva.importbrage.metamodel.BrageDate;
import no.unit.nva.importbrage.metamodel.BrageDescription;
import no.unit.nva.importbrage.metamodel.BrageFormat;
import no.unit.nva.importbrage.metamodel.BrageIdentifier;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static no.unit.nva.importbrage.metamodel.types.FormatType.FORMAT;

public final class XmlImport {

    public static final String UNKNOWN_TYPE_LOG_MESSAGE
            = "Publication <%s> contains element \"%s\" that contains unknown type";
    public static final String DESCRIPTION = "description";
    public static Logger logger = LogManager.getLogger(XmlImport.class);

    private static final ObjectMapper objectMapper = new XmlMapper();
    public static final String CONTRIBUTOR = "contributor";
    public static final String COVERAGE = "coverage";
    public static final String CREATOR = "creator";
    public static final String DATE = "date";
    public static final String IDENTIFIER = "identifier";
    public static final List<String> errors = new ArrayList<>();

    public XmlImport() {

    }

    /**
     * Returns a BragePublication from a Brage qualified Dublin Core xml export.
     * @param file An XML file.
     * @return A list of contributor objects.
     * @throws IOException If the file cannot be found.
     */
    public BragePublication map(File file) throws IOException {
        resetErrors();
        var dublinCore = objectMapper.readValue(file, DublinCore.class);
        var bragePublication = new BragePublication();
        dublinCore.getDcValues()
                .forEach(value -> updateBragePublication(value, bragePublication, file.getAbsolutePath()));
        return errors.isEmpty() ? bragePublication : null;
    }

    public List<String> getErrors() {
        return errors;
    }

    private static void resetErrors() {
        errors.clear();
    }

    private static void updateBragePublication(DcValue value, BragePublication publication, String originPath) {
        var element = value.getElement();
        switch (element) {
            case CONTRIBUTOR:
                try {
                    publication.addContributor(new BrageContributor(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            case COVERAGE:
                try {
                    publication.addCoverage(new BrageCoverage(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            case CREATOR:
                try {
                    publication.addCreator(new BrageCreator(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            case DATE:
                try {
                    publication.addDate(new BrageDate(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            case DESCRIPTION:
                try {
                    publication.addDescription(new BrageDescription(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            case FORMAT:
                try {
                    publication.addFormat(new BrageFormat(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            case IDENTIFIER:
                try {
                    publication.addIdentifier(new BrageIdentifier(value));
                } catch (InvalidQualifierException e) {
                    errors.add(e.getMessage());
                }
                break;
            default:
                logUnknownType(element, originPath);
                break;
        }
    }

    private static void logUnknownType(String element, String originPath) {
        var error = String.format(UNKNOWN_TYPE_LOG_MESSAGE, originPath, element);
        errors.add(error);
        logger.warn(error);
    }
}
