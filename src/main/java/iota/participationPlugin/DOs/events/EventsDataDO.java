package iota.participationPlugin.DOs.events;

import java.io.Serializable;
import java.util.ArrayList;

public class EventsDataDO implements Serializable {
    public ArrayList<String> eventIds;

    public ArrayList<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(ArrayList<String> eventIds) {
        this.eventIds = eventIds;
    }
}
