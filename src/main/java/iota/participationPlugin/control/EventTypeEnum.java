package iota.participationPlugin.control;

public enum EventTypeEnum {
    STAKE("stake"),
    REWARD("reward");

    private final String name;

    EventTypeEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
