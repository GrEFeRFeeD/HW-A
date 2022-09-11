package collections.map;

import collections.lists.MyLinkedList;
import collections.lists.MyList;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private static final int START_CAPACITY = 16;
    private static final int MAX_CAPACITY = 4096;
    private static final double loadFactor = 0.75;
    private MyList<EntryNode<K, V>>[] entryNodeListArray;
    private int elementsCount;
    private int threshold;

    public MyHashMap(){
        clear();
    }

    public MyHashMap(Map<K, V> map) {
        clear();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void put(K key, V value) {

        putPair(key, value);
        if (elementsCount >= threshold && entryNodeListArray.length < MAX_CAPACITY) {
            resize();
        }
    }

    private void putPair(K key, V value) {
        int hash = getHash(key);
        int index = hash % entryNodeListArray.length;


        for (EntryNode<K, V> node : entryNodeListArray[index]) {
            if (node.equals(new EntryNode<>(key, value))) {
                node.setValue(value);
                return;
            }
        }

        entryNodeListArray[index].add(new EntryNode<>(key, value));
        elementsCount++;
    }

    private static<K> int getHash(K key) {
        return (key == null ? 0 : key.hashCode());
    }

    private void resize() {
        Set<EntryNode<K, V>> nodeSet = getNodeSet();
        MyLinkedList<EntryNode<K, V>>[] newArray = (MyLinkedList<EntryNode<K, V>>[]) new MyLinkedList<?>[Math.min(entryNodeListArray.length * 2, MAX_CAPACITY)];
        for (int i = 0; i < newArray.length; ++i) {
            newArray[i] = new MyLinkedList<>();
        }
        this.entryNodeListArray = newArray;
        elementsCount = 0;
        threshold = (int) (2 * entryNodeListArray.length * loadFactor);

        for (EntryNode<K, V> node : nodeSet) {
            putPair(node.getKey(), node.getValue());
        }
    }

    @Override
    public void remove(K key) {
        int hash = getHash(key);
        int index = hash % entryNodeListArray.length;

        int nodeIndex = 0;
        boolean isNodeFound = false;
        for (EntryNode<K, V> node : entryNodeListArray[index]) {
            if (node.keyEquals(key)) {
                isNodeFound = true;
                break;
            }
            nodeIndex++;
        }

        if (isNodeFound) {
            entryNodeListArray[index].remove(nodeIndex);
        }
    }

    @Override
    public void clear() {
        entryNodeListArray = (MyLinkedList<EntryNode<K,V>>[]) new MyLinkedList<?>[START_CAPACITY];
        for (int i = 0; i < entryNodeListArray.length; ++i) {
            entryNodeListArray[i] = new MyLinkedList<>();
        }
        elementsCount = 0;
        threshold = (int) (START_CAPACITY * loadFactor);
    }

    @Override
    public int size() {
        return elementsCount;
    }

    public Set<K> getKeySet() {
        Set<K> keySet = new HashSet<>();
        for (MyList<EntryNode<K, V>> list : entryNodeListArray) {
            for (EntryNode<K, V> node : list) {
                keySet.add(node.getKey());
            }
        }
        return keySet;
    }

    public Set<V> getValueSet() {
        Set<V> valueSet = new HashSet<>();
        for (MyList<EntryNode<K, V>> list : entryNodeListArray) {
            for (EntryNode<K, V> node : list) {
                valueSet.add(node.getValue());
            }
        }
        return valueSet;
    }

    public Set<EntryNode<K, V>> getNodeSet() {
        Set<EntryNode<K, V>> nodeSet = new HashSet<>();
        for (MyList<EntryNode<K, V>> list : entryNodeListArray) {
            for (EntryNode<K, V> node : list) {
                nodeSet.add(new EntryNode<>(node.getKey(), node.getValue()));
            }
        }
        return nodeSet;
    }

    @Override
    public V get(K key){

        int hash = getHash(key);
        int index = hash % entryNodeListArray.length;

        for (EntryNode<K, V> node : entryNodeListArray[index]) {
            if (node.keyEquals(key)) {
                return node.getValue();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MyHashMap: { \n");

        int arrayIndex = 0;
        for (MyList<EntryNode<K, V>> list : entryNodeListArray) {
            sb.append("\t ").append(arrayIndex).append(" : {");

            for (EntryNode<K, V> node : list) {
                sb.append(" [").append(node.getKey()).append(" = ").append(node.getValue()).append("], ");
            }

            if (list.size() > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }

            sb.append(" },\n");
            arrayIndex++;
        }

        sb.delete(sb.length() - 2, sb.length()).append("\n}");
        return sb.toString();
    }

    public static class EntryNode<K, V> {

        private final K key;
        private final int hash;
        private V value;

        public EntryNode(K key, V value) {
            this.key = key;
            this.hash = MyHashMap.getHash(key);
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public int getHash() {
            return hash;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public boolean keyEquals(K key) {
            return MyHashMap.getHash(key) == getHash()
                    && (key == null ?
                    getKey() == null : key.equals(getKey()) );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EntryNode<K, V> e2 = (EntryNode<K, V>) o;
            return  (this.getKey()==null ?
                    e2.getKey()==null : this.getKey().equals(e2.getKey()))  &&
                    (this.getValue()==null ?
                            e2.getValue()==null : this.getValue().equals(e2.getValue()));
        }

        @Override
        public String toString() {
            return "EntryNode{" +
                    "key=" + key +
                    ", hash=" + hash +
                    ", value=" + value +
                    '}';
        }
    }
}

