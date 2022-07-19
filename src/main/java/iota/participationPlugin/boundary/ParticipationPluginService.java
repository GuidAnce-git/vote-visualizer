package iota.participationPlugin.boundary;

import iota.participationPlugin.entity.eventStatus.EventStatusParticipationPluginDO;
import iota.participationPlugin.entity.events.EventsParticipationPluginDO;
import iota.participationPlugin.entity.singleEvent.SingleEventParticipationPluginDO;
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
    EventsParticipationPluginDO getParticipationEvents();

    @GET
    @Path("/events/{eventID}")
    @Produces("application/json")
    SingleEventParticipationPluginDO getParticipationEvent(@PathParam("eventID") String eventID);

    @GET
    @Path("/events/{eventID}/status")
    @Produces("application/json")
    EventStatusParticipationPluginDO getParticipationEventStatus(@PathParam("eventID") String eventID);
}
