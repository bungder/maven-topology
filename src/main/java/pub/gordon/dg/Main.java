package pub.gordon.dg;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.bean.RepositoryConfig;
import pub.gordon.dg.maven.DependencyResolver;
import pub.gordon.dg.maven.bean.MavenNode;
import pub.gordon.dg.maven.suite.filter.GAMavelFilter;
import pub.gordon.dg.suite.DGSuit;
import pub.gordon.dg.util.ParamUtil;
import pub.gordon.dg.util.ResourceUtil;
import pub.gordon.dg.util.StartupParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static pub.gordon.dg.util.StartupParam.*;

/**
 * @author Gordon
 * @date 2017-11-17 14:25
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            Map<String, String> map = new ConcurrentHashMap<>(args.length * 2);
            for (String line : args) {
                String[] pair = ParamUtil.separate(line);
                map.put(pair[0], pair[1]);
            }
            ParamUtil.getArgs().set(map);
        }

        if (!StringUtils.isBlank(StartupParam.get(P_HELP))) {
            try {
                System.out.println("\n\n" + ResourceUtil.readResource("usage.txt"));
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                return;
            }
        }
        if (StringUtils.isBlank(StartupParam.get(P_INPUT_FILE))) {
            logger.error("{} is required. Use {} to get help.", P_INPUT_FILE, P_HELP);
            return;
        }
        if (StringUtils.isBlank(StartupParam.get(P_OUTPUT_FILE))) {
            logger.error("{} is required. Use {} to get help.", P_OUTPUT_FILE, P_HELP);
            return;
        }
        try {
            String content = ResourceUtil.read(StartupParam.get(P_INPUT_FILE));
            if (StringUtils.isBlank(content)) {
                logger.error(StartupParam.get(P_INPUT_FILE + " is empty."));
                System.exit(2);
            }
            RepositoryConfig.Rule rule = RepositoryConfig.getInstance().getRule();
            DGSuit<MavenNode> suit = new DGSuit<>(Arrays.asList(new GAMavelFilter(rule.getGroupIdPattern(), rule.getArtifactIdPattern())));
            List<MavenNode> nodes = DependencyResolver.sortEffected(
                    Arrays.asList(content.split("\n")).stream().map(String::trim).collect(Collectors.toList()),
                    suit);
            StringBuilder sb = new StringBuilder();
            for (MavenNode n : nodes) {
                sb.append(n.getArtifactId()).append("\n");
            }
            ResourceUtil.write(sb.toString(), StartupParam.get(P_OUTPUT_FILE), true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
