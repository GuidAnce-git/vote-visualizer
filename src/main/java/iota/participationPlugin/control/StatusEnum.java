package iota.participationPlugin.control;

public enum StatusEnum {
    ENDED("ended"),
    HOLDING("holding");

    private final String name;

    StatusEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
