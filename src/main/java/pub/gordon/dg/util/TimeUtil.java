package pub.gordon.dg.util;

/**
 * @author Gordon
 * @date 2017-10-30 21:11
 */
public class TimeUtil {

    public static final long SECOND_LEN = 1000;
    public static final long MINUTE_LEN = SECOND_LEN * 60;
    public static final long HOUR_LEN = MINUTE_LEN * 60;
    public static final long DAY_LEN = HOUR_LEN * 24;

    public static String normalizeTime(long period) {
        int day = 0, hour = 0, minute = 0, second = 0, milli = 0;
        day = (int) (period / DAY_LEN);
        hour = (int) ((period % DAY_LEN) / HOUR_LEN);
        minute = (int) ((period % HOUR_LEN) / MINUTE_LEN);
        second = (int) ((period % MINUTE_LEN) / SECOND_LEN);
        milli = (int) (period % SECOND_LEN);
        StringBuilder sb = new StringBuilder();
        if(day > 0){
            sb.append(day).append("天");
        }
        if(hour > 0){
            sb.append(hour).append("小时");
        }
        if(minute > 0){
            sb.append(minute).append("分钟");
        }
        if(second > 0){
            sb.append(second).append("秒");
        }
        if(milli > 0){
            sb.append(milli).append("毫秒");
        }
        return sb.toString();
    }

}
