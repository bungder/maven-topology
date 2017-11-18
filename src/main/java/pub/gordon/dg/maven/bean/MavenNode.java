package pub.gordon.dg.maven.bean;


/**
 * @author Gordon
 * @date 2017-11-03 11:26
 */
public class MavenNode {

    private String groupId;
    private String artifactId;
    private String version;

    public MavenNode(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MavenNode{");
        sb.append("groupId='").append(groupId).append('\'');
        sb.append(", artifactId='").append(artifactId).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
