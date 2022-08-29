package iota.participationPlugin.entity.response.node;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

public class NodeDataDO implements Serializable {

    private String name;
    private String version;

    @JsonbProperty("isHealthy")
    private boolean healthy;

    private String networkId;
    private String bech32HRP;
    private Long minPoWScore;
    private Float messagesPerSecond;
    private Float referencedMessagesPerSecond;
    private Float referencedRate;
    private Long latestMilestoneTimestamp;
    private Long latestMilestoneIndex;
    private Long confirmedMilestoneIndex;
    private Long pruningIndex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getBech32HRP() {
        return bech32HRP;
    }

    public void setBech32HRP(String bech32HRP) {
        this.bech32HRP = bech32HRP;
    }

    public Long getMinPoWScore() {
        return minPoWScore;
    }

    public void setMinPoWScore(Long minPoWScore) {
        this.minPoWScore = minPoWScore;
    }

    public Float getMessagesPerSecond() {
        return messagesPerSecond;
    }

    public void setMessagesPerSecond(Float messagesPerSecond) {
        this.messagesPerSecond = messagesPerSecond;
    }

    public Float getReferencedMessagesPerSecond() {
        return referencedMessagesPerSecond;
    }

    public void setReferencedMessagesPerSecond(Float referencedMessagesPerSecond) {
        this.referencedMessagesPerSecond = referencedMessagesPerSecond;
    }

    public Float getReferencedRate() {
        return referencedRate;
    }

    public void setReferencedRate(Float referencedRate) {
        this.referencedRate = referencedRate;
    }

    public Long getLatestMilestoneTimestamp() {
        return latestMilestoneTimestamp;
    }

    public void setLatestMilestoneTimestamp(Long latestMilestoneTimestamp) {
        this.latestMilestoneTimestamp = latestMilestoneTimestamp;
    }

    public Long getLatestMilestoneIndex() {
        return latestMilestoneIndex;
    }

    public void setLatestMilestoneIndex(Long latestMilestoneIndex) {
        this.latestMilestoneIndex = latestMilestoneIndex;
    }

    public Long getConfirmedMilestoneIndex() {
        return confirmedMilestoneIndex;
    }

    public void setConfirmedMilestoneIndex(Long confirmedMilestoneIndex) {
        this.confirmedMilestoneIndex = confirmedMilestoneIndex;
    }

    public Long getPruningIndex() {
        return pruningIndex;
    }

    public void setPruningIndex(Long pruningIndex) {
        this.pruningIndex = pruningIndex;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
