package iota.participationPlugin.control;

public enum IotaSymbolsEnum {
    MICRO_ASMB("microASMB", "iota_assembly-mark.png"),
    ASMB("ASMB", null),
    SMR("SMR", "iota-shimmer-mark-color.png"),
    PIOTA("PIOTA", null),
    TIOTA("TIOTA", null),
    GIOTA("GIOTA", null),
    MIOTA("MIOTA", null),
    IOTA("IOTA", null);

    private final String name;
    private final String logo;

    IotaSymbolsEnum(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }


    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }
}
