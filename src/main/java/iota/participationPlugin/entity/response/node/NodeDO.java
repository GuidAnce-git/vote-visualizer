package iota.participationPlugin.entity.response.node;

import java.io.Serializable;

public class NodeDO implements Serializable {

    private NodeDataDO data;

    public NodeDataDO getData() {
        return data;
    }

    public void setData(NodeDataDO data) {
        this.data = data;
    }
}
