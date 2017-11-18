package pub.gordon.dg.maven.suite.filter;

import org.apache.commons.lang.StringUtils;
import pub.gordon.dg.maven.bean.MavenNode;
import pub.gordon.dg.util.RegUtil;

/**
 * @author Gordon
 * @date 2017-11-18 23:26
 */
public class GAMavelFilter extends MavenFilter {

    private String groupIdPattern;
    private String artifactIdPattern;

    public GAMavelFilter(String groupIdPattern, String artifactIdPattern) {
        this.groupIdPattern = groupIdPattern;
        this.artifactIdPattern = artifactIdPattern;
    }

    @Override
    public boolean accept(MavenNode node) {
        if (!StringUtils.isBlank(groupIdPattern)
                && !RegUtil.match(node.getGroupId(), groupIdPattern)) return false;
        if (!StringUtils.isBlank(artifactIdPattern)
                && !RegUtil.match(node.getArtifactId(), artifactIdPattern)) return false;
        return true;
    }
}
