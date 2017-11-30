package pub.gordon.dg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

/**
 * MemorySizeUtil Tester.
 *
 * @author Gordon
 * @version 1.0
 * @since <pre>2017-11-26</pre>
 */
public class MemorySizeUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: transformByteSize(int size)
     */
    @Test
    public void testTransformByteSize() throws Exception {
        long size = 1000L*1000*1000*1024*1024*5+10;
        System.out.println(MessageFormat.format("{0} transform to {1}", ""+size, MemorySizeUtil.transformByteSize(size)));
    }


} 
