package pub.gordon.dg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * UrlUtil Tester.
 *
 * @author Gordon
 * @version 1.0
 * @since <pre>2017-11-26</pre>
 */
public class UrlUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: concat(String node1, String node2)
     */
    @Test
    public void testConcatForNode1Node2() throws Exception {
        String s = UrlUtil.concat("path1", "path2");
        System.out.println(s);
    }

    /**
     * Method: concat(String node1, String node2, String... nodes)
     */
    @Test
    public void testConcatForNode1Node2Nodes() throws Exception {
        String[] ss = {"node1", "node2", "node3", "node4"};
        String s = UrlUtil.concat("node", "node0", ss);
        System.out.println(s);
    }


} 
