package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;
import java.util.Set;

public class SingleEventQuestionStatusResponseDO implements Serializable {

    private Set<SingleEventAnswerStatusResponseDO> answers;

    public Set<SingleEventAnswerStatusResponseDO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<SingleEventAnswerStatusResponseDO> answers) {
        this.answers = answers;
    }
}
