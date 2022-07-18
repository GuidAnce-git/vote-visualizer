package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;


@Entity
@NamedQueries({
        @NamedQuery(name = "SingleEventDataEntity.getByValue", query = "SELECT DISTINCT o FROM SingleEventDataEntity o " +
                "JOIN o.payload s1 " +
                "JOIN s1.questions s2 " +
                "JOIN s2.answers s3 " +
                "WHERE o.eventId = :eventId AND s3.value = :value")
})
public class SingleEventAnswersEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    private Long value;

    @Column(length = 5120)
    private String text;

    @Column(length = 5120)
    private String additionalInfo;

    private Long current;
    private Long accumulated;


    public static SingleEventAnswersEntity findByValue(Long value){
        SingleEventDataEntity test = new SingleEventDataEntity();
        test = find("#SingleEventDataEntity.getByValue", Parameters.with("eventId", "9e8e1a15c831441797912a86022f5a78fcb70e151e43fe84812d4c7f6eb79a7b").and("value", value)).firstResult();

        return null;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(Long accumulated) {
        this.accumulated = accumulated;
    }
}
