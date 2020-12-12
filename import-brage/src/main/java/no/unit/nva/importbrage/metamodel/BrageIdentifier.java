package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageIdentifier {
    /*

        Generated from an XML element like:

            <dcvalue element="identifier" qualifier="citation" language="en_US">Henvisning&#x20;til
            &#x20;publiseringskanal&#x20;for&#x20;originalversjon.&#x20;F.eks.&#x20;Acta&#x20;Orthopaedica.&#x20;2014,
            #x20;85&#x20;(5),&#x20;463-469)</dcvalue>
            <dcvalue element="identifier" qualifier="isbn">1234567890123</dcvalue>

        Values according to:
            
            dc.identifier.citation    Human-readable, standard bibliographic citation of non-DSpace format of this item.
            dc.identifier.cristin     CRIStin ID for an item
            dc.identifier.doi         Document Object Identifier.
            dc.identifier.isbn        International Standard Book Number.
            dc.identifier.ismn        International Standard Music Number.
            dc.identifier.issn        International Standard Serial Number.
            dc.identifier.other       A known identifier type common to a local collection.
            dc.identifier.pmid        PubMed is a search engine for the MEDLINE database on life sciences and biomedical
                                      topics.
            dc.identifier.slug        URI supplied via the sword slug header, as a suggested uri for the item.
            dc.identifier.uri         Do not edit; Uniform Resource Identifier.
            dc.identifier.urn         National URN (used for import from DUO).
            dc.identifier             Catch-all for unambiguous identifiers not defined by qualified form; use 
                                      identifier.other for a known identifier common to a local collection instead of 
                                      unqualified form.
     */

    private final IdentifierType identifierType;
    private final String value;

    public BrageIdentifier(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageIdentifier(String identifierType, String value) throws InvalidQualifierException {
        this(IdentifierType.getTypeByName(identifierType), value);
    }

    public BrageIdentifier(IdentifierType identifierType, String value) {
        this.identifierType = identifierType;
        this.value = value;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public String getValue() {
        return value;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageIdentifier)) {
            return false;
        }
        BrageIdentifier that = (BrageIdentifier) o;
        return getIdentifierType() == that.getIdentifierType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getIdentifierType(), getValue());
    }
}