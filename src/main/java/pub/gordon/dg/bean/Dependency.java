package pub.gordon.dg.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Abstraction of {@code T}'s dependency
 *
 * @param <T> Generic type to specify which data type this dependency refer to
 * @author Gordon
 * @date 2017-11-01 00:38
 */
public class Dependency<T> {

    private T source;
    private T dependsOn;

    public Dependency() {
    }

    public Dependency(T source, T dependsOn) {
        this.source = source;
        this.dependsOn = dependsOn;
    }

    public T getSource() {
        return source;
    }

    public T getDependsOn() {
        return dependsOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Dependency)) return false;

        Dependency<?> that = (Dependency<?>) o;

        return new EqualsBuilder()
                .append(getSource(), that.getSource())
                .append(getDependsOn(), that.getDependsOn())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSource())
                .append(getDependsOn())
                .toHashCode();
    }
}
