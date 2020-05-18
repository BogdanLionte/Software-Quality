package integral;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import static org.junit.Assert.*;

public class PrefixIntegralTest {

    @Test
    public void integralTest() {
        List<Double> res1, res2, res3;

        try {
            res1 = PrefixIntegral.integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", -1, 1, 0.1);
            for (Double result : res1) {
                assertTrue("Integral result", 20 <= result && result <= 22);
            }
        } catch (IOException e) {
        }

        try {
            res2 = PrefixIntegral.integral("sin x", -1, 1, 0.1);
            for (Double result : res2) {
                assertTrue("Integral result", -1 <= result && result <= 1);
            }
        } catch (IOException e) {
        }

        try {
            res3 = PrefixIntegral.integral("+ 1 x", -10, 10, 1);
            assertTrue("Integral result", 9 <= res3.get(0) && res3.get(0) <= 11);
            assertTrue("Integral result", 19 <= res3.get(1) && res3.get(1) <= 21);
            assertTrue("Integral result", 29 <= res3.get(2) && res3.get(2) <= 31);
        } catch (IOException e) {
        }

        try {
            PrefixIntegral.integral("x sin", -1, 1, 0.1);
            fail("Incorrect expression.");
        } catch (EmptyStackException | IOException e) {
            System.out.println("Flawed logic in input equation: x sin");
        }

        try {
            PrefixIntegral.integral("+ a b", -1, 1, 0.1);
            fail("Incorrect expression.");
        } catch (IOException e) {
            assertEquals("Invalid token in input list of terms: b", e.getMessage());
        }

        try {
            PrefixIntegral.integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", -1, 1, 100);
            fail("Invalid interval");
        } catch (AssertionError | IOException e) {
            assertEquals("Interval not valid!", e.getMessage());
        }

        try {
            PrefixIntegral.integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", 1, -1, 0.1);
            fail("Invalid interval");
        } catch (AssertionError | IOException e) {
            assertEquals("Interval not valid!", e.getMessage());
        }

        try {
            PrefixIntegral.integral("/ 1 x", 0, 1, 0.1);
            fail("Illegal division by 0!");
        } catch (IOException e) {
            assertEquals("NaN", e.getMessage());
        }
    }
}
