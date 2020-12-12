package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageDescription {
    /*
      <dcvalue element="description" qualifier="none" language="en_US">Dette&#x20;er&#x20;en&#x20;beskrivelse&#x20;av
          &#x20;publikasjonen,&#x20;ikke&#x20;et&#x20;sammendrag.&#x20;Brukes&#x20;i&#x20;de&#x20;tilfeller&#x20;
          dette&#x20;er&#x20;relevant&#x20;for&#x20;å&#x20;fortelle&#x20;noe&#x20;om&#x20;publikasjonen&#x20;som&#x20;
          ikke&#x20;kan&#x20;fortelles&#x20;ved&#x20;bruk&#x20;av&#x20;noen&#x20;av&#x20;de&#x20;andre&#x20;
          metadatafeltene.</dcvalue>
      <dcvalue element="description" qualifier="abstract" language="en_US">Dette&#x20;er&#x20;et&#x20;sammendrag&#x20;
          av&#x20;publikasjonen&#x20;som&#x20;ikke&#x20;sier&#x20;så&#x20;veldig&#x20;mye.</dcvalue>
      <dcvalue element="description" qualifier="abstract" language="en_US">Dette&#x20;er&#x20;enda&#x20;et&#x20;
          sammendrag&#x20;av&#x20;publikasjonen&#x20;på&#x20;et&#x20;annet&#x20;språk&#x20;enn&#x20;det&#x20;første
          &#x20;siden&#x20;det&#x20;er&#x20;mange&#x20;som&#x20;liker&#x20;å&#x20;legge&#x20;inn&#x20;sammendrag&#x20;
          på&#x20;flere&#x20;språk,&#x20;men&#x20;sjelden&#x20;mer&#x20;enn&#x20;to&#x20;forskjellige.</dcvalue>
      <dcvalue element="description" qualifier="provenance" language="en">Made&#x20;available&#x20;in&#x20;DSpace&#x20;
          on&#x20;2020-12-07T16:46:42Z&#x20;(GMT).&#x20;No.&#x20;of&#x20;bitstreams:&#x20;3&#x0A;publikasjonen.pdf:
          &#x20;84038&#x20;bytes,&#x20;checksum:&#x20;faff05c42669bebc40f32804b2c6dabd&#x20;(MD5)&#x0A;KM_
          Seilingsbestemmelser_BB_2013.pdf:&#x20;747873&#x20;bytes,&#x20;checksum:&#x20;
          58bfc42ed37a4f5835f52e5074a8e53b&#x20;(MD5)&#x0A;license_rdf:&#x20;1039&#x20;bytes,&#x20;checksum:&#x20;
          27f259be180cb0fabbc162cd48fd90bb&#x20;(MD5)&#x0A;&#x20;&#x20;Previous&#x20;issue&#x20;date:&#x20;2020-12-07
          </dcvalue>
      <dcvalue element="description" qualifier="sponsorship" language="en_US">Dette&#x20;er&#x20;for&#x20;info&#x20;
          om&#x20;sponsor,&#x20;men&#x20;sponsor&#x20;er&#x20;ikke&#x20;det&#x20;samme&#x20;som&#x20;prosjekt&#x2F;
          finansieringskilde</dcvalue>
      <dcvalue element="description" qualifier="localcode" language="en_US">Lokal&#x20;kode&#x20;som&#x20;institusonen
          &#x20;kan&#x20;bruke&#x20;til&#x20;intern&#x20;kodind,&#x20;er&#x20;søkbar&#x20;men&#x20;blir&#x20;ikke&#x20;
          vist&#x20;i&#x20;enkel&#x20;visning</dcvalue>
      <dcvalue element="description" qualifier="version" language="en_US">acceptedVersion</dcvalue>
      <dcvalue element="description" language="en_US">catchall</dcvalue>

      Values according to:
          dc.description.abstract           Abstract or summary.
          dc.description.degree             An achieved degree that might be associated with the item
                                            (used for import from DiVA).
          dc.description.embargo            Do not use; for embargo setter and lifter in items created in Dspace 1.8
                                            or earlier.
          dc.description.localcode          Can be used for codes or descriptions according to local guidelines at each
                                            institution.
          dc.description.provenance         The history of custody of the item since its creation, including any
                                            changes successive custodians made to it.
          dc.description.sponsorship        Information about sponsoring agencies, individuals, or contractual
                                            arrangements for the item.
          dc.description.tableofcontents    Used for News in metadata for archives, sub-archives and collections.
          dc.description.version            The Peer Reviewed status of an item.
          dc.description                    Catch-all for any description not defined by qualifiers.
     */


    private final DescriptionType descriptionType;
    private final String value;

    public BrageDescription(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageDescription(String qualifier, String value) throws InvalidQualifierException {
        this(DescriptionType.getTypeByName(qualifier), value);
    }

    public BrageDescription(DescriptionType descriptionType, String value) {
        this.descriptionType = descriptionType;
        this.value = value;
    }

    public DescriptionType getDescriptionType() {
        return descriptionType;
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
        if (!(o instanceof BrageDescription)) {
            return false;
        }
        BrageDescription that = (BrageDescription) o;
        return getDescriptionType() == that.getDescriptionType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getDescriptionType(), getValue());
    }
}
