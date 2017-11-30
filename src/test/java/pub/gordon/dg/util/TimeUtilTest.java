package pub.gordon.dg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TimeUtil Tester.
 *
 * @author Gordon
 * @version 1.0
 * @since <pre>十一月 26, 2017</pre>
 */
public class TimeUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: normalizeTime(long period)
     */
    @Test
    public void testNormalizeTime() throws Exception {
        System.out.println(TimeUtil.normalizeTime(1000*60*24 + 100));
    }


} 
