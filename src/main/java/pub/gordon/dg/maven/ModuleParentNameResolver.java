package pub.gordon.dg.maven;

import org.gitlab.api.models.GitlabProject;

/**
 * @author Gordon
 * @date 2017-11-19 00:16
 */
public interface ModuleParentNameResolver {


    /**
     * Try to find out {@code moduleName}'s parent as a Gitlab project
     *
     * @param moduleName
     * @return
     */
    GitlabProject findParentProject(String moduleName);

}
