package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.LanguageType;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLanguage {
    public static final Random RANDOM = new Random();
    public static final String LEXVO_BASE = "http://lexvo.org/id/iso639-3/";
    public static final List<Map<String, String>> LANGUAGES = List.of(Map.of("eng", "en"),
            Map.of("nob", "nb"), Map.of("nno", "nn"));
    private URI language;
    private DcValue dcValue;

    public RandomLanguage() {
        var random = getRandom();
        this.language = getUri(0);
        this.dcValue = getDcValue(0);
    }

    public URI getLanguage() {
        return language;
    }

    public DcValue getDcValue() {
        return dcValue;
    }


    private int getRandom() {
        return RANDOM.nextInt(2);
    }

    private DcValue getDcValue(int i) {
        String code = LANGUAGES.get(i).values().stream().findAny().orElseThrow();
        return new DcValueBuilder()
                .withElement(LanguageType.LANGUAGE)
                .withQualifier(LanguageType.UNQUALIFIED.getQualifier())
                .withLanguage("en_US")
                .withValue(code)
                .build();
    }

    private URI getUri(int element) {
        String code = LANGUAGES.get(0).keySet().stream().findAny().orElseThrow();
        return URI.create(LEXVO_BASE + code);
    }
}
