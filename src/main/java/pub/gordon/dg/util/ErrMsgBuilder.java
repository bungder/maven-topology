package pub.gordon.dg.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Builder for Error Message <br><br>
 * <p>
 * Using this builder could help you combine several error messages into one and get them at single call.
 *
 * @author Gordon
 * @version 2017-10-20 14:21
 */
public class ErrMsgBuilder {

    /**
     * Default separator for error message
     */
    public static final String DEFAULT_SEPARATOR = ",";
    private boolean errorOccur = false;
    private StringBuilder msgBuilder = new StringBuilder();
    /**
     * Separator for error message
     */
    private String separator;

    public ErrMsgBuilder() {
        separator = DEFAULT_SEPARATOR;
    }

    /**
     * @param separator Default separator for error message
     */
    public ErrMsgBuilder(String separator) {
        this.separator = separator;
    }

    /**
     * To see if there is any error occurs.
     *
     * @return true: Error occurs; false: No error
     */
    public boolean isErrorOccur() {
        return errorOccur;
    }

    /**
     * Get the combined error message.
     *
     * @return String that represents the combined error message
     */
    public String getErrMsg() {
        return msgBuilder.toString();
    }

    /**
     * Get the combined error message. If message text ends with the specific {@code separator}, that part would be removed.
     *
     * @param separator The tail should be removed
     * @return String that represents the combined error message
     */
    public String getErrMsgWithoutSeparatorTail(String separator) {
        if (StringUtils.isBlank(separator)) {
            return msgBuilder.toString().trim();
        }
        if (msgBuilder.length() == 0) {
            return msgBuilder.toString();
        }
        String str = msgBuilder.toString().trim();
        separator = separator.trim();
        if (str.endsWith(separator)) {
            str = str.substring(0, str.length() - separator.length());
        }
        return str;
    }

    /**
     * Get the combined error message. If message text ends with the specific {@code separator}, that part would be removed.
     * By default, using the {@link #separator} that was specified by constructor. If it is empty, using {@link #DEFAULT_SEPARATOR}
     *
     * @return String that represents the combined error message
     */
    public String getErrMsgWithoutSeparatorTail() {
        String s = separator;
        if (StringUtils.isBlank(separator)) {
            s = DEFAULT_SEPARATOR;
        }
        return getErrMsgWithoutSeparatorTail(s);
    }

    /**
     * Append an Error Message Builder, using {@link #DEFAULT_SEPARATOR} as separator
     *
     * @param builder ErrMsgBuilder instance
     * @return builder itself "as-is"
     */
    public ErrMsgBuilder append(ErrMsgBuilder builder) {
        if (builder.errorOccur) {
            this.errorOccur = true;
            if (builder.msgBuilder.length() > 0) {
                this.msgBuilder.append(builder.msgBuilder);
            }
        }
        return this;
    }

    /**
     * Append an Error Message Building, using {@code separator} as separator
     *
     * @param builder   ErrMsgBuilder instance
     * @param separator Specified separator
     * @return builder itself "as-is"
     */
    public ErrMsgBuilder append(ErrMsgBuilder builder, String separator) {
        append(builder);
        if (separator != null) {
            msgBuilder.append(separator);
        }
        return this;
    }

    /**
     * Append an error message, using specified separator.
     *
     * @param errMsg    Message to be appended
     * @param separator Specified separator, if empty, using {@link #DEFAULT_SEPARATOR}
     * @return builder itself "as-is"
     */
    public ErrMsgBuilder append(String errMsg, String separator) {
        if (separator == null) {
            separator = DEFAULT_SEPARATOR;
        }
        if (!StringUtils.isBlank(errMsg)) {
            this.msgBuilder.append(errMsg);
            errorOccur = true;
            if (separator != null) {
                this.msgBuilder.append(separator);
            }
        }
        return this;
    }

    /**
     * Append an error message<br><br>
     * <p>
     * If default separator was specified, using it, or using {@link #DEFAULT_SEPARATOR}
     *
     * @param errMsg Message to be appended
     * @return builder itself "as-is"
     */
    public ErrMsgBuilder append(String errMsg) {
        if (separator != null) {
            return append(errMsg, separator);
        }
        return append(errMsg, DEFAULT_SEPARATOR);
    }
}
