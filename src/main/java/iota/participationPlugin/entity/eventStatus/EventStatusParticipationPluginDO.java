package iota.participationPlugin.entity.eventStatus;

import java.io.Serializable;

public class EventStatusParticipationPluginDO implements Serializable {
    public EventStatusDataDO data;

    public EventStatusDataDO getData() {
        return data;
    }

    public void setData(EventStatusDataDO data) {
        this.data = data;
    }


}
