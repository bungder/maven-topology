package pub.gordon.dg.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Gordon
 * @date 2017-11-18 22:36
 */
public class Assert {

    private ErrMsgBuilder err;

    public Assert(ErrMsgBuilder err) {
        this.err = err;
        if (err == null) throw new NullPointerException("DO NOT use null to construct " + this.getClass().getName());
    }

    public Assert stringNotBlank(String content, String errMsg) {
        if (StringUtils.isBlank(content)) err.append(errMsg);
        return this;
    }

    public Assert isTrue(boolean condition, String errMsg) {
        if (!condition)
            err.append(errMsg);

        return this;
    }

    public Assert isFalse(boolean condition, String errMsg) {
        if (condition) err.append(errMsg);
        return this;
    }

    public ErrMsgBuilder getMsg() {
        return err;
    }
}
