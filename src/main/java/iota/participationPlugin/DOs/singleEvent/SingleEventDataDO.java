package iota.participationPlugin.DOs.singleEvent;

import java.io.Serializable;

public class SingleEventDataDO implements Serializable {
    public String name;
    public int milestoneIndexCommence;
    public int milestoneIndexStart;
    public int milestoneIndexEnd;
    public SingleEventPayloadDO payload;
    public String additionalInfo;

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

    public SingleEventPayloadDO getPayload() {
        return payload;
    }

    public void setPayload(SingleEventPayloadDO payload) {
        this.payload = payload;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}
