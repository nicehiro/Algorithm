/**
 * Created by hiro on 16-9-15.
 */
public class StringTest {

    public static boolean isPalindrome(String s) {
        int N = s.length();
        for(int i=0; i<N/2; i++) {
            if(s.charAt(i) != s.charAt(N-1-i))
                return false;
        }
        return true;
    }

    public static void extension(String s) {
        int dot = s.indexOf(".");
        String base = s.substring(0, dot);
        String extension = s.substring(dot+1, s.length());

        myPrint(base);
        myPrint(extension);
    }

    public static void sprintSame(String same, String test) {
        if(test.contains(same)) {
            myPrint(test);
        }
    }

    public static boolean isSorted(String[] a) {
        for(int i=1; i<=a.length; i++) {
            if (a[i-1].compareTo(a[i]) > 0)
                return false;
        }
        return true;
    }

    public static void myPrint(String s) {
        System.out.println(s);
    }

    public static void main(String args[]) {

    }

}
