package pub.gordon.dg.bean;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.util.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import static pub.gordon.dg.util.StartupParam.*;

/**
 * @author Gordon
 * @date 2017-11-16 23:22
 */
public class RepositoryConfig implements Serializable {
    private static final long serialVersionUID = -6275184640159327579L;
    private static final Logger logger = LoggerFactory.getLogger(RepositoryConfig.class);

    public static class GitlabConfig implements Serializable {
        private static final long serialVersionUID = -8439051906568762668L;
        private String url;
        private String username;
        private String password;

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class Rule implements Serializable {
        private static final long serialVersionUID = 4461992305218970169L;
        private String tagPattern;
        private String groupIdPattern;
        private String artifactIdPattern;
        private Order tagOrder = Order.DESC;

        public String getTagPattern() {
            return tagPattern;
        }

        public String getGroupIdPattern() {
            return groupIdPattern;
        }

        public String getArtifactIdPattern() {
            return artifactIdPattern;
        }

        public Order getTagOrder() {
            return tagOrder;
        }
    }

    private String dir;
    private Rule rule;
    private GitlabConfig gitlab;

    public String getDir() {
        return dir;
    }

    public Rule getRule() {
        return rule;
    }

    public GitlabConfig getGitlab() {
        return gitlab;
    }

    private static RepositoryConfig instance;

    static {
        if (!StringUtils.isBlank(StartupParam.get(P_REPO_CONFIG_FILE))) {
            try {
                instance = load(StartupParam.get(P_REPO_CONFIG_FILE));
            } catch (Throwable e) {
                try {
                    instance = load("config.json");
                } catch (Throwable e2) {
                    e.printStackTrace();
                    e2.printStackTrace();
                    System.err.println("Loading repository config failed. Please make sure you have place repo.json correctly.");
                    System.exit(2);
                }
            }
            validate();
        } else {
            instance = new RepositoryConfig();
            instance.dir = g(P_REPO_DIR);
            instance.rule = new Rule();
            instance.rule.artifactIdPattern = g(P_REPO_RULE_ARTIFACT_PATTERN);
            instance.rule.groupIdPattern = g(P_REPO_RULE_GROUPID_PATTERN);
            instance.rule.tagPattern = g(P_REPO_RULE_TAG_PATTERN);
            String tagOrder = g(P_REPO_RULE_TAG_ORDER);
            if (!StringUtils.isBlank(tagOrder)
                    || "asc".equalsIgnoreCase(tagOrder)
                    || "desc".equalsIgnoreCase(tagOrder))
                instance.rule.tagOrder = Order.valueOf(tagOrder.toUpperCase());
            instance.gitlab = new GitlabConfig();
            instance.gitlab.password = g(P_REPO_GITLAB_PASSWORD);
            instance.gitlab.username = g(P_REPO_GITLAB_USERNAME);
            instance.gitlab.url = g(P_REPO_GITLAB_URL);
            validate();
        }
    }

    private static void validate() {
        ErrMsgBuilder err = new Assert(new ErrMsgBuilder(";\n"))
                .stringNotBlank(instance.dir, "Repository directory is required")
                .stringNotBlank(instance.rule.tagPattern, "Tag pattern is required")
                .isFalse((StringUtils.isBlank(instance.rule.artifactIdPattern)
                                && StringUtils.isBlank(instance.rule.groupIdPattern)),
                        "GroupId pattern or artifactId could not be blank both at the same time")
                .stringNotBlank(instance.gitlab.url, "Gitlab url is required")
                .stringNotBlank(instance.gitlab.username, "Gitlab username is required")
                .stringNotBlank(instance.gitlab.password, "Password of gitlab user is required").getMsg();
        if (err.isErrorOccur()) {
            logger.error(MessageFormat.format("Loading config failed: \n{0}", err.getErrMsgWithoutSeparatorTail()));
            System.exit(2);
        }
    }

    private static RepositoryConfig load(String path) throws IOException, URISyntaxException {
        String content = ResourceUtil.read(path);
        RepositoryConfig config = JsonUtil.parse(content, RepositoryConfig.class);
        return config;
    }

    public static RepositoryConfig getInstance() {
        return instance;
    }

    private static String g(String key) {
        return StartupParam.get(key);
    }
}
