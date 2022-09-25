package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@NamedQueries(
        @NamedQuery(name = "SingleEventDataEntity.findEntitiesByType", query = "SELECT s FROM SingleEventDataEntity s " +
                "JOIN s.payload s1 WHERE s.active = true AND s1.type = :type ORDER BY s.name")
)
public class SingleEventDataEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventId;
    private String status;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 1024)
    private String name;
    private String advancedName;
    private Long milestoneIndexCommence;
    private Long milestoneIndexStart;
    private String milestoneIndexStartDate;
    private Long milestoneIndexEnd;
    private String milestoneIndexEndDate;
    private Long milestoneIndex;
    @Column(length = 5120)
    private String additionalInfo;
    private String checksum;
    private String EventEndsIn;
    private String icon;
    private boolean active;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private SingleEventPayloadEntity payload;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private SingleEventStakingEntity staking;

    public static List<SingleEventDataEntity> findEntitiesByTypeAndActive(long type) {
        return list("#SingleEventDataEntity.findEntitiesByType", Parameters.with("type", type));
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMilestoneIndexCommence() {
        return milestoneIndexCommence;
    }

    public void setMilestoneIndexCommence(Long milestoneIndexCommence) {
        this.milestoneIndexCommence = milestoneIndexCommence;
    }

    public Long getMilestoneIndexStart() {
        return milestoneIndexStart;
    }

    public void setMilestoneIndexStart(Long milestoneIndexStart) {
        this.milestoneIndexStart = milestoneIndexStart;
    }

    public Long getMilestoneIndexEnd() {
        return milestoneIndexEnd;
    }

    public void setMilestoneIndexEnd(Long milestoneIndexEnd) {
        this.milestoneIndexEnd = milestoneIndexEnd;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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

    public SingleEventPayloadEntity getPayload() {
        return payload;
    }

    public void setPayload(SingleEventPayloadEntity singleEventPayloadEntity) {
        this.payload = singleEventPayloadEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMilestoneIndex() {
        return milestoneIndex;
    }

    public void setMilestoneIndex(Long milestoneIndex) {
        this.milestoneIndex = milestoneIndex;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public SingleEventStakingEntity getStaking() {
        return staking;
    }

    public void setStaking(SingleEventStakingEntity staking) {
        this.staking = staking;
    }

    public String getEventEndsIn() {
        return EventEndsIn;
    }

    public void setEventEndsIn(String eventEndsIn) {
        EventEndsIn = eventEndsIn;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String logo) {
        this.icon = logo;
    }

    public String getAdvancedName() {
        return advancedName;
    }

    public void setAdvancedName(String advancedName) {
        this.advancedName = advancedName;
    }

    public String getMilestoneIndexStartDate() {
        return milestoneIndexStartDate;
    }

    public void setMilestoneIndexStartDate(String milestoneIndexStartDate) {
        this.milestoneIndexStartDate = milestoneIndexStartDate;
    }

    public String getMilestoneIndexEndDate() {
        return milestoneIndexEndDate;
    }

    public void setMilestoneIndexEndDate(String milestoneIndexEndDate) {
        this.milestoneIndexEndDate = milestoneIndexEndDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
