import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hiro on 9/26/16.
 */
public class Quick {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }
    /*
    递归 sort 方法 每次寻找子数组的首部数的位置
    即：> it 再右   < it 在左
     */
    private static void sort(Comparable[] a, int low, int high) {
        if (low >= high) return;
        int j = partition(a, low, high);                //左右两个数组的分界线
        sort(a, low, j-1);
        sort(a, j+1, high);
    }

    private static int partition(Comparable[] a, int low, int high) {
        int i = low;
        int j = high + 1;                               //与后面的循环有关
        Comparable v = a[low];

        while (true) {
            while (less(a[++i], v)) if (i == high) break;//从第二个数开始比较，如果大，break
                                                         //如果是正序，知道结束退出
            while (less(v, a[--j])) if (j == low) break; //从最后一个数开始比较，如果小，break

            if (i >= j) break;                          //跳出循环的唯一方法：已找到首部数的位置
            exchange(a, i, j);                          //交换不正确的数的位置
        }
        exchange(a, low, j);                            //移动到正确位置
        return j;
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exchange(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void show(Comparable[] a) {
        for (int i=0; i<a.length; i++)
            System.out.println(a[i]);
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i=0; i<a.length; i++) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = args;
        sort(a);
        assert isSorted(a);
        show(a);
    }

}
