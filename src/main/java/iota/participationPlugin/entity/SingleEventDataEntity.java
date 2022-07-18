package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@NamedQueries(
        @NamedQuery(name="SingleEventDataEntity.findEntitiesByType", query = "SELECT s FROM SingleEventDataEntity s " +
                "JOIN s.payload s1 WHERE s1.type = :type ORDER BY s.name")
)
public class SingleEventDataEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String eventId;
    private String status;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Column(length = 1024)
    private String name;
    private Long milestoneIndexCommence;
    private Long milestoneIndexStart;
    private Long milestoneIndexEnd;
    private Long milestoneIndex;
    @Column(length = 5120)
    private String additionalInfo;
    private String checksum;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private SingleEventPayloadEntity payload;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private SingleEventStakingEntity staking;

    public static List<SingleEventDataEntity> findEntitiesByType(long type) {
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
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
}
