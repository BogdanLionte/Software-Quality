package integral;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PrefixIntegralTest {

    @Test
    public void integralTest() {
        List<Double> results1 = PrefixIntegral.integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", -1, 1, 0.1);
        for(Double result : results1) {
            assertTrue("Integral result", 20 <= result && result <= 22);
        }

        List<Double> results2 = PrefixIntegral.integral("sin x", -1, 1, 0.1);
        for(Double result : results2) {
            assertTrue("Integral result", -1 <= result && result <= 1);
        }

        try {
            List<Double> results3 = PrefixIntegral.integral("x sin", -1, 1, 0.1);
            fail("Incorrect expression.");
        } catch (Exception e) {
            assertNull(e.getMessage());
        }

        try {
            List<Double> results4 = PrefixIntegral.integral("+ a b", -1, 1, 0.1);
            fail("Incorrect expression.");
        } catch (Exception e) {
            assertNull(e.getMessage());
        }

        List<Double> results5 = PrefixIntegral.integral("+ 1 x", -10, 10, 1);
        assertTrue("Integral result", 9 <= results5.get(0) && results5.get(0) <= 11);
        assertTrue("Integral result", 19 <= results5.get(1) && results5.get(1) <= 21);
        assertTrue("Integral result", 29 <= results5.get(2) && results5.get(2) <= 31);

        List<Double> results6 = PrefixIntegral.integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", -1, 1, 100);
        assertNull(results6);

        List<Double> results7 = PrefixIntegral.integral("+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", 1, -1, 0.1);
        assertNull(results7);
    }
}
