package iota.participationPlugin.DOs.eventStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class EventStatusDataDO implements Serializable {
    public int milestoneIndex;
    public String status;
    public ArrayList<EventStatusQuestionDO> questions;
    public String checksum;

    public int getMilestoneIndex() {
        return milestoneIndex;
    }

    public void setMilestoneIndex(int milestoneIndex) {
        this.milestoneIndex = milestoneIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<EventStatusQuestionDO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<EventStatusQuestionDO> questions) {
        this.questions = questions;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
