package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;

public class SingleEventAnswerStatusResponseDO implements Serializable {
    private Long value;
    private Long current;
    private Long accumulated;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(Long accumulated) {
        this.accumulated = accumulated;
    }
}
