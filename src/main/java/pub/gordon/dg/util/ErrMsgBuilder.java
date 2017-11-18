package pub.gordon.dg.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 错误消息构造器<br><br>
 * <p>
 * 使用此构造器可以将多次错误消息汇总到一起然后一次性返回
 *
 * @author Gordon
 * @version 2017-10-20 14:21
 */
public class ErrMsgBuilder {

    private boolean errorOccur = false;

    private StringBuilder msgBuilder = new StringBuilder();

    /**
     * 缺省的错误消息分隔符
     */
    public static final String DEFAULT_SEPARATOR = ",";

    /**
     * 错误消息分隔符
     */
    private String separator;

    public ErrMsgBuilder() {
        separator = DEFAULT_SEPARATOR;
    }

    /**
     * @param separator 缺省的错误消息分隔符
     */
    public ErrMsgBuilder(String separator) {
        this.separator = separator;
    }

    /**
     * 是否有错误出现
     *
     * @return true: 有错误发生；false: 没有错误发生
     */
    public boolean isErrorOccur() {
        return errorOccur;
    }

    /**
     * 获取汇总的错误消息
     *
     * @return 汇总的错误消息字符串
     */
    public String getErrMsg() {
        return msgBuilder.toString();
    }

    /**
     * 获取汇总的错误消息，如果消息文本以指定的{@code separator}结尾，则去掉尾部该部分
     *
     * @param separator 要去掉的尾部分隔符
     * @return 汇总的错误消息字符串
     */
    public String getErrMsgWithoutSeparatorTail(String separator) {
        if (StringUtils.isBlank(separator)) {
            return msgBuilder.toString().trim();
        }
        if (msgBuilder.length() == 0) {
            return msgBuilder.toString();
        }
        String str = msgBuilder.toString().trim();
        if (str.endsWith(separator)) {
            str = str.substring(0, str.length() - separator.length());
        }
        return str;
    }

    /**
     * 获取汇总的错误消息，去掉尾部分隔符。默认使用构造器指定的{@code separator}进行判断，如果改值为空，则使用{@code DEFAULT_SEPARATOR}判断
     *
     * @return 汇总的错误消息字符串
     */
    public String getErrMsgWithoutSeparatorTail() {
        String s = separator;
        if (StringUtils.isBlank(separator)) {
            s = DEFAULT_SEPARATOR;
        }
        return getErrMsgWithoutSeparatorTail(s);
    }

    /**
     * 追加一个错误消息构造器，使用{@link #DEFAULT_SEPARATOR}作为分隔符
     *
     * @param builder 错误消息构造器
     * @return 构造器自身
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

    public ErrMsgBuilder append(ErrMsgBuilder builder, String separator) {
        append(builder);
        if (separator != null) {
            msgBuilder.append(separator);
        }
        return this;
    }

    /**
     * 使用指定的分隔符追加一条错误消息
     *
     * @param errMsg    指定的错误消息分隔符，如果为空，则使用{@link #DEFAULT_SEPARATOR}
     * @param separator
     * @return 构造器自身
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
     * 追加一条错误消息<br><br>
     * <p>
     * 如果指定了缺省分隔符，则使用缺省分隔符分割，否则使用{@link #DEFAULT_SEPARATOR}
     *
     * @param errMsg 错误信息
     * @return 构造器自身
     */
    public ErrMsgBuilder append(String errMsg) {
        if(separator != null){
            return append(errMsg, separator);
        }
        return append(errMsg, DEFAULT_SEPARATOR);
    }
}
