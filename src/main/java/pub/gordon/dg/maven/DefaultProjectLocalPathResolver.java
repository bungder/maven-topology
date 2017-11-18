package pub.gordon.dg.maven;

import pub.gordon.dg.util.UrlUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 缺省的
 *
 * @author Gordon
 * @date 2017-11-16 23:14
 */
public class DefaultProjectLocalPathResolver extends ProjectPomLocalPathResolver {

    @Override
    public List<String> resolve(String projectName) {
        UrlUtil.concat(System.getProperty("user.dir"), "Documents/repo/tansc");
        String myPom = System.getProperty("user.dir") + File.separator + "pom.xml";
        return Arrays.asList(myPom);
    }
}
