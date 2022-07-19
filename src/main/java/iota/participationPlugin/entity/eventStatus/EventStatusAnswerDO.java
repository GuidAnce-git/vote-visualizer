package iota.participationPlugin.entity.eventStatus;

import java.io.Serializable;

public class EventStatusAnswerDO implements Serializable {
    public int value;
    public Object current;
    public Object accumulated;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Object getCurrent() {
        return current;
    }

    public void setCurrent(Object current) {
        this.current = current;
    }

    public Object getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(Object accumulated) {
        this.accumulated = accumulated;
    }
}
