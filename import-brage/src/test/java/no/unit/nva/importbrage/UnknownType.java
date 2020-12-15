package no.unit.nva.importbrage;

public enum UnknownType {
    UNKNOWN_TYPE("unknowntype");

    private final String type;

    UnknownType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
