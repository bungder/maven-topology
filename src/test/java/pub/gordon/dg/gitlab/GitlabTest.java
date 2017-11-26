package pub.gordon.dg.gitlab;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Gordon
 * @date 2017-11-18 21:54
 */
public class GitlabTest {

    @Ignore
    @Test
    public void testConnect() throws IOException {
        String host = "http://gitlab.gordon.local";
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

}
