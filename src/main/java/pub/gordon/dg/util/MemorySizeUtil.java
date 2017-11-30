package pub.gordon.dg.util;

/**
 * @author Gordon
 * @date 2017-10-11 09:36
 */
public class MemorySizeUtil {


    public static String transformByteSize(long size) {
        StringBuilder sb = new StringBuilder();
        long pb = 0, tb = 0, gb = 0, mb = 0, kb = 0, b = 0;
        kb = size >> 10;
        mb = kb >> 10;
        gb = mb >> 10;
        tb = gb >> 10;
        pb = tb >> 10;
        tb = tb % 1024;
        gb = gb % 1024;
        mb = mb % 1024;
        kb = kb % 1024;
        b = size % 1024;
        if (pb > 0) {
            sb.append(pb).append("PB ");
        }
        if (tb > 0) {
            sb.append(tb).append("TB ");
        }
        if (gb > 0) {
            sb.append(gb).append("GB ");
        }
        if (mb > 0) {
            sb.append(mb).append("MB ");
        }
        if (kb > 0) {
            sb.append(kb).append("KB ");
        }
        if (b > 0) {
            sb.append(b).append("B ");
        }
        return sb.toString().trim();
    }

}
