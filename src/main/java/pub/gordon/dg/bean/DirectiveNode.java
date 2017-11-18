package pub.gordon.dg.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Gordon
 * @date 2017-11-01 00:13
 */
public class DirectiveNode<T> {

    private T self;

    private Set<DirectiveNode<T>> dependsOn = new HashSet<DirectiveNode<T>>();

    private Set<DirectiveNode<T>> dependsBy = new HashSet<DirectiveNode<T>>();

    public DirectiveNode() {
    }

    public DirectiveNode(T self) {
        this.self = self;
    }

    public Set<DirectiveNode<T>> getDependsOn() {
        return dependsOn;
    }

    public Set<DirectiveNode<T>> getDependsBy() {
        return dependsBy;
    }

    public T getSelf() {
        return self;
    }

    public void addDependsOn(DirectiveNode<T>... nodes) {
        if (nodes == null || nodes.length == 0) {
            return;
        }
        for (DirectiveNode<T> d : nodes) {
            dependsOn.add(d);
            d.dependsBy.add(this);
        }
    }

    public void addDependsBy(DirectiveNode<T>... nodes) {
        if (nodes == null || nodes.length == 0) {
            return;
        }
        for (DirectiveNode<T> d : nodes) {
            dependsBy.add(d);
            d.dependsOn.add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DirectiveNode)) return false;

        DirectiveNode<?> that = (DirectiveNode<?>) o;

        return new EqualsBuilder()
                .append(getSelf(), that.getSelf())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSelf())
                .toHashCode();
    }
}
