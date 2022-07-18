package iota.participationPlugin.control;

import io.quarkus.scheduler.Scheduled;
import iota.participationPlugin.boundary.ParticipationPluginService;
import iota.participationPlugin.entity.*;
import iota.participationPlugin.entity.response.events.EventsRootResponseDO;
import iota.participationPlugin.entity.mapper.QuestionMapper;
import iota.participationPlugin.entity.response.singleEvent.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * The type Participation plugin ba.
 */
@ApplicationScoped
public class ParticipationPluginBA {

    @Inject
    @RestClient
    ParticipationPluginService participationPluginService;

    @Inject
    QuestionMapper questionMapper;

    @Inject
    IotaUnitConverter iotaUnitConverter;

    @Inject
    Logger LOG;





    /**
     * Get all event IDs.
     */
    @Transactional
    @Scheduled(every="1h")
    public void getAllEventIds(){
        LOG.info("Trying to update event IDs");
        EventsRootResponseDO eventsResponse = participationPluginService.getParticipationEvents();
        for (String value : eventsResponse.getData().getEventIds()) {
            if (SingleEventDataEntity.count("eventId", value) == 0) {
                SingleEventDataEntity singleEventDataEntity = new SingleEventDataEntity();
                singleEventDataEntity.setEventId(value);
                singleEventDataEntity.persist();
                LOG.info("New event ID added");
            }
        }
    }


    /**
     * Enrich all events with additional data.
     */
    @Transactional
    @Scheduled(every="10s", delayed = "10s")
    public void getAllEventsData(){
        LOG.info("Trying to enrich all events with new data");
        List<SingleEventDataEntity> singleEventDataEntityList = SingleEventDataEntity.listAll();
        for (SingleEventDataEntity value : singleEventDataEntityList) {
            SingleEventRootResponseDO singleEventRootResponse = participationPluginService.getSingleParticipationEvent(value.getEventId());

            LOG.info("setting root data information");
            value.setName(singleEventRootResponse.getData().getName());
            value.setMilestoneIndexCommence(singleEventRootResponse.getData().getMilestoneIndexCommence());
            value.setMilestoneIndexStart(singleEventRootResponse.getData().getMilestoneIndexStart());
            value.setMilestoneIndexEnd(singleEventRootResponse.getData().getMilestoneIndexEnd());
            value.setAdditionalInfo(singleEventRootResponse.getData().getAdditionalInfo());

            if (value.getPayload() == null){
                LOG.info("Payload NULL. Setting new one");
                value.setPayload(new SingleEventPayloadEntity());
            }
            LOG.info("Adding payload data");
            value.getPayload().setType(singleEventRootResponse.getData().getPayload().getType());
            value.getPayload().setText(singleEventRootResponse.getData().getPayload().getText());
            value.getPayload().setSymbol(singleEventRootResponse.getData().getPayload().getSymbol());
            value.getPayload().setNumerator(singleEventRootResponse.getData().getPayload().getNumerator());
            value.getPayload().setDenominator(singleEventRootResponse.getData().getPayload().getDenominator());
            value.getPayload().setRequiredMinimumRewards(singleEventRootResponse.getData().getPayload().getRequiredMinimumRewards());
            value.getPayload().setAdditionalInfo(singleEventRootResponse.getData().getPayload().getAdditionalInfo());

            if (singleEventRootResponse.getData().getPayload().getQuestions() != null){
                LOG.info("question/answer NULL. Setting new one");
                if(value.getPayload().getQuestions() == null){
                    value.getPayload().setQuestions(new HashSet<>());
                }
                for (SingleEventQuestionResponseDO singleEventQuestionResponseDO : singleEventRootResponse.getData().getPayload().getQuestions()){
                    if (!value.getPayload().getQuestions().isEmpty()){
                        for (SingleEventQuestionsEntity questionValue : value.getPayload().getQuestions()){
                            if (!questionValue.getText().equals(singleEventQuestionResponseDO.getText())){
                                value.getPayload().getQuestions().add(questionMapper.mapQuestionDOtoEntity(singleEventQuestionResponseDO));
                                LOG.info("Adding question/answer data");
                            }
                        }
                    } else {
                        LOG.info("Adding question/answer data");
                        value.getPayload().getQuestions().add(questionMapper.mapQuestionDOtoEntity(singleEventQuestionResponseDO));
                    }
                }
            }
        }
        getAllEventsStatus(singleEventDataEntityList);
    }


    // add status information to event
    @Transactional
    private void getAllEventsStatus(List<SingleEventDataEntity> singleEventDataEntityList){
        LOG.info("Trying to enrich all events with new status data");
        for (SingleEventDataEntity value : singleEventDataEntityList) {
            SingleEventRootStatusResponseDO singleEventRootStatusResponseDO = participationPluginService.getParticipationEventStatus(value.getEventId());
            value.setMilestoneIndex(singleEventRootStatusResponseDO.getData().getMilestoneIndex());
            value.setStatus(singleEventRootStatusResponseDO.getData().getStatus());
            value.setChecksum(singleEventRootStatusResponseDO.getData().getChecksum());

            // add staking information
            if (singleEventRootStatusResponseDO.getData().getStaking() != null){
                if(value.getStaking() == null){
                    LOG.info("Staking NULL. Setting new one");
                    value.setStaking(new SingleEventStakingEntity());
                }
                LOG.info("Adding staking data");
                value.getStaking().setStaked(iotaUnitConverter.convertFromIotaToUnits(singleEventRootStatusResponseDO.getData().getStaking().getStaked()));
                value.getStaking().setRewarded(iotaUnitConverter.convertFromIotaToUnits(singleEventRootStatusResponseDO.getData().getStaking().getRewarded()));
                value.getStaking().setSymbol(singleEventRootStatusResponseDO.getData().getStaking().getSymbol());
            }

            // add answer information
            if (singleEventRootStatusResponseDO.getData().getQuestions() != null){
                LOG.info("Enrich all answers with new status data");
                Set<SingleEventQuestionStatusResponseDO> singleEventQuestionStatusResponseDOSet = singleEventRootStatusResponseDO.getData().getQuestions();
                Set<SingleEventAnswerStatusResponseDO> singleEventAnswerStatusResponseDOsSet = new HashSet<>();
                for (SingleEventQuestionStatusResponseDO singleEventQuestionStatusResponseDO : singleEventQuestionStatusResponseDOSet) {
                    singleEventAnswerStatusResponseDOsSet.addAll(singleEventQuestionStatusResponseDO.getAnswers());
                }

                for (SingleEventAnswerStatusResponseDO singleEventAnswerStatusResponseDO : singleEventAnswerStatusResponseDOsSet) {
                    for (SingleEventQuestionsEntity singleEventQuestionsEntity : value.getPayload().getQuestions()) {
                        for (SingleEventAnswersEntity singleEventAnswersEntity : singleEventQuestionsEntity.getAnswers()) {
                            if (singleEventAnswersEntity.getValue().equals(singleEventAnswerStatusResponseDO.getValue())){
                                singleEventAnswersEntity.setCurrent(singleEventAnswerStatusResponseDO.getCurrent());
                                singleEventAnswersEntity.setAccumulated(singleEventAnswerStatusResponseDO.getAccumulated());
                            }
                        }
                    }
                }
            }
        }
    }

}
