import java.util.Scanner;
import java.util.Stack;

/**
 * Created by hiro on 16-9-16.
 */
public class Evaluate {
    public static void main(String[] args) {
        Stack<String> opraters = new Stack<String>();
        Stack<Double> values = new Stack<Double>();

        Scanner in = new Scanner(System.in);

        System.out.println("Please Input a Evaluate:");

        while (in.hasNext()) {
            String s = in.next();

            System.out.println("haha");

            if(s.equals("("));
            else if(s.equals("+"))
                opraters.push(s);
            else if(s.equals("-"))
                opraters.push(s);
            else if(s.equals("*"))
                opraters.push(s);
            else if(s.equals("/"))
                opraters.push(s);
            else if (s.equals("sqrt"))
                opraters.push(s);

            else if (s.equals(")")) {

                String op = opraters.pop();
                double value = values.pop();

                if (op.equals("+"))
                    value = values.pop() + value;
                else if (op.equals("-"))
                    value = values.pop() - value;
                else if (op.equals("*"))
                    value = values.pop() * value;
                else if (op.equals("/"))
                    value = values.pop() / value;
                else if (op.equals("sqrt"))
                    value = Math.sqrt(value);

                values.push(value);

                if (values.size() == 1) {
                    break;
                }

            } else {
                values.push(Double.parseDouble(s));
            }
        }
        System.out.println("haha");
        System.out.println(values.pop());
    }
}
