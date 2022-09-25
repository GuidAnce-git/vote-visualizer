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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * The type Participation plugin ba.
 */
@ApplicationScoped
public class ParticipationPluginBA {

    private static final int TIME_BETWEEN_MILESTONES = 10;
    private static final long IOTA_TOTAL_TOKEN = 2779530283277761L;

    @Inject
    UnitConverter unitConverter;

    @Inject
    @RestClient
    ParticipationPluginService participationPluginService;

    @Inject
    QuestionMapper questionMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    Logger LOG;

    private void createHistoricalEntry(SingleEventDataEntity value) {
        if (!StatusEnum.ENDED.getName().equals(value.getStatus()) && value.getStaking() != null) {
            SingleEventHistoryEntity singleEventHistoryEntity = new SingleEventHistoryEntity();
            singleEventHistoryEntity.setEventId(value.getEventId());
            singleEventHistoryEntity.setStaked(value.getStaking().getStaked());
            singleEventHistoryEntity.setRewarded(value.getStaking().getRewarded());
            singleEventHistoryEntity.persist();
        }

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
                singleEventDataEntity.setActive(false);
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

            LOG.info("setting root data information for EventID " + value.getEventId());
            value.setName(singleEventRootResponse.getData().getName());
            value.setMilestoneIndexCommence(singleEventRootResponse.getData().getMilestoneIndexCommence());
            value.setMilestoneIndexStart(singleEventRootResponse.getData().getMilestoneIndexStart());
            value.setMilestoneIndexStartDate(unitConverter.convertMilestoneToDate(value.getMilestoneIndexStart()));
            value.setMilestoneIndexEnd(singleEventRootResponse.getData().getMilestoneIndexEnd());
            value.setMilestoneIndexEndDate(unitConverter.convertMilestoneToDate(value.getMilestoneIndexEnd()));
            value.setAdditionalInfo(singleEventRootResponse.getData().getAdditionalInfo());

            if (value.getPayload() == null) {
                value.setPayload(new SingleEventPayloadEntity());
            }
            LOG.info("Adding payload data for EventID " + value.getEventId());
            value.getPayload().setType(singleEventRootResponse.getData().getPayload().getType());
            value.getPayload().setText(singleEventRootResponse.getData().getPayload().getText());
            value.getPayload().setSymbol(singleEventRootResponse.getData().getPayload().getSymbol());
            value.getPayload().setNumerator(singleEventRootResponse.getData().getPayload().getNumerator());
            value.getPayload().setDenominator(singleEventRootResponse.getData().getPayload().getDenominator());
            value.getPayload().setRequiredMinimumRewards(singleEventRootResponse.getData().getPayload().getRequiredMinimumRewards());
            value.getPayload().setAdditionalInfo(singleEventRootResponse.getData().getPayload().getAdditionalInfo());

            if (singleEventRootResponse.getData().getPayload().getQuestions() != null) {
                if (value.getPayload().getQuestions() == null) {
                    value.getPayload().setQuestions(new HashSet<>());
                }
                LOG.info("Adding Questions data for EventID " + value.getEventId());
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
            LOG.info("Enrich Event " + value.getEventId());
            SingleEventRootStatusResponseDO singleEventRootStatusResponseDO = participationPluginService.getParticipationEventStatus(value.getEventId());
            value.setMilestoneIndex(singleEventRootStatusResponseDO.getData().getMilestoneIndex());
            value.setStatus(singleEventRootStatusResponseDO.getData().getStatus());
            value.setChecksum(singleEventRootStatusResponseDO.getData().getChecksum());
            calcDuration(value);
            addStakingInformation(value, singleEventRootStatusResponseDO);
            addAnswerInformation(value, singleEventRootStatusResponseDO);
            setIcon(value);
            setAdvancedName(value);

            // TODO - historical entries disabled until next staking round
            // createHistoricalEntry(value);

            set24hRewards(value);
            set24hStaking(value);
            setEventTimeframeInMonths(value);
            setEventTimeframeInWeeks(value);
            setRewardGrowthForLastMonths(value);
            setStakingGrowthForLastMonths(value);
            setRewardGrowthForLastWeeks(value);
            setStakingGrowthForLastWeeks(value);

            // calcVotingResultsInPercent


            if (value.getPayload().getQuestions() != null && !value.getPayload().getQuestions().isEmpty()) {
                for (SingleEventQuestionsEntity questionValue : value.getPayload().getQuestions()) {
                    List<String> listOfAnswers = new ArrayList<>();
                    List<Long> listOfVotes = new ArrayList<>();
                    for (SingleEventAnswersEntity answerValue : questionValue.getAnswers()) {
                        listOfVotes.add(answerValue.getAccumulated());
                    }
                    long sum = listOfVotes.stream().mapToLong(Long::longValue).sum();

                    listOfVotes.clear();
                    for (SingleEventAnswersEntity answerValue : questionValue.getAnswers()) {
                        listOfAnswers.add(answerValue.getText());
                        listOfVotes.add((long) ((double) answerValue.getAccumulated() / sum * 100));
                    }

                    questionValue.setListOfAnswers(listOfAnswers);
                    //listOfVotes.replaceAll(aLong -> ((Double) ((double) aLong / sum * 100)).longValue());
                    questionValue.setVotesInPercent(listOfVotes);
                }
            }

            Set<SingleEventQuestionsEntity> foo = value.getPayload().getQuestions();
        }
    }

