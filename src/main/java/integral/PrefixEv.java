package integral;

import javafx.util.Pair;

import java.util.*;
import java.io.IOException;

import static java.lang.Math.*;

public class PrefixEv {

    private static List<String> unary_ops = Arrays.asList("exp", "sqrt", "sin", "cos", "tan", "log"),
            binary_ops = Arrays.asList("+", "-", "*", "/", "%", "^");

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

    public static int getPriority(String S) {
        if (Objects.equals(S, "-") || Objects.equals(S, "+"))
            return 1;
        else if (Objects.equals(S, "*") || Objects.equals(S, "/") || Objects.equals(S, "%")
                || Objects.equals(S, "sin") || Objects.equals(S, "cos") || Objects.equals(S, "tan") || Objects.equals(S, "log"))
            return 2;
        else if (Objects.equals(S, "^") || Objects.equals(S, "exp") || Objects.equals(S, "sqrt"))
            return 3;
        return 0;
    }

    public static String infixToPrefix(String infix) throws IOException {
        List<String> terms = Arrays.asList(infix.split(" "));
        Stack<String> operators = new Stack<String>();
        Stack<String> operands = new Stack<String>();
        String op1, op2, op, tmp;

        // check for invalid tokens
        for (String term : terms) {
            if (!Objects.equals(term, "x") && !isNumeric(term)
                    && !unary_ops.contains(term) && !binary_ops.contains(term)
                    && !Objects.equals(term, "(") && !Objects.equals(term, ")"))
                throw new IOException(term);
        }

        for (String term : terms) {

            if (Objects.equals(term, "("))
                operators.push(term);

            else if (Objects.equals(term, ")")) {

                while (!operators.empty() && !Objects.equals(operators.peek(), "(")) {

                    op1 = operands.peek();
                    operands.pop();

                    op = operators.peek();
                    operators.pop();

                    tmp = op + " ";

                    if (binary_ops.contains(op)) {
                        op2 = operands.peek();
                        operands.pop();
                        tmp += op2 + " ";
                    }

                    tmp += op1;
                    operands.push(tmp);
                }
                operators.pop();

            } else if (isNumeric(term) || Objects.equals(term, "x"))
                operands.push(term);

            else {
                while (!operators.empty() && getPriority(term) <= getPriority(operators.peek())) {

                    op1 = operands.peek();
                    operands.pop();

                    op = operators.peek();
                    operators.pop();

                    tmp = op + " ";

                    if (binary_ops.contains(op)) {
                        op2 = operands.peek();
                        operands.pop();
                        tmp += op2 + " ";
                    }

                    tmp += op1;
                    operands.push(tmp);
                }

                operators.push(term);
            }
        }

        while (!operators.empty()) {
            op1 = operands.peek();
            operands.pop();

            op = operators.peek();
            operators.pop();

            tmp = op + " ";

            if (binary_ops.contains(op)) {
                op2 = operands.peek();
                operands.pop();
                tmp += op2 + " ";
            }

            tmp += op1;
            operands.push(tmp);
        }

        return operands.peek();
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
                    case "log":
                        st.push(log(o1));
                        break;
                }
            }
        }

        return st.peek();
    }

    public static List<Pair<Double, Double>> evaluate(String expr, double low, double high, double step) throws IOException {
        List<Pair<Double, Double>> res = new ArrayList<>();
        String prefix = infixToPrefix(expr);
        List<String> terms = Arrays.asList(prefix.split(" "));

        Collections.reverse(terms);
        for (double it = low; it <= high; it += step) {
            double val = f(it, terms);
            res.add(new Pair<>(it, val));
        }

        return res;
    }

    public static void main(String[] args) {

        try {
            System.out.println(infixToPrefix("( x ^ 3 - 6 * x ^ 2 ) + ( 4 * x + 12 )"));
            System.out.println(evaluate("( x ^ 3 - 6 * x ^ 2 ) + ( 4 * x + 12 )", -1, 1, 0.1));
            System.out.println(evaluate("exp ( x )", -1, 1, 0.1));
            System.out.println(evaluate("sqrt ( x )", 0, 2, 0.1));
            System.out.println(evaluate("log ( x )", 0, 2, 0.1));
            System.out.println(evaluate("sin ( x )", -1, 1, 0.1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}