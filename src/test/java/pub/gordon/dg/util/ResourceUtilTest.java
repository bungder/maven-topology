package pub.gordon.dg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Gordon
 * @date 2017-11-17 17:37
 */
public class ResourceUtilTest {

    String content = "123123123123";
    String path = "~/test.txt";

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
        System.out.println("write");
        ResourceUtil.write(content, path, true);
    }

    @After
    public void after() throws Exception {
        File file = ResourceUtil.getFile("~/test.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Method: read(String resourcePath)
     */
    @Test
    public void testRead() throws Exception {
        String s = ResourceUtil.read(path);
        assert content.equals(content);
    }

    /**
     * Method: readResource(String path)
     */
    @Test
    public void testReadResource() throws Exception {
        String s = ResourceUtil.readResource(path);
        assert content.equals(content);
    }

    /**
     * Method: getFile(String path)
     */
    @Test
    public void testGetFile() throws Exception {
        File file = ResourceUtil.getFile("~/test.txt");
        assert file.exists();
    }

    /**
     * Method: write(String content, String path, boolean echo)
     */
    @Test
    public void testWrite() throws Exception {
        //TODO: Test goes here...
    }


}