    private void calcDuration(SingleEventDataEntity value) {
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

    private void addStakingInformation(SingleEventDataEntity value, SingleEventRootStatusResponseDO singleEventRootStatusResponseDO) {
        if (singleEventRootStatusResponseDO.getData().getStaking() != null) {
            if (value.getStaking() == null) {
                LOG.info("Staking NULL. Setting new one");
                value.setStaking(new SingleEventStakingEntity());
            }
            LOG.info("Adding staking data for Event " + value.getEventId());
            value.getStaking().setStaked(singleEventRootStatusResponseDO.getData().getStaking().getStaked());
            value.getStaking().setRewarded(singleEventRootStatusResponseDO.getData().getStaking().getRewarded());
            value.getStaking().setSymbol(singleEventRootStatusResponseDO.getData().getStaking().getSymbol());

            value.getStaking().setFormattedReward(unitConverter.convertToHigherUnit(value.getStaking()));
            value.getStaking().setFormattedStaked(unitConverter.convertIotaToHigherUnitsWithUnit(value.getStaking()));
            value.getStaking().setFormattedStaked(value.getStaking().getFormattedStaked() + " (" + String.format("%.0f", value.getStaking().getStaked() * 100f / IOTA_TOTAL_TOKEN) + "%)");
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

    private void setIcon(SingleEventDataEntity value) {
        if (value.getStaking() != null && IotaSymbolsEnum.MICRO_ASMB.getName().equals(value.getStaking().getSymbol())) {
            value.setIcon(IotaSymbolsEnum.MICRO_ASMB.getLogo());
        }
        if (value.getStaking() != null && IotaSymbolsEnum.SMR.getName().equals(value.getStaking().getSymbol())) {
            value.setIcon(IotaSymbolsEnum.SMR.getLogo());
        }
    }

    private void setAdvancedName(SingleEventDataEntity value) {
        if (value.getPayload() != null && value.getPayload().getText() != null) {
            value.setAdvancedName(value.getPayload().getText());
        } else {
            value.setAdvancedName(value.getName());
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

    private void setEventTimeframeInMonths(SingleEventDataEntity value) {
        List<String> lastMonths = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(value.getMilestoneIndexStartDate(), formatter).withDayOfMonth(1);
        LocalDate endDate = LocalDate.parse(value.getMilestoneIndexEndDate(), formatter).withDayOfMonth(1);
        Period diff = Period.between(startDate, endDate);

        for (int i = 0; i < diff.getMonths() + 1; i++) {
            lastMonths.add(startDate.plusMonths(i).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }

        if (value.getStaking() != null) {
            value.getStaking().setEventTimeframeInMonths(String.join(",", lastMonths));
        }
    }

    private void setEventTimeframeInWeeks(SingleEventDataEntity value) {
        List<Integer> lastWeeks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(value.getMilestoneIndexStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(value.getMilestoneIndexEndDate(), formatter);
        long weeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);

        for (int i = 0; i < weeksBetween + 1; i++) {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Calendar cal = Calendar.getInstance();
            cal.setTime(Date.from(startDate.plusWeeks(i).atStartOfDay(defaultZoneId).toInstant()));
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            lastWeeks.add(week);
        }

        if (value.getStaking() != null) {
            value.getStaking().setEventTimeframeInWeeks(lastWeeks.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    private void setRewardGrowthForLastMonths(SingleEventDataEntity singleEventDataEntity) {
        List<Long> resultList = new ArrayList<>();
        List<String> notAvailableMonths = new ArrayList<>();
        if (!singleEventDataEntity.getStatus().equals(StatusEnum.ENDED.getName())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate startDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexStartDate(), formatter).withDayOfMonth(1);
            LocalDate endDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexEndDate(), formatter).withDayOfMonth(1);
            Period diff = Period.between(startDate, endDate);

            for (int i = 0; i < diff.getMonths() + 1; i++) {
                List<Long> rewardsPerDay = SingleEventHistoryEntity.findEntriesByDate(EventTypeEnum.REWARD.getName(), singleEventDataEntity.getEventId(), startDate.plusMonths(i).withDayOfMonth(1).atStartOfDay(), startDate.plusMonths(i).withDayOfMonth(startDate.plusMonths(i).lengthOfMonth()).atStartOfDay()).stream().map(SingleEventHistoryEntity::getRewarded).toList();
                if (!rewardsPerDay.isEmpty()) {
                    resultList.add(unitConverter.getConvertedASMB(singleEventDataEntity.getStaking(), rewardsPerDay.get(rewardsPerDay.size() - 1) - rewardsPerDay.get(0)));
                } else {
                    //no data available
                    resultList.add(0L);
                    notAvailableMonths.add(startDate.plusMonths(i).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + "/" + startDate.plusMonths(i).getYear());
                }
            }

            singleEventDataEntity.getStaking().setRewardsLastMonths(resultList);
            if (notAvailableMonths.isEmpty()) {
                singleEventDataEntity.getStaking().setMonthWithoutRewards("-");
            } else {
                singleEventDataEntity.getStaking().setMonthWithoutRewards(notAvailableMonths.toString());
            }
        }
    }

    private void setStakingGrowthForLastMonths(SingleEventDataEntity singleEventDataEntity) {
        List<Long> resultList = new ArrayList<>();
        List<String> notAvailableMonths = new ArrayList<>();
        if (!singleEventDataEntity.getStatus().equals(StatusEnum.ENDED.getName())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate startDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexStartDate(), formatter).withDayOfMonth(1);
            LocalDate endDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexEndDate(), formatter).withDayOfMonth(1);
            Period diff = Period.between(startDate, endDate);

            for (int i = 0; i < diff.getMonths() + 1; i++) {
                List<Long> stakesPerDay = SingleEventHistoryEntity.findEntriesByDate(EventTypeEnum.STAKE.getName(), singleEventDataEntity.getEventId(), startDate.plusMonths(i).withDayOfMonth(1).atStartOfDay(), startDate.plusMonths(i).withDayOfMonth(startDate.plusMonths(i).lengthOfMonth()).atStartOfDay()).stream().map(SingleEventHistoryEntity::getStaked).toList();
                if (!stakesPerDay.isEmpty()) {
                    resultList.add(unitConverter.convertIotaToMiota(stakesPerDay.get(stakesPerDay.size() - 1) - stakesPerDay.get(0)));
                } else {
                    //no data available
                    resultList.add(0L);
                    notAvailableMonths.add(startDate.plusMonths(i).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + "/" + startDate.plusMonths(i).getYear());
                }
            }

            singleEventDataEntity.getStaking().setStakesLastMonths(resultList);
            if (notAvailableMonths.isEmpty()) {
                singleEventDataEntity.getStaking().setMonthWithoutStaking("-");
            } else {
                singleEventDataEntity.getStaking().setMonthWithoutStaking(notAvailableMonths.toString());
            }
        }
    }

    private void setRewardGrowthForLastWeeks(SingleEventDataEntity singleEventDataEntity) {
        List<Long> resultList = new ArrayList<>();
        if (!singleEventDataEntity.getStatus().equals(StatusEnum.ENDED.getName())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate startDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexEndDate(), formatter);
            long weeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);
            Calendar cal = Calendar.getInstance();
            ZoneId defaultZoneId = ZoneId.systemDefault();

            for (int i = 0; i < weeksBetween + 1; i++) {
                Date date = Date.from(startDate.plusWeeks(i).atStartOfDay(defaultZoneId).toInstant());
                cal.setTime(date);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                LocalDateTime weekStartDate = cal.getTime().toInstant().atZone(defaultZoneId).toLocalDateTime();
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                LocalDateTime weekEndDate = cal.getTime().toInstant().atZone(defaultZoneId).toLocalDate().atTime(LocalTime.MAX).truncatedTo(ChronoUnit.MINUTES);

                List<Long> rewardsPerDay = Objects.requireNonNull(SingleEventHistoryEntity.findEntriesByDate(
                        EventTypeEnum.REWARD.getName(),
                        singleEventDataEntity.getEventId(),
                        weekStartDate,
                        weekEndDate
                )).stream().map(SingleEventHistoryEntity::getRewarded).toList();

                if (!rewardsPerDay.isEmpty()) {
                    resultList.add(unitConverter.getConvertedASMB(singleEventDataEntity.getStaking(), rewardsPerDay.get(rewardsPerDay.size() - 1) - rewardsPerDay.get(0)));
                } else {
                    //no data available
                    resultList.add(0L);
                }
            }
            singleEventDataEntity.getStaking().setRewardsLastWeeks(resultList);
        }
    }

    private void setStakingGrowthForLastWeeks(SingleEventDataEntity singleEventDataEntity) {
        List<Long> resultList = new ArrayList<>();
        if (!singleEventDataEntity.getStatus().equals(StatusEnum.ENDED.getName())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate startDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(singleEventDataEntity.getMilestoneIndexEndDate(), formatter);
            long weeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);

            Calendar cal = Calendar.getInstance();
            ZoneId defaultZoneId = ZoneId.systemDefault();

            for (int i = 0; i < weeksBetween + 1; i++) {
                Date date = Date.from(startDate.plusWeeks(i).atStartOfDay(defaultZoneId).toInstant());
                cal.setTime(date);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                LocalDateTime weekStartDate = cal.getTime().toInstant().atZone(defaultZoneId).toLocalDateTime();
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                LocalDateTime weekEndDate = cal.getTime().toInstant().atZone(defaultZoneId).toLocalDate().atTime(LocalTime.MAX).truncatedTo(ChronoUnit.MINUTES);

                List<Long> stakesPerDay = Objects.requireNonNull(SingleEventHistoryEntity.findEntriesByDate(
                        EventTypeEnum.STAKE.getName(),
                        singleEventDataEntity.getEventId(),
                        weekStartDate,
                        weekEndDate
                )).stream().map(SingleEventHistoryEntity::getStaked).toList();

                if (!stakesPerDay.isEmpty()) {
                    resultList.add(unitConverter.convertIotaToMiota(stakesPerDay.get(stakesPerDay.size() - 1) - stakesPerDay.get(0)));
                } else {
                    //no data available
                    resultList.add(0L);
                }
            }
            singleEventDataEntity.getStaking().setStakesLastWeeks(resultList);
        }
    }

    private Double getRewardGrowth(String eventId) {
        Instant fromDate = Instant.now().minus(Duration.ofHours(24));
        Query queryFirstValue = entityManager.createNativeQuery("SELECT rewarded FROM singleeventhistoryentity WHERE eventid like :eventId AND createdat >= :fromDate ORDER BY createdat LIMIT 1");
        queryFirstValue.setParameter("eventId", eventId).setParameter("fromDate", fromDate);
        Query queryLastValue = entityManager.createNativeQuery("SELECT rewarded FROM singleeventhistoryentity WHERE eventid like :eventId AND createdat >= :fromDate ORDER BY createdat DESC LIMIT 1");
        return getPercentValue(eventId, fromDate, queryFirstValue, queryLastValue);
    }

    private void setColor(SingleEventDataEntity value, Double growth) {
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

    private Double getStakedGrowth(String eventId) {
        Instant fromDate = Instant.now().minus(Duration.ofHours(24));
        Query queryFirstValue = entityManager.createNativeQuery("SELECT staked FROM singleeventhistoryentity WHERE eventid like :eventId AND createdat >= :fromDate ORDER BY createdat LIMIT 1");
        queryFirstValue.setParameter("eventId", eventId).setParameter("fromDate", fromDate);
        Query queryLastValue = entityManager.createNativeQuery("SELECT staked FROM singleeventhistoryentity WHERE eventid like :eventId AND createdat >= :fromDate ORDER BY createdat DESC LIMIT 1");
        return getPercentValue(eventId, fromDate, queryFirstValue, queryLastValue);
    }

    private Double getPercentValue(String eventId, Instant fromDate, Query queryFirstValue, Query queryLastValue) {
        queryLastValue.setParameter("eventId", eventId).setParameter("fromDate", fromDate);

        if (!queryLastValue.getResultList().isEmpty() && !queryLastValue.getResultList().isEmpty()) {
            Double lastValue = Double.valueOf(queryLastValue.getResultList().get(0).toString());
            Double firstValue = Double.valueOf(queryFirstValue.getResultList().get(0).toString());
            return ((firstValue / lastValue)) * 100;
        }
        return 100.0d;
    }


}
