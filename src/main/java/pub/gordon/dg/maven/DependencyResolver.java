package pub.gordon.dg.maven;

import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.TopologicalSorting;
import pub.gordon.dg.bean.Dependency;
import pub.gordon.dg.exception.POMNotFoundException;
import pub.gordon.dg.maven.bean.MavenNode;
import pub.gordon.dg.suite.DGSuit;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gordon
 * @date 2017-11-03 13:03
 */
public class DependencyResolver {

    private static final Logger logger = LoggerFactory.getLogger(DependencyResolver.class);

    public static List<MavenNode> sortEffected(List<String> projectNames, DGSuit<MavenNode> suit)
            throws IOException, XmlPullParserException, POMNotFoundException, GitAPIException, InterruptedException {
        logger.info("Start resolving...");
        POMResolver resolver = new POMResolver(new ConfiguredProjectPomPathResolver());
        if (CollectionUtils.isEmpty(projectNames)) {
            return Collections.EMPTY_LIST;
        }
        Set<Dependency<MavenNode>> relationSet = new HashSet<>(100);
        for (String projectName : projectNames) {
            logger.info("Resolving {}", projectName);
            resolver.resolveProject(projectName, suit);
        }
        if (!CollectionUtils.isEmpty(resolver.getRelations())) {
            relationSet.addAll(resolver.getRelations());
        }
        List<MavenNode> result = TopologicalSorting.sort(relationSet);
        return result;
    }

}
