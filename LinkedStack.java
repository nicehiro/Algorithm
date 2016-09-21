import java.util.Iterator;

/**
 * Created by hiro on 16-9-16.
 */
public class LinkedStack <Item> implements Iterator<Item>{

    @Override
    public boolean hasNext() {
        return first == null;
    }

    @Override
    public Item next() {
        Item i = first.item;
        first = first.next;
        return i;
    }

    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private int N;

    public boolean isEmpty() { return first == null; }

    public int size () {
        return N;
    }

    public void push (Item i) {
        Node oldFirst = first;
        first = new Node();
        first.item = i;
        first.next = oldFirst;
        N ++;
    }

    public Item pop () {
        Item item = first.item;
        first = first.next;
        N --;
        return item;
    }

}
