package iota.participationPlugin;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ParticipationPlugin")
public class ParticipationPluginResource {
    @Inject
    ParticipationPluginBA participationPlugin;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getParticipationEvents(String eventID) {
        return participationPlugin.getParticipationEventNames().toString();
    }
}
