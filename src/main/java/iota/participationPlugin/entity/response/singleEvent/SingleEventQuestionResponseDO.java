package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;
import java.util.Set;

public class SingleEventQuestionResponseDO implements Serializable {

    private String text;
    private Set<SingleEventAnswerResponseDO> answers;
    private String additionalInfo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<SingleEventAnswerResponseDO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<SingleEventAnswerResponseDO> answers) {
        this.answers = answers;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


}
