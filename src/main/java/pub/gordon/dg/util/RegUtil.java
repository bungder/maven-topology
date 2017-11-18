package pub.gordon.dg.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Gordon
 * @date 2017-11-18 22:56
 */
public class RegUtil {

    public static boolean match(String content, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(content);
        return matcher != null && matcher.find();
    }

    public static Stream<String> match(Stream<String> stream, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        return stream.filter(s -> match(s, pattern));
    }

    public static boolean match(String content, Pattern pattern){
        Matcher matcher = pattern.matcher(content);
        return matcher != null && matcher.find();
    }

}
