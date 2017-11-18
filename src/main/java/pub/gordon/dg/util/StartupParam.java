package pub.gordon.dg.util;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gordon
 * @date 2017-11-17 14:28
 */
public class StartupParam {

    public static final String P_HELP = "-Dh";
    public static final String P_REPO_DIR = "-Drepo.dir";
    public static final String P_REPO_RULE_TAG_PATTERN = "-Drule.tag.pattern";
    public static final String P_REPO_RULE_TAG_ORDER = "-Drule.tag.order";
    public static final String P_REPO_RULE_GROUPID_PATTERN = "-Drule.groupId.pattern";
    public static final String P_REPO_RULE_ARTIFACT_PATTERN = "-Drule.artifactId.pattern";
    public static final String P_REPO_GITLAB_URL = "-Dgitlab.url";
    public static final String P_REPO_GITLAB_USERNAME = "-Dgitlab.username";
    public static final String P_REPO_GITLAB_PASSWORD = "-Dgitlab.password";
    public static final String P_REPO_CONFIG_FILE = "-Dc";
    public static final String P_OUTPUT_FILE = "-Do";
    public static final String P_INPUT_FILE = "-Di";

    private static volatile Map<String, String> map;

    static {
        map = load();
    }

    private static Map<String, String> load() {
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        Map<String, String> param = new ConcurrentHashMap<>(inputArguments.size() * 2);
        for (String line : inputArguments) {
            String[] pair = ParamUtil.separate(line);
            param.put(pair[0], pair[1]);
        }
        Map<String, String> a = ParamUtil.getArgs().get();
        if (a != null && a.size() > 0) {
            param.putAll(ParamUtil.getArgs().get());
        }
        return param;
    }



    public static String get(String key) {
        return map.get(key);
    }

}
