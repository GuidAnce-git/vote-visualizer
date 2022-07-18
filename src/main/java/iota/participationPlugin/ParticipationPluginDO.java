package iota.participationPlugin;

import java.io.Serializable;

public class ParticipationPluginDO implements Serializable {
    public DataDO data;

    public DataDO getData() {
        return data;
    }

    public void setData(DataDO data) {
        this.data = data;
    }
}
