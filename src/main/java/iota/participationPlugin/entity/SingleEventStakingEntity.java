package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;


@Entity
public class SingleEventStakingEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double staked;

    private Double rewarded;

    private String symbol;

    private String formattedReward;

    private String formattedStaked;

    private String Rewarded24hInPercent;
    private String Staked24hInPercent;
    private String PercentColor;

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

    public Double getStaked() {
        return staked;
    }

    public void setStaked(Double staked) {
        this.staked = staked;
    }

    public Double getRewarded() {
        return rewarded;
    }

    public void setRewarded(Double rewarded) {
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

    public String getFormattedReward() {
        return formattedReward;
    }

    public void setFormattedReward(String formattedReward) {
        this.formattedReward = formattedReward;
    }

    public String getFormattedStaked() {
        return formattedStaked;
    }

    public void setFormattedStaked(String formattedStaked) {
        this.formattedStaked = formattedStaked;
    }

    public String getRewarded24hInPercent() {
        return Rewarded24hInPercent;
    }

    public void setRewarded24hInPercent(String rewarded24hInPercent) {
        Rewarded24hInPercent = rewarded24hInPercent;
    }

    public String getPercentColor() {
        return PercentColor;
    }

    public void setPercentColor(String percentColor) {
        PercentColor = percentColor;
    }

    public String getStaked24hInPercent() {
        return Staked24hInPercent;
    }

    public void setStaked24hInPercent(String staked24hInPercent) {
        Staked24hInPercent = staked24hInPercent;
    }
}
