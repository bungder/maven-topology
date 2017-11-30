package pub.gordon.dg.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Gordon
 * @date 2017-11-16 23:28
 */
public class ResourceUtil {

    private static ClassLoader loader = ResourceUtil.class.getClassLoader();
    private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

    public static String read(String resourcePath) throws URISyntaxException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(getFile(resourcePath)));
        StringBuilder sb = new StringBuilder();
        char[] tmp = new char[2048];
        int len = 0;
        while ((len = reader.read(tmp)) > 0) {
            sb.append(tmp, 0, len);
        }
        reader.close();
        return sb.toString();
    }

    public static String readResource(String path) throws IOException {
        InputStream input = ResourceUtil.class.getClassLoader().getResourceAsStream(path);
        StringBuilder sb = new StringBuilder();
        int len;
        byte[] tmp = new byte[1024];
        while ((len = input.read(tmp)) != -1) {
            sb.append(new String(tmp, 0, len));
        }
        input.close();
        return sb.toString();
    }

    public static File getFile(String path, boolean createOnMissing) throws URISyntaxException, FileNotFoundException {
        if (StringUtils.isBlank(path)) throw new FileNotFoundException("path is empty");
        if (path.startsWith("~")) {
            path = UrlUtil.concat(System.getProperty("user.home"), path.substring(1));
        }
        File f = new File(path);
        if (f.exists()) return f;
        if (!path.startsWith("/") && !path.startsWith("~")) {
            f = new File(UrlUtil.concat(System.getProperty("user.dir"), path));
            if (f.exists()) return f;
        }
        try {
            URL url = loader.getResource(path);
            if (url == null) throw new FileNotFoundException(path);
            URI uri = new URI(url.toString());
            f = new File(uri.getPath());
            if (f.exists()) return f;
            throw new FileNotFoundException(path);
        } catch (FileNotFoundException e) {
            if (!createOnMissing) throw e;
            f.mkdirs();
            return f;
        }
    }

    public static File getFile(String path) throws FileNotFoundException, URISyntaxException {
        return getFile(path, false);
    }

    public static void write(String content, String path, boolean echo) throws IOException {
        if (path.startsWith("~")) {
            path = UrlUtil.concat(System.getProperty("user.home"), path.substring(1));
        }
        File f = new File(path);
        f = f.getAbsoluteFile();

        if (f.getParentFile() == null || !f.getParentFile().exists()) {
            new File(f.getParent()).mkdirs();
        }
        FileUtils.write(f, content);
        if (echo) {
            logger.info("Result is wrote to " + path);
        }
    }

}
