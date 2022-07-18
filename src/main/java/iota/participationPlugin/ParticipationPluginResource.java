package iota.participationPlugin;


import iota.participationPlugin.DOs.singleEvent.SingleEventParticipationPluginDO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/ParticipationPlugin")
public class ParticipationPluginResource {
    @Inject
    ParticipationPluginBA participationPlugin;


    @GET
    @Path("/test2")
    @Produces(MediaType.TEXT_PLAIN)
    public String getOpenParticipationEvents() {
        Set<SingleEventParticipationPluginDO> participationPluginSet = participationPlugin.getOpenParticipationEvents();
        StringBuilder response = new StringBuilder();
        response.append("Available events:");
        response.append(System.getProperty("line.separator"));
        for (SingleEventParticipationPluginDO participationPluginDO : participationPluginSet) {
            response.append(participationPluginDO.getData().getName());
            response.append(": ");
            response.append(participationPluginDO.getData().getAdditionalInfo());
            response.append(System.getProperty("line.separator"));
            response.append(System.getProperty("line.separator"));
        }
        return response.toString();
    }

    @GET
    @Path("/test1")
    @Produces(MediaType.TEXT_PLAIN)
    public String getParticipationEvents() {
        Set<SingleEventParticipationPluginDO> participationPluginSet = participationPlugin.getParticipationEventNames();
        StringBuilder response = new StringBuilder();
        response.append("Available events:");
        response.append(System.getProperty("line.separator"));
        for (SingleEventParticipationPluginDO participationPluginDO : participationPluginSet) {
            if (participationPluginDO.getData().getPayload().getType() == 0) {
                response.append(participationPluginDO.getData().getName());
                response.append(": ");
                response.append(participationPluginDO.getData().getAdditionalInfo());
                response.append(System.getProperty("line.separator"));
                response.append(System.getProperty("line.separator"));
            }
        }



        response.append(System.getProperty("line.separator"));
        response.append(System.getProperty("line.separator"));
        response.append("Open events:");
        response.append(System.getProperty("line.separator"));

        return response.toString();
    }
}
