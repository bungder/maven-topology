package pub.gordon.dg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RegUtil Tester.
 *
 * @author Gordon
 * @version 1.0
 * @since <pre>2017-11-26</pre>
 */
public class RegUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: match(String content, String regexp)
     */
    @Test
    public void testMatchForContentRegexp() throws Exception {
        assert RegUtil.match("123", "123");
    }

    /**
     * Method: match(Stream<String> stream, String regexp)
     */
    @Test
    public void testMatchForStreamRegexp() throws Exception {
        assert RegUtil.match(Stream.of("123", "123123", "333"), "123").collect(Collectors.toList()).size() == 2;
    }

    /**
     * Method: match(String content, Pattern pattern)
     */
    @Test
    public void testMatchForContentPattern() throws Exception {
        //TODO: Test goes here...
    }


} 
