package no.unit.nva.brage.nsddbhimiport.helpers;

public class JournalResource {

    public static final String SIMPLE_VALID_RESOURCE_CSV = "/simple_valid_resource.csv";
    public static final String INVALID_OLD_RESOURCE_CSV = "/invalid_old_resource.csv";
    public static final String MULTIPLE_LEVELS_RESOURCE_CSV = "/multiple_levels_resource.csv";
    public static final String INVALID_LEVEL_RESOURCE_NOT_A_NUMBER_CSV = "/invalid_level_resource_not_a_number.csv";
    public static final String INVALID_LEVEL_RESOURCE_OUT_OF_RANGE_LEVEL_CSV =
            "/invalid_level_resource_out_of_range_level.csv";
    public static final String CAT_PICTURE_CSV = "/cat_picture.csv";

    public static String validResource() {
        return SIMPLE_VALID_RESOURCE_CSV;
    }

    public static String invalidOldResource() {
        return INVALID_OLD_RESOURCE_CSV;
    }

    public static String multipleLevelValuesResource() {
        return MULTIPLE_LEVELS_RESOURCE_CSV;
    }

    public static String invalidLevelResourceNotNumber() {
        return INVALID_LEVEL_RESOURCE_NOT_A_NUMBER_CSV;
    }

    public static String invalidLevelResourceOutOfRange() {
        return INVALID_LEVEL_RESOURCE_OUT_OF_RANGE_LEVEL_CSV;
    }

    public static String getCatPicture() {
        return CAT_PICTURE_CSV;
    }


    public static String getNonExistingResource() {
        return "/nothing.here";
    }
}
