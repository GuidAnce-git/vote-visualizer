package iota.participationPlugin.entity.singleEvent;

import java.io.Serializable;

public class SingleEventParticipationPluginDO implements Serializable {
    public SingleEventDataDO data;

    public SingleEventDataDO getData() {
        return data;
    }

    public void setData(SingleEventDataDO data) {
        this.data = data;
    }
}
