package iota.participationPlugin.control;

public enum ParticipationPluginTypeEum {
    STAKING(1),
    VOTING(0);

    private final int type;

    ParticipationPluginTypeEum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
