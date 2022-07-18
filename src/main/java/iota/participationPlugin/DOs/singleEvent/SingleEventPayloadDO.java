package iota.participationPlugin.DOs.singleEvent;

import java.io.Serializable;
import java.util.ArrayList;

public class SingleEventPayloadDO implements Serializable {
    public int type;
    public ArrayList<SingleEventQuestionDO> questions;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<SingleEventQuestionDO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<SingleEventQuestionDO> questions) {
        this.questions = questions;
    }

}
