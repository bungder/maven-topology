package pub.gordon.dg.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.exception.UnknownCommandException;

import java.io.*;

/**
 * @author Gordon
 * @date 2017-11-03 11:39
 */
public class ScriptCaller {
    private static final Logger logger = LoggerFactory.getLogger(ScriptCaller.class);

    public static void call(String path) throws IOException, InterruptedException, UnknownCommandException {
        if (StringUtils.isBlank(path)) {
            throw new NullPointerException("缺少脚本路径");
        }
        if (path.endsWith(".sh")) {
            callSH(path);
        } else if (path.endsWith(".bat")) {
            callBAT(path);
        } else {
            throw new UnknownCommandException("未知的脚本类型：" + path);
        }
    }

    public static void callBAT(String path) throws IOException {
        Process child = Runtime.getRuntime().exec("cmd.exe /C start " + path);
        InputStream in = child.getInputStream();
        int c;
        while ((c = in.read()) != -1) {
        }
        in.close();
        try {
            child.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("done");

    }

    public static void exeCommand(String workDir, String command) throws InterruptedException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("cd ").append(workDir).append("\n").append(command).append("\n");
        Process child = Runtime.getRuntime().exec(sb.toString());
        child.waitFor();
    }


    public static void callSH(String path) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        String command = path;
        Process pcs = rt.exec(command);
        PrintWriter outWriter = new PrintWriter(new File("/export/home/zjg/show.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(pcs.getInputStream()));
        String line = new String();
        logger.info(line);
        outWriter.write(line);
        pcs.waitFor();
        br.close();
        outWriter.flush();
        outWriter.close();
        int ret = pcs.exitValue();
        logger.info(String.valueOf(ret));
        logger.info("执行完毕!");
    }

}
