package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.metamodel.exceptions.UnknownLanguageException;

import java.net.URI;
import java.util.Arrays;

public enum Language {
    DANISH("da", "dan", null),
    DUTCH("nl", "nld", "dut"),
    ENGLISH("en", "eng", null),
    FRENCH("fr", "fre", "fra"),
    GERMAN("de", "deu", "ger"),
    ITALIAN("it", "ita", null),
    NORWEGIAN_BOKMAAL("nb", "nob", null),
    NORWEGIAN_NYNORSK("nn", "nno", null),
    NORWEGIAN("no", "nor", null),
    SAMI(null, "smi", null),
    SPANISH("es", "spa", null),
    SWEDISH("sv", "swe", null),
    UNCODED(null, "mis", null);

    public static final int TWO_LETTER_CODE = 2;
    public static final int THREE_LETTER_CODE = 3;
    private final String iso6391;
    private final String iso6392Terminology;
    private final String iso6392Bibliographic;

    Language(String iso6391, String iso6392, String iso6392Bibliographic) {
        this.iso6391 = iso6391;
        this.iso6392Terminology = iso6392;
        this.iso6392Bibliographic = iso6392Bibliographic;
    }

    public static final String LEXVO_BASE = "http://lexvo.org/id/iso639-3/";

    public URI getUri() {
        return URI.create(LEXVO_BASE + iso6392Terminology);
    }

    public static Language getByCode(String code) throws UnknownLanguageException {
        if (code.length() == TWO_LETTER_CODE) {
            return getByTwoLetterCode(code);
        } else if (code.length() == THREE_LETTER_CODE) {
                return getByThreeLetterCode(code);
        } else {
            throw new UnknownLanguageException(code);
        }
    }

    private static Language getByTwoLetterCode(String code) throws UnknownLanguageException {
        return Arrays.stream(values())
                .filter(language -> code.equals(language.iso6391))
                .findFirst()
                .orElseThrow(() -> new UnknownLanguageException(code));
    }

    private static Language getByThreeLetterCode(String code) throws UnknownLanguageException {
        return Arrays.stream(values())
                .filter(language -> isTerminologyOrBibliographicCode(code, language))
                .findFirst()
                .orElseThrow(() -> new UnknownLanguageException(code));
    }

    private static boolean isTerminologyOrBibliographicCode(String code, Language language) {
        return code.equals(language.iso6392Terminology) || code.equals(language.iso6392Bibliographic);
    }
}
