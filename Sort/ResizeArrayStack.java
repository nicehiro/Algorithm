package Sort;

import java.util.Iterator;

/**
 * Created by hiro on 16-9-16.
 */
public class ResizeArrayStack <Item>{

    private Item[] a;
    private int N;

    public ResizeArrayStack(int cap) {
        a = (Item[]) new Object[cap];
    }

    public boolean isEmpty () {
        return N == 0;
    }

    public int size() {
        return a.length;
    }

    public void push (Item i) {
        if (N == a.length) resize(2 * a.length);
        a[N++] = i;
    }

    public Item pop () {
        if (N < a.length / 4) resize(a.length / 2);
        Item temp = a[-- N];
        a[N] = null;
        return temp;
    }

    public void resize (int max) {
        Item[] temp = (Item[]) new Object[max];

        for (int i=0; i<N; i++) {
            temp[i] = a[i];
        }

        a = temp;
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public class ReverseArrayIterator implements Iterator<Item> {
        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return a[-- i];
        }

        public void remove() {}
    }

}
