package iota.participationPlugin.entity.response.singleEvent;

import java.io.Serializable;
import java.util.Set;

public class SingleEventStakingStatusResponseDO implements Serializable {

    private Double staked;
    private Double rewarded;
    private String symbol;

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
}
