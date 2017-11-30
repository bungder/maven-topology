package pub.gordon.dg.util;

import java.io.IOException;

/**
 * @author Gordon
 * @date 2017-11-30 11:28
 */
public class Usage {

    public static String get() {
        try {
            return ResourceUtil.readResource("usage.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
