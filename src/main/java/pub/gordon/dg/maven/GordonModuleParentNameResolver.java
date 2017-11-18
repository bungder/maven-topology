package pub.gordon.dg.maven;

import org.gitlab.api.models.GitlabProject;
import pub.gordon.dg.gitlab.RepoInfoCache;

/**
 * @author Gordon
 * @date 2017-11-19 00:11
 */
public class GordonModuleParentNameResolver implements ModuleParentNameResolver {

    @Override
    public GitlabProject findParentProject(String moduleName) {
        if (moduleName.startsWith("gordon-service")) {
            return RepoInfoCache.getProjectByName(moduleName.replace("gordon-service", "gordon"));
        }
        if (moduleName.startsWith("gordon-api")) {
            return RepoInfoCache.getProjectByName(moduleName.replace("gordon-api", "gordon"));
        }
        return null;

    }
}
