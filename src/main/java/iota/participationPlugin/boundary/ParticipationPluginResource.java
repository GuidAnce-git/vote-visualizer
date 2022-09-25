package iota.participationPlugin.boundary;

import io.quarkus.panache.common.Sort;
import iota.participationPlugin.control.ParticipationPluginTypeEum;
import iota.participationPlugin.entity.SingleEventDataEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/test")
public class ParticipationPluginResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SingleEventDataEntity> getAllEvents() {
        return SingleEventDataEntity.listAll(Sort.by("name"));
    }

    @GET
    @Path("/staking")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SingleEventDataEntity> getAllStakingEvents() {
        return SingleEventDataEntity.findEntitiesByTypeAndActive(ParticipationPluginTypeEum.STAKING.getType());
    }

    @GET
    @Path("/voting")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SingleEventDataEntity> getAllVotingEvents() {
        return SingleEventDataEntity.findEntitiesByTypeAndActive(ParticipationPluginTypeEum.VOTING.getType());
    }
/*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createInputData(){
        participationPlugin.getAllEventIds();

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Uni<List<DataResponseDO>> list() {
        return DataResponseDO.listAll();
    }

    @POST
    @Transactional
    public Response create(SingleEventParticipationPluginDO singleEventParticipationPluginDO) {
        ParticipationPluginEntity participationPluginEntity = new ParticipationPluginEntity();
        participationPluginEntity.setName(singleEventParticipationPluginDO.getData().getName());
        participationPluginEntity.setMilestoneIndexCommence(singleEventParticipationPluginDO.getData().getMilestoneIndexCommence());
        participationPluginEntity.setMilestoneIndexStart(singleEventParticipationPluginDO.getData().getMilestoneIndexStart());
        participationPluginEntity.setMilestoneIndexEnd(singleEventParticipationPluginDO.getData().getMilestoneIndexEnd());
        participationPluginEntity.setAdditionalInfo(singleEventParticipationPluginDO.getData().getAdditionalInfo());
        return Response.created(URI.create("/test/" + participationPluginEntity.id)).build();
    }


    @Inject
    ParticipationPluginBA participationPlugin;


    @GET
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

 */
}
