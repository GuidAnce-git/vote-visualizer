package iota.participationPlugin.control;

import io.quarkus.scheduler.Scheduled;
import iota.participationPlugin.boundary.NodeService;
import iota.participationPlugin.entity.NodeEntity;
import iota.participationPlugin.entity.response.node.NodeDO;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class NodeBA {
    @Inject
    Logger LOG;

    @Inject
    @RestClient
    NodeService nodeService;


    /**
     * Get all node info.
     */
    @Transactional
    @Scheduled(every = "1m", delayed = "1s")
    public void getNodeInfo() {
        LOG.info("Trying to get node info");
        NodeDO node = nodeService.getNodeInfo();

        NodeEntity nodeEntity = NodeEntity.findById(1L);
        if (NodeEntity.findById(1L) == null) {
            nodeEntity = new NodeEntity();
        }
        nodeEntity.setName(node.getData().getName());
        nodeEntity.setVersion(node.getData().getVersion());
        nodeEntity.setHealthy(node.getData().isHealthy());
        nodeEntity.setNetworkId(node.getData().getNetworkId());
        nodeEntity.setBech32HRP(node.getData().getBech32HRP());
        nodeEntity.setMinPoWScore(node.getData().getMinPoWScore());
        nodeEntity.setMessagesPerSecond(node.getData().getMessagesPerSecond());
        nodeEntity.setReferencedMessagesPerSecond(node.getData().getReferencedMessagesPerSecond());
        nodeEntity.setReferencedRate(node.getData().getReferencedRate());
        nodeEntity.setLatestMilestoneTimestamp(node.getData().getLatestMilestoneTimestamp());
        nodeEntity.setLatestMilestoneIndex(node.getData().getLatestMilestoneIndex());
        nodeEntity.setConfirmedMilestoneIndex(node.getData().getConfirmedMilestoneIndex());
        nodeEntity.setPruningIndex(node.getData().getPruningIndex());
        nodeEntity.persist();
        LOG.info("New node info added");


    }
}
