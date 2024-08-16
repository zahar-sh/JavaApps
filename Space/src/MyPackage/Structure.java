package MyPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;

public class Structure<K, V> {

    private static class Node<K, V> {
        public V value;
        public HashMap<K, Node<K, V>> nodeMap = new HashMap<>();
        public Node<K, V> previousNode;

        public Node(Node<K, V> previousNode) {
            this.previousNode = previousNode;
        }

        @Override
        public int hashCode() {
            long bits = nodeMap.hashCode() * 31 + (value == null ? 0 : value.hashCode());
            return (((int) bits) ^ ((int) (bits >> 32)));
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof Node) {
                @SuppressWarnings("unchecked")
                Node<K, V> n = (Node<K, V>) obj;
                return Objects.equals(value, n.value) && nodeMap.equals(n.nodeMap);
            }
            return false;
        }
    }

    private Node<K, V> node, startNode, savedNode;

    public Structure() {
        node = new Node<>(null);
        startNode = node;
        savedNode = node;
    }

    public void nextNode(K key) {
        node = node.nodeMap.get(key);
    }

    public void previousNode() {
        node = node.previousNode;
    }

    public void toSavedNode() {
        node = savedNode;
    }

    public void saveNode() {
        savedNode = node;
    }

    public void setNode(K key) {
        node.nodeMap.put(key, new Node<>(node));
    }

    public V put(V value) {
        if (node == null) {
            throw new NullPointerException("This node is null");
        } else {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }
    }

    public V get() {
        return node.value;
    }

    public boolean contains(V value) {
        return Objects.equals(value, node.value);
    }

    private void setStartNode() {
        while (startNode.previousNode != null) {
            startNode = startNode.previousNode;
        }
    }

    public ArrayList<V> getAllValues() {
        ArrayList<V> list = new ArrayList<>();
        setStartNode();
        insert(startNode, list);
        return list;
    }

    private void insert(Node<K, V> node, ArrayList<V> list) {
        if (node.value != null) {
            list.add(node.value);
        }
        for (Map.Entry<K, Node<K, V>> e : node.nodeMap.entrySet()) {
            insert(e.getValue(), list);
        }
    }

    @Override
    public int hashCode() {
        setStartNode();
        return startNode.hashCode() * 37;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Structure) {
            @SuppressWarnings("unchecked")
            Structure<K, V> s = (Structure<K, V>) obj;
            s.setStartNode();
            setStartNode();
            return startNode.equals(s.startNode);
        }
        return false;
    }
}