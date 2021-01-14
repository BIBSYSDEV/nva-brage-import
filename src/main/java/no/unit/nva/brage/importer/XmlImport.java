package no.unit.nva.brage.importer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.dublincore.DublinCore;
import no.unit.nva.brage.nsddbhimiport.JournalParser;
import no.unit.nva.importbrage.BragePublicationConverter;
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
import no.unit.nva.importbrage.metamodel.BrageTitle;
import no.unit.nva.importbrage.metamodel.BrageType;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.importbrage.metamodel.types.CoverageType;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;
import no.unit.nva.importbrage.metamodel.types.FormatType;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import no.unit.nva.importbrage.metamodel.types.LanguageType;
import no.unit.nva.importbrage.metamodel.types.ProvenanceType;
import no.unit.nva.importbrage.metamodel.types.PublisherType;
import no.unit.nva.importbrage.metamodel.types.RelationType;
import no.unit.nva.importbrage.metamodel.types.RightsType;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import no.unit.nva.importbrage.metamodel.types.SubjectType;
import no.unit.nva.importbrage.metamodel.types.TitleType;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import nva.commons.utils.SingletonCollector;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class XmlImport {

    public static final String UNKNOWN_TYPE_LOG_MESSAGE
            = "Publication <%s> contains element \"%s\" that contains unknown type";

    private static final XmlMapper objectMapper = new XmlMapper();
    public static final List<String> errors = new ArrayList<>();
    private transient final JournalParser journalParser;

    public XmlImport() {
        this(new JournalParser());
    }

    public XmlImport(JournalParser journalParser) {
        this.journalParser = journalParser;
    }

    /**
     * Returns a BragePublication from a Brage qualified Dublin Core xml export.
     * @param file An XML file.
     * @return A list of contributor objects.
     * @throws IOException If the file cannot be found.
     */
    public Mapping map(File file) throws IOException {
        resetErrors();
        var dublinCore = objectMapper.readValue(file, DublinCore.class);
        var bragePublication = new BragePublication();
        dublinCore.getDcValues()
                .forEach(value -> updateBragePublication(value, bragePublication, file.getAbsolutePath()));
        var bragePublicationConverter = new BragePublicationConverter(bragePublication, journalParser);
        errors.addAll(bragePublicationConverter.getFailures().stream()
                .map(Exception::getMessage)
                .collect(Collectors.toList()));
        var brageUri = bragePublication.getIdentifiers().stream()
                .filter(BrageIdentifier::isUri)
                .map(BrageIdentifier::getValue)
                .filter(identifier -> identifier.contains("handle"))
                .map(URI::create)
                .collect(SingletonCollector.collectOrElse(null));
        return new Mapping(errors, bragePublicationConverter.getNvaPublication(), brageUri);
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
            case ContributorType.CONTRIBUTOR:
                updateContributor(value, publication);
                break;
            case CoverageType.COVERAGE:
                updateCoverage(value, publication);
                break;
            case CreatorType.CREATOR:
                updateCreator(value, publication);
                break;
            case DateType.DATE:
                updateDate(value, publication);
                break;
            case DescriptionType.DESCRIPTION:
                updateDescription(value, publication);
                break;
            case FormatType.FORMAT:
                updateFormat(value, publication);
                break;
            case IdentifierType.IDENTIFIER:
                updateIdentifier(value, publication);
                break;
            case LanguageType.LANGUAGE:
                updateLanguage(value, publication);
                break;
            case ProvenanceType.PROVENANCE:
                updateProvenance(value, publication);
                break;
            case PublisherType.PUBLISHER:
                updatePublisher(value, publication);
                break;
            case RelationType.RELATION:
                updateRelation(value, publication);
                break;
            case RightsType.RIGHTS:
                updateRights(value, publication);
                break;
            case SourceType.SOURCE:
                updateSources(value, publication);
                break;
            case SubjectType.SUBJECT:
                updateSubjects(value, publication);
                break;
            case TitleType.TITLE:
                updateTitles(value, publication);
                break;
            case TypeBasic.TYPE:
                updateType(value, publication);
                break;
            default:
                logUnknownType(element, originPath);
                break;
        }
    }

    private static void updateType(DcValue value, BragePublication publication) {
        try {
            publication.addType(new BrageType(value));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
    }

    private static void updateTitles(DcValue value, BragePublication publication) {
        try {
            publication.addTitle(new BrageTitle(value));
        } catch (InvalidQualifierException e) {
            errors.add(e.getMessage());
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
    }
}
