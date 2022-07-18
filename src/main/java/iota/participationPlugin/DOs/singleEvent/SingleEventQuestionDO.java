package iota.participationPlugin.DOs.singleEvent;

import java.io.Serializable;
import java.util.ArrayList;

public class SingleEventQuestionDO implements Serializable {
    public String text;
    public ArrayList<SingleEventAnswerDO> answers;
    public String additionalInfo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<SingleEventAnswerDO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<SingleEventAnswerDO> answers) {
        this.answers = answers;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
