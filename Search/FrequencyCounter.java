package Search;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by hiro on 16-11-18.
 */
public class FrequencyCounter {
    public static void main(String[] args) {
        int minlen = Integer.parseInt(args[0]);
        BinarySearchST<String, Integer> st = new BinarySearchST<>(100);

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (word.length() < minlen) continue;

            if (!st.contains(word)) {
                st.put(word, 1);
            } else {
                st.put(word, st.get(word) + 1);
            }
        }

        System.out.println(st.max() + " " + st.get(st.max()));
    }
}
