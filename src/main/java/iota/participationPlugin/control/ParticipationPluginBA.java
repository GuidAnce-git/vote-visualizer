package iota.participationPlugin.control;

import iota.participationPlugin.entity.eventStatus.EventStatusAnswerEnum;
import iota.participationPlugin.entity.eventStatus.EventStatusParticipationPluginDO;
import iota.participationPlugin.entity.events.EventsParticipationPluginDO;
import iota.participationPlugin.entity.singleEvent.SingleEventParticipationPluginDO;
import iota.participationPlugin.boundary.ParticipationPluginService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class ParticipationPluginBA {

    @Inject
    @RestClient
    ParticipationPluginService participationPluginService;


    public Set<SingleEventParticipationPluginDO> getOpenParticipationEvents(){
        EventsParticipationPluginDO eventsParticipationPluginDO = participationPluginService.getParticipationEvents();
        Set<SingleEventParticipationPluginDO> participationPluginsTypeZeroSet = new HashSet<>();
        for (String event : eventsParticipationPluginDO.getData().getEventIds()) {
            SingleEventParticipationPluginDO participationPluginEvents = participationPluginService.getParticipationEvent(event);
            EventStatusParticipationPluginDO eventStatusParticipationPlugin = participationPluginService.getParticipationEventStatus(event);
            if (participationPluginEvents.getData().getPayload().getType() == 0 && Objects.equals(eventStatusParticipationPlugin.getData().getStatus(), EventStatusAnswerEnum.COMMENCING.getValue())){
                participationPluginsTypeZeroSet.add(participationPluginEvents);
            }
        }
        return participationPluginsTypeZeroSet;
    }

    public Set<SingleEventParticipationPluginDO> getParticipationEventNames(){
        EventsParticipationPluginDO eventsParticipationPluginDO = participationPluginService.getParticipationEvents();
        Set<SingleEventParticipationPluginDO> participationPluginSet = new HashSet<>();
        for (String event : eventsParticipationPluginDO.getData().getEventIds()) {
            SingleEventParticipationPluginDO participationPluginEvents = participationPluginService.getParticipationEvent(event);
            participationPluginSet.add(participationPluginEvents);
        }
        return participationPluginSet;
    }
}
