package pub.gordon.dg.gitlab;

import com.sun.org.apache.regexp.internal.RE;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.junit.Test;
import pub.gordon.dg.bean.RepositoryConfig;
import pub.gordon.dg.util.GitUtil;
import pub.gordon.dg.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Gordon
 * @date 2017-11-18 21:54
 */
public class GitlabTest {

    @Test
    public void testConnect() throws IOException {
        String host = "http://gitlab.hd";
        GitlabSession session = GitlabAPI.connect(host, "gordon", "123123123");
        String token = session.getPrivateToken();
        System.out.println(MessageFormat.format("=========================> Private token is {0}", token));
        GitlabAPI api = GitlabAPI.connect(host, token);
        List<GitlabGroup> groups = api.getGroups();
        List<GitlabProject> projects = api.getProjects();
        if (projects != null) {
            System.out.println(MessageFormat.format("Number of projects is {0}", projects.size()));
            for (GitlabProject p : projects) {
                System.out.println(p.getName());
            }
        }
    }

    @Test
    public void cloneAll() throws IOException {
        RepositoryConfig config = RepositoryConfig.getInstance();
        GitlabSession session = GitlabAPI.connect(config.getGitlab().getUrl(), config.getGitlab().getUsername(), config.getGitlab().getPassword());
        String token = session.getPrivateToken();
        System.out.println(MessageFormat.format("=========================> Private token is {0}", token));
        GitlabAPI api = GitlabAPI.connect(config.getGitlab().getUrl(), token);
        try {
            List<GitlabProject> projects = api.getProjects();
            if (projects != null) {
                projects.parallelStream().forEach(p -> {
                    try {
                        GitUtil.cloneRepo(p.getHttpUrl(), new File(UrlUtil.concat(config.getDir(), p.getName())));
                    } catch (GitAPIException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                /*for (GitlabProject p : projects) {
                    GitUtil.cloneRepo(p.getHttpUrl(), new File(UrlUtil.concat(config.getDir(), p.getName())));
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMkdir() {
        RepositoryConfig config = RepositoryConfig.getInstance();
        File file = new File(UrlUtil.concat(config.getDir(), "aaaaaa"));
        if (!file.exists()) {
            file.mkdirs();
        }

    }

}
