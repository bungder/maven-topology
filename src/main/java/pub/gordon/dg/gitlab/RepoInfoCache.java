package pub.gordon.dg.gitlab;

import org.apache.commons.collections4.CollectionUtils;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import pub.gordon.dg.bean.RepositoryConfig;
import pub.gordon.dg.exception.GitRepoLocateException;
import pub.gordon.dg.util.ErrMsgBuilder;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gordon
 * @date 2017-11-17 09:38
 */
public class RepoInfoCache {

    private static volatile boolean initialized = false;
    private static RepositoryConfig.GitlabConfig config = RepositoryConfig.getInstance().getGitlab();
    private static ConcurrentHashMap<String, GitlabProject> projectMap = new ConcurrentHashMap<>(100);
    private static String[] groupPriority = {"mm_platform", "mm_platform_new"};

    static {
        try {
            init();
        } catch (IOException | GitRepoLocateException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }


    private static synchronized void init() throws IOException, GitRepoLocateException {
        if (initialized) return;
        GitlabSession session = GitlabAPI.connect(config.getUrl(), config.getUsername(), config.getPassword());
        String token = session.getPrivateToken();
        GitlabAPI api = GitlabAPI.connect(config.getUrl(), token);
        List<GitlabProject> projects = api.getProjects();
        if (CollectionUtils.isEmpty(projects)) {
            throw new GitRepoLocateException(MessageFormat.format("Could not find any repository in GitLab. [host: {0}, username: {1}]", config.getUrl(), config.getUsername()));
        }
        ErrMsgBuilder err = new ErrMsgBuilder("\n");
        for (GitlabProject p : projects) {
            if (projectMap.containsKey(p.getName())) {
                boolean conflicted = true;
                GitlabProject p0 = projectMap.get(p.getName());
                for (String group : groupPriority) {
                    if (group.equalsIgnoreCase(tirmHttpUrlGroupName(p.getHttpUrl()))) {
                        projectMap.put(p.getName(), p);
                        conflicted = false;
                        break;
                    } else if (group.equalsIgnoreCase(tirmHttpUrlGroupName(p0.getHttpUrl()))) {
                        conflicted = false;
                        break;
                    }
                }
                if (conflicted) {
                    err.append(MessageFormat.format("Duplicated repository name: {0}, url: {1}, {2}", p.getName(),
                            p0.getWebUrl(), p.getWebUrl()));
                }
            } else {
                projectMap.put(p.getName(), p);
            }
        }
        if (err.isErrorOccur()) {
            throw new GitRepoLocateException(err.getErrMsgWithoutSeparatorTail());
        }
        initialized = true;
    }

    public static GitlabProject getProjectByName(String projectName) {
        return projectMap.get(projectName);
    }

    private static String tirmHttpUrlGroupName(String httpUrl) throws GitRepoLocateException {
        char[] cs = httpUrl.toCharArray();
        int start = 0, end = 0;
        for (int i = cs.length - 1; i > 0; i--) {
            if (cs[i] == '/') {
                if (end == 0) {
                    end = i;
                } else {
                    start = i;
                    break;
                }
            }
        }
        if (start == 0 || end == 0) {
            throw new GitRepoLocateException("Resolve group name form http url failed: " + httpUrl);
        }
        return httpUrl.substring(start + 1, end);
    }


}
