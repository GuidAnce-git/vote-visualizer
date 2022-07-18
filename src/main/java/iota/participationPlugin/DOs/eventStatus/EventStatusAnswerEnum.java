package iota.participationPlugin.DOs.eventStatus;

public enum EventStatusAnswerEnum {
    UPCOMING("upcoming"),
    COMMENCING("commencing"),
    HOLDING("holding"),
    ENDED("ended");

    EventStatusAnswerEnum(final String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
