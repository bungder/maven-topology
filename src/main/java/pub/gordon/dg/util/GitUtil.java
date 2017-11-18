package pub.gordon.dg.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.bean.RepositoryConfig;

import java.io.File;
import java.io.IOException;

/**
 * @author Gordon
 * @date 2017-11-17 09:20
 */
public class GitUtil {
    private static final Logger logger = LoggerFactory.getLogger(GitUtil.class);

    private static RepositoryConfig config = RepositoryConfig.getInstance();

    public static void cloneRepo(String repoUrl, File path) throws GitAPIException, IOException {
        logger.info("Cloning {} to {}", repoUrl, path.getAbsoluteFile());
        Git.cloneRepository()
                .setCloneSubmodules(true)
                .setURI(repoUrl)
                .setDirectory(path)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(config.getGitlab().getUsername(), config.getGitlab().getPassword()))
                .call();
    }

}
