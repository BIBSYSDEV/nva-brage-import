package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.DoiException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.IdentifierType;
import nva.commons.utils.JacocoGenerated;

import java.net.URI;
import java.util.Objects;

import static no.unit.nva.importbrage.metamodel.types.IdentifierType.ISBN;
import static no.unit.nva.importbrage.metamodel.types.IdentifierType.ISSN;

public class BrageIdentifier extends BrageLanguageValue {
    public static final String HTTP_DOI_NAMESPACE = "http://doi.org/";
    public static final String HTTPS_DOI_NAMESPACE = "https://doi.org/";
    public static final String HTTP_DX_DOI_NAMESPACE = "http://dx.doi.org/";
    public static final String HTTPS_DX_DOI_NAMESPACE = "https://dx.doi.org/";
    public static final String RAW_DOI_PREFIX = "10";
    private static final String DOI_IS_NOT_A_URN = "doi:";
    private static final String DOI_DOC = "doc:";

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

    public BrageIdentifier(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageIdentifier(String identifierType, String value, String language) throws InvalidQualifierException {
        this(IdentifierType.getTypeByName(identifierType, value), value, language);
    }

    public boolean isDoi() {
        return identifierType.equals(IdentifierType.DOI);
    }

    public URI asDoi() throws DoiException {
        var value = getValue();
        if (value.startsWith(HTTP_DOI_NAMESPACE)) {
            return createDoiUri(value.replace(HTTP_DOI_NAMESPACE, HTTPS_DOI_NAMESPACE));
        } else if (value.startsWith(HTTP_DX_DOI_NAMESPACE)) {
            return createDoiUri(value.replace(HTTP_DX_DOI_NAMESPACE, HTTPS_DOI_NAMESPACE));
        } else if (value.startsWith(HTTPS_DX_DOI_NAMESPACE)) {
            return createDoiUri(value.replace(HTTPS_DX_DOI_NAMESPACE, HTTPS_DOI_NAMESPACE));
        } else if (value.startsWith(HTTPS_DOI_NAMESPACE)) {
            return createDoiUri(value);
        } else if (value.startsWith(DOI_IS_NOT_A_URN)) {
            return createDoiUri(value.replace(DOI_IS_NOT_A_URN, HTTPS_DOI_NAMESPACE));
        } else if (value.startsWith(DOI_DOC)) {
            return createDoiUri(value.replace(DOI_DOC, HTTPS_DOI_NAMESPACE));
        } else if (value.startsWith(RAW_DOI_PREFIX)) {
            return createDoiUri(HTTPS_DOI_NAMESPACE + value);
        } else {
            throw new DoiException(value);
        }
    }

    private URI createDoiUri(String value) {
        return URI.create(value.trim());
    }

    public BrageIdentifier(IdentifierType identifierType, String value, String language) {
        super(value, language);
        this.identifierType = identifierType;
    }

    public boolean isIsbn() {
        return ISBN.equals(identifierType);
    }

    public boolean isIssn() {
        return ISSN.equals(identifierType);
    }

    public boolean isUri() {
        return IdentifierType.URI.equals(identifierType);
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageIdentifier that = (BrageIdentifier) o;
        return getIdentifierType() == that.getIdentifierType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdentifierType());
    }
}