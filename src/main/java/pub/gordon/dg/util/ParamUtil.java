package pub.gordon.dg.util;

import java.util.Map;

/**
 * @author Gordon
 * @date 2017-11-17 16:57
 */
public class ParamUtil {

    private static ThreadLocal<Map<String, String>> args = new ThreadLocal<>();

    public static String[] separate(String line) {
        String[] pair = new String[2];
        int index = line.indexOf('=');
        if (index > 0) {
            pair[0] = line.substring(0, index);
            pair[1] = line.substring(index + 1, line.length());
        } else {
            pair[0] = line;
            pair[1] = "1";
        }
        return pair;
    }

    public static ThreadLocal<Map<String, String>> getArgs() {
        return args;
    }
}
