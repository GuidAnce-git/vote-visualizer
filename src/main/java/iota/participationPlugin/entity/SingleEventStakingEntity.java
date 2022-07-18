package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
public class SingleEventStakingEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staked;

    private String rewarded;

    private String symbol;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaked() {
        return staked;
    }

    public void setStaked(String staked) {
        this.staked = staked;
    }

    public String getRewarded() {
        return rewarded;
    }

    public void setRewarded(String rewarded) {
        this.rewarded = rewarded;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
}
