package pub.gordon.dg.maven.suite.filter;


import pub.gordon.dg.maven.bean.MavenNode;
import pub.gordon.dg.suite.filter.Filter;

/**
 * @author Gordon
 * @date 2017-11-03 11:30
 */
public abstract class MavenFilter implements Filter<MavenNode> {

    @Override
    public abstract boolean accept(MavenNode node);
}
