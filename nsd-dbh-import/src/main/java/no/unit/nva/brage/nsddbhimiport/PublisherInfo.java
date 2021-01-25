package no.unit.nva.brage.nsddbhimiport;

import no.unit.nva.model.Level;

import java.util.Map;

public class PublisherInfo {
    private final String title; // "Original tittel"
    private final String printIssn; // "Print ISSN"
    private final String onlineIssn; // "Online ISSN"
    private final boolean openAccess; // "Open Access"
    private final String npiSubjectHeading; // "NPI Fagfelt"
    private final String npiSubject; // "NPI Fagområde"
    private final Map<String, Level> level;
    private final String country; //"Utgiverland"
    private final String language; //"Språk"
    private final String url; // "Url"
    private final String alternativeTitle; // "Internasjonal tittel"

    public PublisherInfo(String title,
                         String alternativeTitle,
                         String printIssn,
                         String onlineIssn,
                         boolean openAccess,
                         String npiSubjectHeading,
                         String npiSubject,
                         Map<String, Level> level,
                         String country,
                         String language,
                         String url) {
        this.title = title;
        this.alternativeTitle = alternativeTitle;
        this.printIssn = printIssn;
        this.onlineIssn = onlineIssn;
        this.openAccess = openAccess;
        this.npiSubjectHeading = npiSubjectHeading;
        this.npiSubject = npiSubject;
        this.level = level;
        this.country = country;
        this.language = language;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAlternativeTitle() {
        return alternativeTitle;
    }

    public String getPrintIssn() {
        return printIssn;
    }

    public String getOnlineIssn() {
        return onlineIssn;
    }

    public boolean getOpenAccess() {
        return openAccess;
    }

    public String getNpiSubjectHeading() {
        return npiSubjectHeading;
    }

    public String getNpiSubject() {
        return npiSubject;
    }

    public Map<String, Level> getLevel() {
        return level;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }
}
