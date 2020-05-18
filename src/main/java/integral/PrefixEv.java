package integral;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.io.IOException;

import static java.lang.Math.*;

public class PrefixEv {

    private static List<String> unary_ops = Arrays.asList("exp", "sqrt", "sin", "cos", "tan", "log"),
            binary_ops = Arrays.asList("+", "-", "*", "/", "%", "^");

    /**
     * @param  strNum any possible string
     * @return true if the string is a number, false otherwise
     *
     * Precondition: none
     * Post-condition: none
     */
    static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    /**
     * @param S the operator
     * @return integer representing the operator's priority, higher number meaning higher priority
     *
     * Precondition: input string must be a valid operator
     * Post-condition: none, as the return values are hardcoded
     */
    static int getPriority(String S) {
        assert unary_ops.contains(S) || binary_ops.contains(S) || S.equals("(") || S.equals(")"): "S must be either a unary or binary operator";

        /*-----------------METHOD CODE------------------*/
        if (Objects.equals(S, "-") || Objects.equals(S, "+"))
            return 1;
        else if (Objects.equals(S, "*") || Objects.equals(S, "/") || Objects.equals(S, "%")
                || Objects.equals(S, "sin") || Objects.equals(S, "cos") || Objects.equals(S, "tan"))
            return 2;
        else if (Objects.equals(S, "^") || Objects.equals(S, "exp") || Objects.equals(S, "sqrt") || Objects.equals(S, "log"))
            return 3;
        return 0;
    }

    /**
     * @param infix equation in infix form, with all terms separated by space
     * @return string representing the equation in prefix form
     * @throws IOException invalid tokens in the equation
     *
     * Precondition: The terms of the input equation must be valid: '(', ')', number, operator or 'x'
     * Post-condition: The terms of the return equation must be valid: number or operator, 'x'
     */
    public static String infixToPrefix(@NotNull String infix) throws IOException {
        String[] terms = infix.split(" ");

        for (String term : terms) {
            if (!Objects.equals(term, "x") && !isNumeric(term)
                    && !unary_ops.contains(term) && !binary_ops.contains(term)
                    && !Objects.equals(term, "(") && !Objects.equals(term, ")"))
                throw new IOException("Invalid token in input equation: " + term);
        }

        /*-----------------METHOD CODE------------------*/
        Stack<String> operators = new Stack<>();
        Stack<String> operands = new Stack<>();
        String op1, op2, op, tmp, output;

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

        output = operands.peek();

        /*----------------POST-CONDITION-------------------*/
        for (String term : output.split(" ")) {
            if (!Objects.equals(term, "x") && !isNumeric(term)
                    && !unary_ops.contains(term) && !binary_ops.contains(term))
                throw new IOException("Invalid token in output equation: " + term);
        }

        return output;
    }

    /**
     * @param it a value that replaces 'x' in the equation
     * @param terms list of terms of the prefix equation
     * @return computational result of the equation
     * @throws IOException for when an operation cannot be completed
     *
     * Precondition: the terms of the input list to be valid: number, operator or 'x'
     * Post-condition: none
     */
    static Double f(double it, @NotNull List<String> terms) throws IOException{
        for (String term : terms) {
            if (!Objects.equals(term, "x") && !isNumeric(term)
                    && !unary_ops.contains(term) && !binary_ops.contains(term))
                throw new IOException("Invalid token in input list of terms: " + term);
        }

        /*-----------------METHOD CODE------------------*/
        Stack<Double> st = new Stack<>();

        for (String t : terms) {

            if (Objects.equals(t, "x"))
                st.push(it);

            else if (isNumeric(t))
                st.push(Double.parseDouble(t));

            else {
                double o1 = st.peek(), o2;
                st.pop();

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
                        if (o2 != 0)
                            st.push(o1 / o2);
                        else
                            throw new IOException("NaN");
                        break;
                    case "%":
                        o2 = st.peek();
                        st.pop();
                        if (o2 != 0)
                            st.push(o1 % o2);
                        else
                            throw new IOException("NaN");
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
                        if (o1 >= 0)
                            st.push(sqrt(o1));
                        else
                            throw new IOException("NaN");
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
                        if (o1 > 0)
                            st.push(log(o1));
                        else
                            throw new IOException("NaN");
                        break;
                }
            }
        }

        return st.peek();
    }

    /**
     * @param expr equation in infix form
     * @param low lower bound of the interval
     * @param high higher bound of the interval
     * @param step value to be added each iteration to the current iterator of the interval
     * @return list of coordinates which will be used to draw a graph
     * @throws IOException if input equation cannot be converted to prefix form
     *
     * Precondition: the interval bounds and step to be valid
     * Post-condition: none
     */
    public static List<Pair<Double, Double>> evaluate(String expr, double low, double high, double step) throws IOException {
        assert low < high && step <= (high-low);

        /*-----------------METHOD CODE------------------*/
        List<Pair<Double, Double>> res = new ArrayList<>();
        String prefix = infixToPrefix(expr);
        List<String> terms = Arrays.asList(prefix.split(" "));
        Double val;

        Collections.reverse(terms);
        for (double it = low; it <= high; it += step) {
            try{
                val = f(it, terms);
            }
            catch (IOException e){
                continue;
            }
            res.add(new Pair<>(it, val));
        }

        return res;
    }
}
