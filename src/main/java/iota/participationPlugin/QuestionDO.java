package iota.participationPlugin;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionDO implements Serializable {
    public String text;
    public ArrayList<AnswerDO> answers;
    public String additionalInfo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<AnswerDO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerDO> answers) {
        this.answers = answers;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
