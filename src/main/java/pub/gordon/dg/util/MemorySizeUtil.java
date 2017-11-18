package pub.gordon.dg.util;

/**
 * @author Gordon
 * @date 2017-10-11 09:36
 */
public class MemorySizeUtil {


    public static String transformByteSize(int size){
        int mb = 0, kb = 0, b = 0;
        kb = size / 1024;
        mb = kb / 1024;
        kb = kb%1024;
        b = size % 1024;
        String result = "";
        if(mb > 0){
            result += mb + "MB ";
        }
        if(kb > 0){
            result += kb + "KB ";
        }
        if(b > 0){
            result += b + "B";
        }
        return result;
    }

}
