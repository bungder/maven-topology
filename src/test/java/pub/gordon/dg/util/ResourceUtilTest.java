package pub.gordon.dg.util;

import org.junit.Test;

import java.io.IOException;

/**
 * @author Gordon
 * @date 2017-11-17 17:37
 */
public class ResourceUtilTest {

    @Test
    public void test() {
        String content = "adfadfadf";
        String path = "~/Desktop/aaa/a.txt";
        try {
            ResourceUtil.write(content, path, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
