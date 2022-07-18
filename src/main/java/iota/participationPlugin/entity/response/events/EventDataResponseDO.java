package iota.participationPlugin.entity.response.events;


import java.io.Serializable;
import java.util.List;

public class EventDataResponseDO implements Serializable {

    private List<String> eventIds;

    public List<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<String> eventIds) {
        this.eventIds = eventIds;
    }
}
