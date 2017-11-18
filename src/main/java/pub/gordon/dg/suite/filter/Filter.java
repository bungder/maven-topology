package pub.gordon.dg.suite.filter;


/**
 * @author Gordon
 * @date 2017-11-03 10:41
 */
public interface Filter<T> {

    boolean accept(T node);

}
