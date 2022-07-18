package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;

public class SingleEventRootResponseDO implements Serializable {

    private SingleEventDataResponseDO data;

    public SingleEventDataResponseDO getData() {
        return data;
    }

    public void setData(SingleEventDataResponseDO data) {
        this.data = data;
    }
}
