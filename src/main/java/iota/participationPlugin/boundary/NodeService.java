package iota.participationPlugin.boundary;

import iota.participationPlugin.entity.response.node.NodeDO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/api/v1")
@RegisterRestClient(configKey = "node-api")
@Singleton
public interface NodeService {

    @GET
    @Path("/info")
    @Produces("application/json")
    NodeDO getNodeInfo();
}
