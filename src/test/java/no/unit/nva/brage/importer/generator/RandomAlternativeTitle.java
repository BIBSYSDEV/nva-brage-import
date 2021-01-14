package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.TitleType;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomAlternativeTitle {

    public static final String EN_US = "en_US";
    private static final Random RANDOM = new Random();
    private static final List<Map<String, String>> ALTERNATIVES = List.of(Map.of("en", "An alternative title"),
            Map.of("nb", "Et alternativtittel"));

    private final Map<String, String> alternativeTitle;

    public RandomAlternativeTitle() {
        this.alternativeTitle = getRandomTitle();
    }

    public Map<String, String> getAlternativeTitle() {
        return alternativeTitle;
    }

    public DcValue getDcValue() {
        var value = alternativeTitle.values().stream().findFirst().orElseThrow();
        return new DcValueBuilder()
                .withElement(TitleType.TITLE)
                .withLanguage(EN_US)
                .withQualifier(TitleType.ALTERNATIVE.getQualifier())
                .withValue(value)
                .build();
    }

    private int getRandom() {
        return RANDOM.nextInt(ALTERNATIVES.size() - 1);
    }

    private Map<String, String> getRandomTitle() {
        return ALTERNATIVES.get(getRandom());
    }
}
