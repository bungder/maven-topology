package pub.gordon.dg.maven;

import org.eclipse.jgit.api.errors.GitAPIException;
import pub.gordon.dg.exception.POMNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * @author Gordon
 * @date 2017-11-16 23:13
 */
public abstract class ProjectPomLocalPathResolver {

    public abstract List<String> resolve(String projectName) throws POMNotFoundException, IOException, GitAPIException, InterruptedException;
}
