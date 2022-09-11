package collections.lists;

public interface MyList<E> extends Iterable<E>{
    void add(E value);
    void remove(int index);
    void clear();
    int size();
    E get(int index);
}
