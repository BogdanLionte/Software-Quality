package integral;

import javafx.util.Pair;

import java.util.*;
import java.io.IOException;

import static java.lang.Math.*;

public class PrefixEv {

    private static List<String> ops = Arrays.asList("+", "-", "*", "/", "%", "^", "exp", "sqrt", "sin", "cos", "tan");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Double f(double it, List<String> terms) {
        Stack<Double> st = new Stack<Double>();

        for (String t : terms) {

            if (Objects.equals(t, "x"))
                st.push(it);

            else if (isNumeric(t))
                st.push(Double.parseDouble(t));

            else {
                double o1 = st.peek();
                st.pop();
                double o2;

                switch (t) {
                    case "+":
                        o2 = st.peek();
                        st.pop();
                        st.push(o1 + o2);
                        break;
                    case "-":
                        o2 = st.peek();
                        st.pop();
                        st.push(o1 - o2);
                        break;
                    case "*":
                        o2 = st.peek();
                        st.pop();
                        st.push(o1 * o2);
                        break;
                    case "/":
                        o2 = st.peek();
                        st.pop();
                        st.push(o1 / o2);
                        break;
                    case "%":
                        o2 = st.peek();
                        st.pop();
                        st.push(o1 % o2);
                        break;
                    case "^":
                        o2 = st.peek();
                        st.pop();
                        st.push(pow(o1, o2));
                        break;
                    case "exp":
                        st.push(exp(o1));
                        break;
                    case "sqrt":
                        st.push(sqrt(o1));
                        break;
                    case "sin":
                        st.push(sin(o1));
                        break;
                    case "cos":
                        st.push(cos(o1));
                        break;
                    case "tan":
                        st.push(tan(o1));
                        break;
                }
            }
        }

        return st.peek();
    }

    public static List<Pair<Double, Double>> evaluate(String expr, double low, double high, double step) throws IOException {
        List<Pair<Double, Double>> res = new ArrayList<>();
        List<String> terms = Arrays.asList(expr.split(" "));

        // check for invalid tokens
        for (String term : terms) {
            if (!Objects.equals(term, "x") && !isNumeric(term) && !ops.contains(term))
                throw new IOException(term);
        }

        Collections.reverse(terms);
        for (double it = low; it <= high; it += step) {
            double val = f(it, terms);
            res.add(new Pair<>(it, val));
        }

        return res;
    }

    public static void main(String[] args) {
        try {
            List<Pair<Double, Double>> t = evaluate("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", -1, 1, 0.1);
            System.out.println(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}