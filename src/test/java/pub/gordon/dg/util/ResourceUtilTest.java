package pub.gordon.dg.util;

import org.junit.After;
import org.junit.Before;
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

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: read(String resourcePath)
     */
    @Test
    public void testRead() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: readResource(String path)
     */
    @Test
    public void testReadResource() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getFile(String path)
     */
    @Test
    public void testGetFile() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: write(String content, String path, boolean echo)
     */
    @Test
    public void testWrite() throws Exception {
        //TODO: Test goes here...
    }


}
