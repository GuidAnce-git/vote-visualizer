package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;

public class SingleEventDataResponseDO implements Serializable {

    private String name;
    private Long milestoneIndexCommence;
    private Long milestoneIndexStart;
    private Long milestoneIndexEnd;
    private SingleEventPayloadResponseDO payload;
    private String additionalInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMilestoneIndexCommence() {
        return milestoneIndexCommence;
    }

    public void setMilestoneIndexCommence(Long milestoneIndexCommence) {
        this.milestoneIndexCommence = milestoneIndexCommence;
    }

    public Long getMilestoneIndexStart() {
        return milestoneIndexStart;
    }

    public void setMilestoneIndexStart(Long milestoneIndexStart) {
        this.milestoneIndexStart = milestoneIndexStart;
    }

    public Long getMilestoneIndexEnd() {
        return milestoneIndexEnd;
    }

    public void setMilestoneIndexEnd(Long milestoneIndexEnd) {
        this.milestoneIndexEnd = milestoneIndexEnd;
    }

    public SingleEventPayloadResponseDO getPayload() {
        return payload;
    }

    public void setPayload(SingleEventPayloadResponseDO payload) {
        this.payload = payload;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}
