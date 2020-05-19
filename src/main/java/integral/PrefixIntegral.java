package integral;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrefixIntegral {

    /**
     * @param expr Input equation in prefix form
     * @param low lower bound of the interval
     * @param high higher bound of the interval
     * @param width division value of interval
     * @return List of integral approximations
     * @throws IOException Passes the caught exception forward.
     *
     * Precondition: the interval  values to be valid (expression to not have invalid tokens ??? -> already checked in called method)
     * Post-condition: none
     */
    public static List<Double> integral(String expr, double low, double high, double width) throws IOException{
        assert low < high && width <= (high-low) : "Interval not valid!";

        /*-----------------METHOD CODE------------------*/
        double sum_left = 0.0, sum_mid = 0.0, sum_right = 0.0, x_left, x_mid, x_right;
        List<String> terms = Arrays.asList(expr.split(" "));
        Collections.reverse(terms);

        for (x_left = low; x_left < high; x_left += width) {
            assert high - x_left > 0; // invariant

            x_right = x_left + width;
            x_mid = (x_right + x_left) / 2;

            sum_left = sum_left + width * PrefixEv.f(x_left, terms);
            sum_mid = sum_mid + width * PrefixEv.f(x_mid, terms);
            sum_right = sum_right + width * PrefixEv.f(x_right, terms);

        }

        return Arrays.asList(sum_left, sum_mid, sum_right);
    }
}
