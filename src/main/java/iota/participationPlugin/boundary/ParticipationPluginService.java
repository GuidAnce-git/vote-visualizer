package iota.participationPlugin.boundary;

import iota.participationPlugin.entity.response.events.EventsRootResponseDO;
import iota.participationPlugin.entity.response.singleEvent.SingleEventRootResponseDO;
import iota.participationPlugin.entity.response.singleEvent.SingleEventRootStatusResponseDO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/api/plugins/participation")
@RegisterRestClient(configKey = "participationPlugin-api")
@Singleton
public interface ParticipationPluginService {


    @GET
    @Path("/events")
    @Produces("application/json")
    EventsRootResponseDO getParticipationEvents();


    @GET
    @Path("/events/{eventID}")
    @Produces("application/json")
    SingleEventRootResponseDO getSingleParticipationEvent(@PathParam("eventID") String eventID);


    @GET
    @Path("/events/{eventID}/status")
    @Produces("application/json")
    SingleEventRootStatusResponseDO getParticipationEventStatus(@PathParam("eventID") String eventID);

}
