package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.TitleType;

import java.util.List;
import java.util.Random;

public class RandomMainTitle {

    private static final List<String> TITLES = List.of("Public alternatives to 马",
            "奶牛 as a contributing factor to climate change",
            "Enumerating 鸭子 in polychromatic analysis");
    private static final Random RANDOM = new Random();
    public static final String EN_US = "en_US";

    private final String mainTitle;

    public RandomMainTitle() {
        this.mainTitle = getRandomTitle();
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public DcValue getDcValue() {
        return new DcValueBuilder()
                .withElement(TitleType.TITLE)
                .withQualifier(TitleType.UNQUALIFIED.getQualifier())
                .withLanguage(EN_US)
                .withValue(mainTitle)
                .build();
    }

    private int getRandom() {
        return RANDOM.nextInt(TITLES.size() - 1);
    }

    private String getRandomTitle() {
        return TITLES.get(getRandom());
    }
}
