package iota.participationPlugin;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/api/plugins/participation")
@RegisterRestClient(configKey="participationPlugin-api")
@Singleton
public interface ParticipationPluginService {

    @GET
    @Path("/events")
    @Produces("application/json")
    ParticipationPluginDO getParticipationEvents();

    @GET
    @Path("/events/{eventID}")
    @Produces("application/json")
    ParticipationPluginDO getParticipationEvent(@PathParam("eventID") String eventID);
}
