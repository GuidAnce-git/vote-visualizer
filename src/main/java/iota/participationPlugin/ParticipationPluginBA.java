package iota.participationPlugin;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ParticipationPluginBA {

    @Inject
    @RestClient
    ParticipationPluginService participationPluginService;

    public Set<String> getParticipationEventNames(){
        ParticipationPluginDO participationPlugin = participationPluginService.getParticipationEvents();
        Set<ParticipationPluginDO> participationPluginSet = new HashSet<>();
        for (String event : participationPlugin.getData().eventIds) {
            ParticipationPluginDO participationPluginEvents = participationPluginService.getParticipationEvent(event);
            participationPluginSet.add(participationPluginEvents);
        }
        return participationPluginSet.stream().map(k -> k.getData().name).collect(Collectors.toSet());
    }
}
