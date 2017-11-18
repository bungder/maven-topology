package pub.gordon.dg;

import org.apache.commons.collections4.CollectionUtils;
import pub.gordon.dg.bean.Dependency;
import pub.gordon.dg.bean.DirectiveNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 拓扑排序
 *
 * @author Gordon
 * @date 2017-11-01 00:36
 */
public class TopologicalSorting {

    public static <T> List<T> sort(Collection<Dependency<T>> relations) {
//        return deepFirstSort(relations);
        return general(relations);
    }

    public static <T> List<T> deepFirstSort(Collection<Dependency<T>> relations) {
        return null;
    }

    public static <T> List<T> general(Collection<Dependency<T>> relations) {
        if (CollectionUtils.isEmpty(relations)) {
            return Collections.EMPTY_LIST;
        }
        Set<DirectiveNode<T>> nodes = makeDegree(relations);
        List<T> result = new LinkedList<>();
        while (nodes.size() > 0) {
            int nodesSize = nodes.size();
            nodes.stream().filter(p -> p.getDependsBy().size() == 0).collect(Collectors.toList()).stream().forEach(p -> {
                p.getDependsOn().stream().forEach(c -> c.getDependsBy().remove(p));
                result.add(p.getSelf());
                nodes.remove(p);
            });
            /*nodes.stream().filter(p -> p.getDependsBy().size()==0).forEach(p -> {
                p.getDependsOn().stream().forEach(c -> c.getDependsBy().remove(p));
                result.add(p.getSelf());
                nodes.remove(p);
            });*/
            if (nodes.size() == nodesSize) {
                throw new RuntimeException("loop");
            }
        }
        Collections.reverse(result);
        return result;
    }

    private static <T> List<DirectiveNode<T>> makeGraph(Collection<Dependency<T>> relations) {
        Set<DirectiveNode<T>> nodes = makeDegree(relations);
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.EMPTY_LIST;
        }
        List<DirectiveNode<T>> result = new LinkedList<DirectiveNode<T>>();
        for (DirectiveNode<T> node : nodes) {
            if (CollectionUtils.isEmpty(node.getDependsBy())) {
                // in-degree is 0
                result.add(node);
            }
        }
        return result;
    }

    private static <T> Set<DirectiveNode<T>> makeDegree(Collection<Dependency<T>> relations) {
        if (CollectionUtils.isEmpty(relations)) {
            return Collections.EMPTY_SET;
        }
        Map<T, DirectiveNode<T>> map = new HashMap<T, DirectiveNode<T>>(relations.size());
        for (Dependency<T> d : relations) {
            if (!map.containsKey(d.getSource())) {
                map.put(d.getSource(), new DirectiveNode<T>(d.getSource()));
            }
            if (!map.containsKey(d.getDependsOn())) {
                map.put(d.getDependsOn(), new DirectiveNode<T>(d.getDependsOn()));
            }
            map.get(d.getSource()).addDependsOn(map.get(d.getDependsOn()));
        }
        return map.values().stream().collect(Collectors.toSet());
    }
}
