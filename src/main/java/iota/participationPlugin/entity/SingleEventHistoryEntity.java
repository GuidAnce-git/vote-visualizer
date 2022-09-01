package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import iota.participationPlugin.control.EventTypeEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name = "SingleEventHistoryEntity.findRewardedByDate", query = "SELECT s FROM SingleEventHistoryEntity s " +
                "WHERE s.eventId = :eventId AND s.createdAt BETWEEN :startDate AND :endDate " +
                "ORDER BY s.rewarded"),
        @NamedQuery(name = "SingleEventHistoryEntity.findStakedByDate", query = "SELECT s FROM SingleEventHistoryEntity s " +
                "WHERE s.eventId = :eventId AND s.createdAt BETWEEN :startDate AND :endDate " +
                "ORDER BY s.staked")
})
public class SingleEventHistoryEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;

    private Long staked;

    private Long rewarded;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public static List<SingleEventHistoryEntity> findEntriesByDate(String type, String eventId, LocalDateTime startDate, LocalDateTime endDate) {
        if (type.equals(EventTypeEnum.REWARD.getName())) {
            return find("#SingleEventHistoryEntity.findRewardedByDate",
                    Parameters.with("eventId", eventId).and("startDate", startDate).and("endDate", endDate)).list();
        }
        if (type.equals(EventTypeEnum.STAKE.getName())) {
            return find("#SingleEventHistoryEntity.findStakedByDate",
                    Parameters.with("eventId", eventId).and("startDate", startDate).and("endDate", endDate)).list();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getStaked() {
        return staked;
    }

    public void setStaked(Long staked) {
        this.staked = staked;
    }

    public Long getRewarded() {
        return rewarded;
    }

    public void setRewarded(Long rewarded) {
        this.rewarded = rewarded;
    }
}
