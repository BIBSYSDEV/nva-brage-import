package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import no.unit.nva.model.Reference;
import no.unit.nva.model.exceptions.InvalidIsbnException;
import no.unit.nva.model.exceptions.InvalidIssnException;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RandomReference {

    public static final URI FAKE_DOI = URI.create("https://doi.org/10.1000/123-fake");
    public static final String EN_US = "en_US";
    private final Reference reference;
    private final List<DcValue> dcValues = new ArrayList<>();

    public RandomReference(Class<?> instance) throws InvalidIsbnException,
            MalformedURLException, InvalidIssnException {
        reference = generateReference(instance);
    }

    public Reference getReference() {
        return reference;
    }

    public List<DcValue> getDcValues() {
        return dcValues;
    }

    private Reference generateReference(Class<?> instance) throws
            InvalidIsbnException,
            InvalidIssnException, MalformedURLException {
        var publicationContext = new RandomPublishingContext(instance);
        dcValues.addAll(publicationContext.getDcValues());
        var publicationInstance = new RandomPublicationInstance(instance);
        dcValues.addAll(publicationInstance.getDcValues());
        var fakeDoi = FAKE_DOI;
        dcValues.add(getDoi(fakeDoi));
        return new Reference.Builder()
                .withDoi(fakeDoi)
                .withPublishingContext(publicationContext.getPublicationContext())
                .withPublicationInstance(publicationInstance.getPublicationInstance())
                .build();
    }

    private DcValue getDoi(URI doi) {
        return new DcValueBuilder()
                .withElement(IdentifierType.IDENTIFIER)
                .withLanguage(EN_US)
                .withQualifier(IdentifierType.DOI.getQualifier())
                .withValue(doi.toString())
                .build();
    }
}
