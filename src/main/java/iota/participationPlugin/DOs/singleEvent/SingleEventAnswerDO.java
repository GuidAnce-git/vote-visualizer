package iota.participationPlugin.DOs.singleEvent;

import java.io.Serializable;

public class SingleEventAnswerDO implements Serializable {
    public int value;
    public String text;
    public String additionalInfo;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
