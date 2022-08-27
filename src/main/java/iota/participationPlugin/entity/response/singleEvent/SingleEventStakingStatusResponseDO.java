package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;

public class SingleEventStakingStatusResponseDO implements Serializable {

    private Long staked;
    private Long rewarded;
    private String symbol;

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
}
