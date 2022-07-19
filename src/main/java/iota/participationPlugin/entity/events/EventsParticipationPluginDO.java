package iota.participationPlugin.entity.events;

import java.io.Serializable;

public class EventsParticipationPluginDO implements Serializable {
    public EventsDataDO data;

    public EventsDataDO getData() {
        return data;
    }

    public void setData(EventsDataDO data) {
        this.data = data;
    }
}
