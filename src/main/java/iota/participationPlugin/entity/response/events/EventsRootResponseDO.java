package iota.participationPlugin.entity.response.events;

import java.io.Serializable;

public class EventsRootResponseDO implements Serializable {
    public EventDataResponseDO data;

    public EventDataResponseDO getData() {
        return data;
    }

    public void setData(EventDataResponseDO data) {
        this.data = data;
    }
}
