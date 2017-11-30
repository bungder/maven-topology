package pub.gordon.dg.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.exception.SilentException;


/**
 * @author Gordon
 * @date 2017-11-30 16:26
 */
public class ExceptionProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionProcessor.class);


    public static void processAndExit(Throwable e, Logger logger) {
        if (e instanceof SilentException) {
            logger.error(e.getMessage());
            printUsage(logger);
        } else {
            e.printStackTrace();
            printUsage(logger);
        }
        System.exit(2);
    }

    public static void processAndExit(Throwable e) {
        processAndExit(e, LOG);
    }

    private static void printUsage(Logger logger) {
        if (StringUtils.isBlank(StartupParam.get(StartupParam.P_REPO_CONFIG_FILE))
                || StringUtils.isBlank(StartupParam.get(StartupParam.P_INPUT_FILE))) {
            logger.info("\n\n" + Usage.get());
        }
    }

}
