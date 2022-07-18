package iota.participationPlugin;

import iota.participationPlugin.DOs.eventStatus.EventStatusAnswerEnum;
import iota.participationPlugin.DOs.eventStatus.EventStatusParticipationPluginDO;
import iota.participationPlugin.DOs.events.EventsParticipationPluginDO;
import iota.participationPlugin.DOs.singleEvent.SingleEventParticipationPluginDO;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

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
