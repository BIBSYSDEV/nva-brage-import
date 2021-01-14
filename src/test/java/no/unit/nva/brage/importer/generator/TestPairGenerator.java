package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.dublincore.DublinCore;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.model.Contributor;
import no.unit.nva.model.EntityDescription;
import no.unit.nva.model.Publication;
import no.unit.nva.model.Role;
import no.unit.nva.model.exceptions.InvalidIsbnException;
import no.unit.nva.model.exceptions.InvalidIssnException;
import no.unit.nva.model.exceptions.MalformedContributorException;
import no.unit.nva.model.instancetypes.book.BookAnthology;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class TestPairGenerator {
    private static final String EN_US = "en_US";
    private final Class<?> targetType;
    private final List<DcValue> dcValues = new ArrayList<>();

    public TestPairGenerator(Class<?> targetType) {
        this.targetType = targetType;
    }

    public TestPair getTestPair() throws MalformedContributorException, InvalidIsbnException, MalformedURLException,
            InvalidIssnException {
        var contributor = targetType.equals(BookAnthology.class)
                ? new RandomContributor(Role.EDITOR)
                : new RandomContributor(true);
        dcValues.addAll(contributor.generateDcValuesForContributor());
        EntityDescription entityDescription = generateEntityDescription(List.of(contributor.generateContributor()));
        var publication = new Publication.Builder()
                .withEntityDescription(entityDescription)
                .build();
        var dublinCore = new DublinCore();
        dublinCore.setDcValues(dcValues);
        return new TestPair(dublinCore, publication);
    }

    public EntityDescription generateEntityDescription(List<Contributor> contributors) throws InvalidIsbnException, InvalidIssnException, MalformedURLException {
        var description = new RandomDescription();
        dcValues.add(description.getDcValue());
        var abztract = new RandomAbstract();
        dcValues.add(abztract.getDcValue());
        var publicationDate = new RandomPublicationDate(DateType.ISSUED);
        dcValues.add(publicationDate.getDcValue());
        var language = new RandomLanguage();
        dcValues.add(language.getDcValue());
        var alternativeTitles = new RandomAlternativeTitle();
        dcValues.add(alternativeTitles.getDcValue());
        var mainTitle = new RandomMainTitle();
        dcValues.add(mainTitle.getDcValue());
        var reference = new RandomReference(targetType);
        dcValues.addAll(reference.getDcValues());

        return new EntityDescription.Builder()
                .withContributors(contributors)
                .withDescription(description.getDescription())
                .withAbstract(abztract.getAbstract())
                .withAlternativeTitles(alternativeTitles.getAlternativeTitle())
                .withDate(publicationDate.getPublicationDate())
                .withLanguage(language.getLanguage())
                .withMainTitle(mainTitle.getMainTitle())
                .withMetadataSource(null)
                .withNpiSubjectHeading(null)
                .withReference(reference.getReference())
                .withTags(null)
                .build();
    }

    public TestPair getTestPairWithCreator(boolean matchesExistingContributor) throws MalformedContributorException,
            InvalidIsbnException, MalformedURLException,
            InvalidIssnException {
        var contributor = new RandomContributor(false);
        dcValues.addAll(contributor.generateDcValuesForContributor());
        var contributors = new ArrayList<>(List.of(contributor.generateContributor()));
        if (matchesExistingContributor) {
            var dcValue = new DcValueBuilder()
                    .withElement(CreatorType.CREATOR)
                    .withQualifier(CreatorType.UNQUALIFIED.getQualifier())
                    .withLanguage(EN_US)
                    .withValue(contributor.getName().getValue())
                    .build();
            dcValues.add(dcValue);
        } else {
            var creator = new RandomCreator();
            dcValues.add(creator.generateDcValuesForCreator());
            contributors.add(creator.generateContributor());
        }

        var publication = new Publication.Builder()
                .withEntityDescription(generateEntityDescription(contributors))
                .build();
        var dublinCore = new DublinCore();
        dublinCore.setDcValues(dcValues);
        return new TestPair(dublinCore, publication);
    }
}
