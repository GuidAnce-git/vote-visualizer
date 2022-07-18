package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;
import java.util.Set;

public class SingleEventDataStatusResponseDO implements Serializable {

    private Long milestoneIndex;
    private String status;
    private Set<SingleEventQuestionStatusResponseDO> questions;
    private SingleEventStakingStatusResponseDO staking;
    private String checksum;

    public Long getMilestoneIndex() {
        return milestoneIndex;
    }

    public void setMilestoneIndex(Long milestoneIndex) {
        this.milestoneIndex = milestoneIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<SingleEventQuestionStatusResponseDO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<SingleEventQuestionStatusResponseDO> questions) {
        this.questions = questions;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public SingleEventStakingStatusResponseDO getStaking() {
        return staking;
    }

    public void setStaking(SingleEventStakingStatusResponseDO staking) {
        this.staking = staking;
    }
}
