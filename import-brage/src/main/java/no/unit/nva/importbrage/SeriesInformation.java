package no.unit.nva.importbrage;


public class SeriesInformation {
    private final String seriesTitle;
    private final String seriesNumber;

    public SeriesInformation(String seriesTitle, String seriesNumber) {
        this.seriesTitle = seriesTitle;
        this.seriesNumber = seriesNumber;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }
}
