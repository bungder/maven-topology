package pub.gordon.dg.util;


import org.apache.commons.lang.StringUtils;

/**
 * @author Gordon
 * @date 2017-10-30 11:20
 */
public class UrlUtil {

    /**
     * 拼接URL
     *
     * @param node1
     * @param node2
     * @return
     */
    public static String concat(String node1, String node2) {
        if (StringUtils.isBlank(node1) && StringUtils.isBlank(node2)) {
            return "";
        }
        if (StringUtils.isBlank(node2)) {
            return node1;
        }
        if (node1.endsWith("/") && node2.startsWith("/")) {
            return node1.substring(0, node1.length() - 1) + node2;
        }
        if (!node1.endsWith("/") && !node2.startsWith("/")) {
            return node1 + "/" + node2;
        }
        return node1 + node2;
    }

    /**
     * 拼接URL
     * @param node1
     * @param node2
     * @param nodes
     * @return
     */
    public static String concat(String node1, String node2, String... nodes) {
        String result = concat(node1, node2);
        if (nodes != null && nodes.length > 0) {
            for (String s : nodes) {
                result = concat(result, s);
            }
        }
        return result;
    }
}
