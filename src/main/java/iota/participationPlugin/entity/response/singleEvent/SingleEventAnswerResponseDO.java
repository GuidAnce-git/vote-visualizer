package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;

public class SingleEventAnswerResponseDO implements Serializable {
    private Long value;
    private String text;
    private String additionalInfo;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
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
