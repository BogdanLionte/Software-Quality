package integral;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrefixIntegral {

    public static List<Double> integral(String expr, double low, double high, double width) {
        List<String> terms = Arrays.asList(expr.split(" "));
        Collections.reverse(terms);
        double sum_left = 0.0, sum_mid = 0.0, sum_right = 0.0;

        if(width > high - low || low > high) return null;

        for (double x_left = low; x_left < high; x_left = x_left + width) {
            double x_right = x_left + width;
            double x_mid = (x_right + x_left) / 2;

            try {
                sum_left = sum_left + width * PrefixEv.f(x_left, terms);
                sum_mid = sum_mid + width * PrefixEv.f(x_mid, terms);
                sum_right = sum_right + width * PrefixEv.f(x_right, terms);
            } catch (IOException e) {
                return null;
            }
        }

        return Arrays.asList(sum_left, sum_mid, sum_right);
    }

//    public static void main(String[] args) {
//
//        List<Double> i = integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", -1, 1, 0.1);
//        System.out.println(i);
//
//        i = integral("sin x", -1, 1, 0.1);
//        System.out.println(i);
//
//
//    }
}
