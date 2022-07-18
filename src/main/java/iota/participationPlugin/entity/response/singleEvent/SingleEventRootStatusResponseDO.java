package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;

public class SingleEventRootStatusResponseDO implements Serializable {

    private SingleEventDataStatusResponseDO data;

    public SingleEventDataStatusResponseDO getData() {
        return data;
    }

    public void setData(SingleEventDataStatusResponseDO data) {
        this.data = data;
    }
}
