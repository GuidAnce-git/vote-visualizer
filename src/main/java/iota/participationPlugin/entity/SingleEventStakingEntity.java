package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class SingleEventStakingEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long staked;

    private Long rewarded;

    private String symbol;

    private String formattedReward;

    private String formattedStaked;

    private String Rewarded24hInPercent;
    private String Staked24hInPercent;
    private String PercentColor;
    private String eventTimeframe;

    @ElementCollection
    private List<Long> rewardsLastMonths;

    @ElementCollection
    private List<Long> stakesLastMonths;

    private String monthWithoutRewards;
    private String monthWithoutStaking;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public String getEventTimeframe() {
        return eventTimeframe;
    }

    public void setEventTimeframe(String last12Months) {
        this.eventTimeframe = last12Months;
    }

    public List<Long> getRewardsLastMonths() {
        return rewardsLastMonths;
    }

    public void setRewardsLastMonths(List<Long> rewardsLast12Months) {
        this.rewardsLastMonths = rewardsLast12Months;
    }

    public String getMonthWithoutRewards() {
        return monthWithoutRewards;
    }

    public void setMonthWithoutRewards(String monthWithoutRewards) {
        this.monthWithoutRewards = monthWithoutRewards;
    }

    public List<Long> getStakesLastMonths() {
        return stakesLastMonths;
    }

    public void setStakesLastMonths(List<Long> stakesLast12Months) {
        this.stakesLastMonths = stakesLast12Months;
    }

    public String getMonthWithoutStaking() {
        return monthWithoutStaking;
    }

    public void setMonthWithoutStaking(String monthWithoutStaking) {
        this.monthWithoutStaking = monthWithoutStaking;
    }
}
