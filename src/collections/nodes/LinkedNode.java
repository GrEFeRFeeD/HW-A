package collections.nodes;

public class LinkedNode<E> {

    protected E value;
    protected LinkedNode<E> next;

    public LinkedNode(E value, LinkedNode<E> next) {
        this.value = value;
        this.next = next;
    }

    public LinkedNode(E value) {
        this(value, null);
    }

    public LinkedNode() {
        this(null, null);
    }

    public E getValue() {
        return value;
    }

    public LinkedNode<E> getNext() {
        return next;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setNext(LinkedNode<E> next) {
        this.next = next;
    }

    public boolean hasNext() {
        return next != null;
    }

}
