package pub.gordon.dg.maven;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pub.gordon.dg.maven.bean.MavenNode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gordon
 * @date 2017-11-03 12:41
 */
public class POMProducer {

    protected static class POMKey implements Serializable {
        private static final long serialVersionUID = -74634971205734318L;
        private String groupId;
        private String artifactId;

        public POMKey(String groupId, String artifactId) {
            this.groupId = groupId;
            this.artifactId = artifactId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof POMKey)) return false;

            POMKey pomKey = (POMKey) o;

            return new EqualsBuilder()
                    .append(groupId, pomKey.groupId)
                    .append(artifactId, pomKey.artifactId)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(groupId)
                    .append(artifactId)
                    .toHashCode();
        }
    }

    private Map<POMKey, MavenNode> nodeMap = new HashMap<POMKey, MavenNode>(100);

    public MavenNode getMavenNode(String groupId, String artifactId, String version) {
        POMKey key = new POMKey(groupId, artifactId);
        if (nodeMap.containsKey(key)) {
            return nodeMap.get(key);
        }
        MavenNode node = new MavenNode(groupId, artifactId, version);
        nodeMap.put(key, node);
        return node;
    }

}
