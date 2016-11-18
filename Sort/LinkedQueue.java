package Sort;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by hiro on 16-9-16.
 */
public class LinkedQueue <Item> {

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private int N;
    private Node last;

    public boolean isEmpty () {
        return first == null;
    }

    public int size () { return N; }

    public void enqueue (Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        N ++;

        if (isEmpty()) first = last;
        else oldLast.next = last;
        last.next = null;
    }

    public Item dequeue () {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N --;
        return item;
    }

}
