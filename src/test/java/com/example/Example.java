package com.example;

import cs1.graphs.GraphNode;
import cs1.graphs.UnweightedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Example {
    public static <T> int size(UnweightedGraph<T> graph) {
        return graph.getEdges().size();
    }

    public static <T> int sizeWithNodes(UnweightedGraph<T> graph) {
        return graph.getNodes().size();
    }

    public static <T> int size(GraphNode<T> node) {
        Set<GraphNode<T>> nodes = new HashSet<>();
        traverse(node, nodes);
        return nodes.size();
    }

    public static int sum(UnweightedGraph<Integer> graph) {
        return graph.getEdges().keySet().stream().mapToInt(GraphNode::getValue).sum();
    }

    public static int sum(GraphNode<Integer> node) {
        Set<GraphNode<Integer>> nodes = new HashSet<>();
        traverse(node, nodes);
        return nodes.stream().mapToInt(GraphNode::getValue).sum();
    }

    private static <T> void traverse(GraphNode<T> node, Set<GraphNode<T>> visited) {
        visited.add(node);
        for (GraphNode<T> neighbor : node.getNeighbors()) {
            if (!(visited.contains(neighbor))) {
                traverse(neighbor, visited);
            }
        }
    }

    public static <T> boolean hasCycle(UnweightedGraph<T> graph) {
        return hasCycle(graph.getNode(), new ArrayList<>());
    }

    private static <T> boolean hasCycle(GraphNode<T> node, List<GraphNode<T>> visited) {
        if (visited.contains(node)) {
            return visited.indexOf(node) != visited.size() - 2;
        }
        visited.add(node);
        for (GraphNode<T> neighbor : node.getNeighbors()) {
            if (hasCycle(neighbor, new ArrayList<>(visited))) {
                return true;
            }
        }
        return false;
    }
}
