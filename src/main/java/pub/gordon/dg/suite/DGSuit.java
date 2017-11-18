package pub.gordon.dg.suite;

import pub.gordon.dg.suite.filter.Filter;

import java.util.List;

/**
 * 有向图套件
 *
 * @author Gordon
 * @date 2017-11-03 10:45
 */
public class DGSuit<T> {

    private List<Filter<T>> filters;

    public DGSuit(List<Filter<T>> filters) {
        this.filters = filters;
    }

    public List<Filter<T>> getFilters() {
        return filters;
    }

}
