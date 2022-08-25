package iota.participationPlugin.control;

import io.quarkus.scheduler.Scheduled;
import iota.participationPlugin.boundary.ParticipationPluginService;
import iota.participationPlugin.entity.*;
import iota.participationPlugin.entity.mapper.QuestionMapper;
import iota.participationPlugin.entity.response.events.EventsRootResponseDO;
import iota.participationPlugin.entity.response.singleEvent.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    private static final int TIME_BETWEEN_MILESTONES = 10;

    @Inject
    @RestClient
    ParticipationPluginService participationPluginService;

    @Inject
    QuestionMapper questionMapper;

    @Inject
    UnitConverter unitConverter;


    @PersistenceContext
    EntityManager entityManager;

    @Inject
    Logger LOG;

    private static void calcDuration(SingleEventDataEntity value) {
        if (!StatusEnum.ENDED.getName().equals(value.getStatus())) {
            long secondsToEnd = (value.getMilestoneIndexEnd() - value.getMilestoneIndex()) * TIME_BETWEEN_MILESTONES;
            if (secondsToEnd <= TimeUnit.DAYS.toSeconds(1)) {
                value.setEventEndsIn("within 24h");
            } else {
                value.setEventEndsIn(TimeUnit.SECONDS.toDays(secondsToEnd) + " days left");
            }
        } else {
            value.setEventEndsIn(StatusEnum.ENDED.getName());
        }
    }

    private static void setIcon(SingleEventDataEntity value) {
        if (value.getStaking() != null && IotaSymbolsEnum.MICRO_ASMB.getName().equals(value.getStaking().getSymbol())) {
            value.setIcon(IotaSymbolsEnum.MICRO_ASMB.getLogo());
        }
        if (value.getStaking() != null && IotaSymbolsEnum.SMR.getName().equals(value.getStaking().getSymbol())) {
            value.setIcon(IotaSymbolsEnum.SMR.getLogo());
        }
    }

    private static void setAdvancedName(SingleEventDataEntity value) {
        if (value.getPayload() != null && value.getPayload().getText() != null) {
            value.setAdvancedName(value.getPayload().getText());
        } else {
            value.setAdvancedName(value.getName());
        }
    }

    private static void createHistoricalEntry(SingleEventDataEntity value) {
        if (!StatusEnum.ENDED.getName().equals(value.getStatus()) && value.getStaking() != null) {
            SingleEventHistoryEntity singleEventHistoryEntity = new SingleEventHistoryEntity();
            singleEventHistoryEntity.setEventId(value.getEventId());
            singleEventHistoryEntity.setStaked(value.getStaking().getStaked());
            singleEventHistoryEntity.setRewarded(value.getStaking().getRewarded());
            singleEventHistoryEntity.persist();
        }

    }

    private static void setColor(SingleEventDataEntity value, Double growth) {
        if ((100.0d - growth) > 0.0d) {
            value.getStaking().setPercentColor("success");
        }
        if ((100.0d - growth) == 0.0d) {
            value.getStaking().setPercentColor("secondary");
        }
        if ((100.0d - growth) < 0.0d) {
            value.getStaking().setPercentColor("primary");
        }
    }

    private Double getRewardGrowth(String eventId) {
        Query query = entityManager.createNativeQuery("WITH cte AS (SELECT rewarded, ABS(EXTRACT(EPOCH FROM (SELECT createdat - (SELECT MAX(createdat) - INTERVAL '1 day' FROM singleeventhistoryentity)))) AS secs_from_prev_timestamp FROM singleeventhistoryentity WHERE eventid like :eventId) SELECT rewarded / (SELECT rewarded FROM singleeventhistoryentity WHERE eventid like :eventId AND createdat = (SELECT MAX(createdat) FROM singleeventhistoryentity)) * 100.0 AS percentage_difference FROM cte WHERE secs_from_prev_timestamp = (SELECT MIN(secs_from_prev_timestamp) FROM cte)");
        query.setParameter("eventId", eventId);
        if (!query.getResultList().isEmpty()) {
            return (Double) query.getResultList().get(0);
        }
        return 100.0d;
    }

    private Double getStakedGrowth(String eventId) {
        Query query = entityManager.createNativeQuery("WITH cte AS (SELECT staked, ABS(EXTRACT(EPOCH FROM (SELECT createdat - (SELECT MAX(createdat) - INTERVAL '1 day' FROM singleeventhistoryentity)))) AS secs_from_prev_timestamp FROM singleeventhistoryentity WHERE eventid like :eventId) SELECT staked / (SELECT staked FROM singleeventhistoryentity WHERE eventid like :eventId AND createdat = (SELECT MAX(createdat) FROM singleeventhistoryentity)) * 100.0 AS percentage_difference FROM cte WHERE secs_from_prev_timestamp = (SELECT MIN(secs_from_prev_timestamp) FROM cte)");
        query.setParameter("eventId", eventId);
        if (!query.getResultList().isEmpty()) {
            return (Double) query.getResultList().get(0);
        }
        return 100.0d;
    }

    /**
     * Get all event IDs.
     */
    @Transactional
    @Scheduled(every = "1h")
    public void getAllEventIds() {
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
    @Scheduled(every = "1m", delayed = "10s")
    public void getAllEventsData() {
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

            if (value.getPayload() == null) {
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

            if (singleEventRootResponse.getData().getPayload().getQuestions() != null) {
                LOG.info("question/answer NULL. Setting new one");
                if (value.getPayload().getQuestions() == null) {
                    value.getPayload().setQuestions(new HashSet<>());
                }
                for (SingleEventQuestionResponseDO singleEventQuestionResponseDO : singleEventRootResponse.getData().getPayload().getQuestions()) {
                    if (!value.getPayload().getQuestions().isEmpty()) {
                        for (SingleEventQuestionsEntity questionValue : value.getPayload().getQuestions()) {
                            if (!questionValue.getText().equals(singleEventQuestionResponseDO.getText())) {
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
    private void getAllEventsStatus(List<SingleEventDataEntity> singleEventDataEntityList) {
        LOG.info("Trying to enrich all events with new status data");
        for (SingleEventDataEntity value : singleEventDataEntityList) {
            SingleEventRootStatusResponseDO singleEventRootStatusResponseDO = participationPluginService.getParticipationEventStatus(value.getEventId());
            value.setMilestoneIndex(singleEventRootStatusResponseDO.getData().getMilestoneIndex());
            value.setStatus(singleEventRootStatusResponseDO.getData().getStatus());
            value.setChecksum(singleEventRootStatusResponseDO.getData().getChecksum());
            calcDuration(value);
            addStakingInformation(value, singleEventRootStatusResponseDO);
            addAnswerInformation(value, singleEventRootStatusResponseDO);
            setIcon(value);
            setAdvancedName(value);
            createHistoricalEntry(value);
            set24hRewards(value);
            set24hStaking(value);
        }
    }

    private void set24hRewards(SingleEventDataEntity value) {
        if (value.getStaking() != null) {
            Double growth = getRewardGrowth(value.getEventId());
            value.getStaking().setRewarded24hInPercent(String.format("%.2f%%", 100.0d - growth));
            setColor(value, growth);
        }
    }

    private void set24hStaking(SingleEventDataEntity value) {
        if (value.getStaking() != null) {
            Double growth = getStakedGrowth(value.getEventId());
            value.getStaking().setStaked24hInPercent(String.format("%.2f%%", 100.0d - growth));
            setColor(value, growth);
        }
    }

    private void addAnswerInformation(SingleEventDataEntity value, SingleEventRootStatusResponseDO singleEventRootStatusResponseDO) {
        if (singleEventRootStatusResponseDO.getData().getQuestions() != null) {
            LOG.info("Enrich all answers with new status data");
            Set<SingleEventQuestionStatusResponseDO> singleEventQuestionStatusResponseDOSet = singleEventRootStatusResponseDO.getData().getQuestions();
            Set<SingleEventAnswerStatusResponseDO> singleEventAnswerStatusResponseDOsSet = new HashSet<>();
            for (SingleEventQuestionStatusResponseDO singleEventQuestionStatusResponseDO : singleEventQuestionStatusResponseDOSet) {
                singleEventAnswerStatusResponseDOsSet.addAll(singleEventQuestionStatusResponseDO.getAnswers());
            }

            for (SingleEventAnswerStatusResponseDO singleEventAnswerStatusResponseDO : singleEventAnswerStatusResponseDOsSet) {
                for (SingleEventQuestionsEntity singleEventQuestionsEntity : value.getPayload().getQuestions()) {
                    for (SingleEventAnswersEntity singleEventAnswersEntity : singleEventQuestionsEntity.getAnswers()) {
                        if (singleEventAnswersEntity.getValue().equals(singleEventAnswerStatusResponseDO.getValue())) {
                            singleEventAnswersEntity.setCurrent(singleEventAnswerStatusResponseDO.getCurrent());
                            singleEventAnswersEntity.setAccumulated(singleEventAnswerStatusResponseDO.getAccumulated());
                        }
                    }
                }
            }
        }
    }

    private void addStakingInformation(SingleEventDataEntity value, SingleEventRootStatusResponseDO singleEventRootStatusResponseDO) {
        if (singleEventRootStatusResponseDO.getData().getStaking() != null) {
            if (value.getStaking() == null) {
                LOG.info("Staking NULL. Setting new one");
                value.setStaking(new SingleEventStakingEntity());
            }
            LOG.info("Adding staking data");
            value.getStaking().setStaked(singleEventRootStatusResponseDO.getData().getStaking().getStaked());
            value.getStaking().setRewarded(singleEventRootStatusResponseDO.getData().getStaking().getRewarded());
            value.getStaking().setSymbol(singleEventRootStatusResponseDO.getData().getStaking().getSymbol());
            unitConverter.convertToHigherUnit(value.getStaking());
            unitConverter.convertFromIotaToUnits(value.getStaking());

        }
    }

}