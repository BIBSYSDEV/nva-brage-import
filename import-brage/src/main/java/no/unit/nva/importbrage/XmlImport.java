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
import no.unit.nva.importbrage.metamodel.BrageLanguage;
import no.unit.nva.importbrage.metamodel.BrageProvenance;
import no.unit.nva.importbrage.metamodel.BragePublication;
import no.unit.nva.importbrage.metamodel.BragePublisher;
import no.unit.nva.importbrage.metamodel.BrageRelation;
import no.unit.nva.importbrage.metamodel.BrageRights;
import no.unit.nva.importbrage.metamodel.BrageSource;
import no.unit.nva.importbrage.metamodel.BrageSubject;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static no.unit.nva.importbrage.metamodel.types.ContributorType.CONTRIBUTOR;
import static no.unit.nva.importbrage.metamodel.types.CoverageType.COVERAGE;
import static no.unit.nva.importbrage.metamodel.types.CreatorType.CREATOR;
import static no.unit.nva.importbrage.metamodel.types.DateType.DATE;
import static no.unit.nva.importbrage.metamodel.types.DescriptionType.DESCRIPTION;
import static no.unit.nva.importbrage.metamodel.types.FormatType.FORMAT;
import static no.unit.nva.importbrage.metamodel.types.IdentifierType.IDENTIFIER;
import static no.unit.nva.importbrage.metamodel.types.LanguageType.LANGUAGE;
import static no.unit.nva.importbrage.metamodel.types.ProvenanceType.PROVENANCE;
import static no.unit.nva.importbrage.metamodel.types.PublisherType.PUBLISHER;
import static no.unit.nva.importbrage.metamodel.types.RelationType.RELATION;
import static no.unit.nva.importbrage.metamodel.types.RightsType.RIGHTS;
import static no.unit.nva.importbrage.metamodel.types.SourceType.SOURCE;
import static no.unit.nva.importbrage.metamodel.types.SubjectType.SUBJECT;

public final class XmlImport {

    public static final String UNKNOWN_TYPE_LOG_MESSAGE
            = "Publication <%s> contains element \"%s\" that contains unknown type";
    public static Logger logger = LogManager.getLogger(XmlImport.class);

    private static final ObjectMapper objectMapper = new XmlMapper();
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
                updateContributor(value, publication);
                break;
            case COVERAGE:
                updateCoverage(value, publication);
                break;
            case CREATOR:
                updateCreator(value, publication);
                break;
            case DATE:
                updateDate(value, publication);
                break;
            case DESCRIPTION:
                updateDescription(value, publication);
                break;
            case FORMAT:
                updateFormat(value, publication);
                break;
            case IDENTIFIER:
                updateIdentifier(value, publication);
                break;
            case LANGUAGE:
                updateLanguage(value, publication);
                break;
            case PROVENANCE:
                updateProvenance(value, publication);
                break;
            case PUBLISHER:
                updatePublisher(value, publication);
                break;
            case RELATION:
                updateRelation(value, publication);
                break;
            case RIGHTS:
                updateRights(value, publication);
                break;
            case SOURCE:
                updateSources(value, publication);
                break;
            case SUBJECT:
                updateSubjects(value, publication);
                break;
            default:
                logUnknownType(element, originPath);
                break;
        }
    }

    private static void updateSubjects(DcValue value, BragePublication publication) {
        try {
            publication.addSubject(new BrageSubject(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateSources(DcValue value, BragePublication publication) {
        try {
            publication.addSource(new BrageSource(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateRights(DcValue value, BragePublication publication) {
        try {
            publication.addRights(new BrageRights(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateRelation(DcValue value, BragePublication publication) {
        try {
            publication.addRelation(new BrageRelation(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updatePublisher(DcValue value, BragePublication publication) {
        try {
            publication.addPublisher(new BragePublisher(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateProvenance(DcValue value, BragePublication publication) {
        try {
            publication.addProvenance(new BrageProvenance(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateLanguage(DcValue value, BragePublication publication) {
        try {
            publication.addLanguage(new BrageLanguage(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateIdentifier(DcValue value, BragePublication publication) {
        try {
            publication.addIdentifier(new BrageIdentifier(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateFormat(DcValue value, BragePublication publication) {
        try {
            publication.addFormat(new BrageFormat(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateDescription(DcValue value, BragePublication publication) {
        try {
            publication.addDescription(new BrageDescription(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateDate(DcValue value, BragePublication publication) {
        try {
            publication.addDate(new BrageDate(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateCreator(DcValue value, BragePublication publication) {
        try {
            publication.addCreator(new BrageCreator(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateCoverage(DcValue value, BragePublication publication) {
        try {
            publication.addCoverage(new BrageCoverage(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateContributor(DcValue value, BragePublication publication) {
        try {
            publication.addContributor(new BrageContributor(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
        }
    }

    private static void logUnknownType(String element, String originPath) {
        var error = String.format(UNKNOWN_TYPE_LOG_MESSAGE, originPath, element);
        errors.add(error);
        logger.warn(error);
    }
}
