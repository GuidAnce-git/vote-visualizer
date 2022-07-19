package iota.participationPlugin.entity.eventStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class EventStatusQuestionDO implements Serializable {
    public ArrayList<EventStatusAnswerDO> answers;

    public ArrayList<EventStatusAnswerDO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<EventStatusAnswerDO> answers) {
        this.answers = answers;
    }
}
