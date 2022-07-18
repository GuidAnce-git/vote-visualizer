package iota.participationPlugin.DOs.events;

import iota.participationPlugin.DOs.events.EventsDataDO;

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
