package pub.gordon.dg.maven;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jgit.api.errors.GitAPIException;
import pub.gordon.dg.bean.Dependency;
import pub.gordon.dg.exception.POMNotFoundException;
import pub.gordon.dg.maven.bean.MavenNode;
import pub.gordon.dg.suite.DGSuit;
import pub.gordon.dg.suite.filter.Filter;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author Gordon
 * @date 2017-11-03 12:30
 */
public class POMResolver {

    POMProducer producer = new POMProducer();
    List<Dependency<MavenNode>> relations = new LinkedList<>();
    ProjectPomLocalPathResolver pathResolver;

    public POMResolver() {
        pathResolver = new DefaultProjectLocalPathResolver();
    }

    public POMResolver(ProjectPomLocalPathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    public void resolveProject(String projectName, DGSuit<MavenNode> suit) throws IOException, XmlPullParserException,
            POMNotFoundException, GitAPIException, InterruptedException, URISyntaxException {
        List<String> pomList = pathResolver.resolve(projectName);
        if (CollectionUtils.isEmpty(pomList)) {
            throw new POMNotFoundException(projectName);
        }
        for (String pom : pomList) {
            resolveFile(pom, suit);
        }
    }

    public void resolveFile(String pomPath, DGSuit<MavenNode> suit) throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pomPath));
        Properties ps = model.getProperties();

        MavenNode node = producer.getMavenNode(model.getGroupId()==null?model.getParent().getGroupId():model.getGroupId(), model.getArtifactId(), determineVersion(ps, model.getVersion()));
        if (!accept(node, suit)) {
            return;
        }
        List<org.apache.maven.model.Dependency> dependencies = model.getDependencies();
        if (!CollectionUtils.isEmpty(dependencies)) {
            for (org.apache.maven.model.Dependency d : dependencies) {
                MavenNode dnode = producer.getMavenNode(d.getGroupId(), d.getArtifactId(), determineVersion(ps, d.getVersion()));
                if (accept(dnode, suit)) {
                    relations.add(new Dependency<>(node, dnode));
                }
            }
        }
    }

    private boolean accept(MavenNode node, DGSuit<MavenNode> suit) {
        if (CollectionUtils.isEmpty(suit.getFilters())) {
            return true;
        }
        for (Filter<MavenNode> f : suit.getFilters()) {
            if (!f.accept(node)) {
                return false;
            }
        }
        return true;
    }


    public List<Dependency<MavenNode>> getRelations() {
        return relations;
    }

    private String determineVersion(Properties ps, String version) {
        if (StringUtils.isBlank(version)) {
            return version;
        }
        if (version.startsWith("${") && version.endsWith("}")) {
            String t = version.substring(2, version.length() - 1);
            t = ps.getProperty(t);
            if (StringUtils.isBlank(t)) {
                return version;
            } else {
                return t;
            }
        }
        return version;
    }
}
