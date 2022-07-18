package iota.participationPlugin;

import java.io.Serializable;
import java.util.ArrayList;

public class DataDO implements Serializable {
    public String name;
    public int milestoneIndexCommence;
    public int milestoneIndexStart;
    public int milestoneIndexEnd;
    public PayloadDO payload;
    public String additionalInfo;
    public ArrayList<String> eventIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMilestoneIndexCommence() {
        return milestoneIndexCommence;
    }

    public void setMilestoneIndexCommence(int milestoneIndexCommence) {
        this.milestoneIndexCommence = milestoneIndexCommence;
    }

    public int getMilestoneIndexStart() {
        return milestoneIndexStart;
    }

    public void setMilestoneIndexStart(int milestoneIndexStart) {
        this.milestoneIndexStart = milestoneIndexStart;
    }

    public int getMilestoneIndexEnd() {
        return milestoneIndexEnd;
    }

    public void setMilestoneIndexEnd(int milestoneIndexEnd) {
        this.milestoneIndexEnd = milestoneIndexEnd;
    }

    public PayloadDO getPayload() {
        return payload;
    }

    public void setPayload(PayloadDO payload) {
        this.payload = payload;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public ArrayList<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(ArrayList<String> eventIds) {
        this.eventIds = eventIds;
    }
}
