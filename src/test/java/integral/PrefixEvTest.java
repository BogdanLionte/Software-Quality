package integral;

import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PrefixEvTest {

    @Test
    public void isNumericTest(){
        assertFalse("Null is not numeric", PrefixEv.isNumeric(null));
        assertFalse("Empty string is not numeric", PrefixEv.isNumeric(""));
        assertFalse("Characters are not numeric", PrefixEv.isNumeric("a"));
        assertFalse("Characters are not numeric", PrefixEv.isNumeric("!"));
        assertTrue("Numbers are numeric", PrefixEv.isNumeric("1"));
        assertTrue("Numbers are numeric", PrefixEv.isNumeric("4.20"));
        assertTrue("Numbers are numeric", PrefixEv.isNumeric("-1"));
    }

    @Test
    public void getPriorityTest(){
        assertEquals("Parentheses have the lowest priority.", 0, PrefixEv.getPriority("("));
        assertEquals("Parentheses have the lowest priority.", 0, PrefixEv.getPriority(")"));

        assertEquals("Addition and subtraction have low priority.", 1, PrefixEv.getPriority("-"));
        assertEquals("Addition and subtraction have low priority.", 1, PrefixEv.getPriority("+"));

        assertEquals("Multiplication, division and modulo have middle priority.", 2, PrefixEv.getPriority("*"));
        assertEquals("Multiplication, division and modulo have middle priority.", 2, PrefixEv.getPriority("/"));
        assertEquals("Multiplication, division and modulo have middle priority.", 2, PrefixEv.getPriority("%"));

        assertEquals("Trigonometric functions also have middle priority.", 2, PrefixEv.getPriority("sin"));
        assertEquals("Trigonometric functions also have middle priority.", 2, PrefixEv.getPriority("cos"));
        assertEquals("Trigonometric functions also have middle priority.", 2, PrefixEv.getPriority("tan"));

        assertEquals("Power, exponentiation, root and logarithm have the highest priority.", 3, PrefixEv.getPriority("^"));
        assertEquals("Power, exponentiation, root and logarithm have the highest priority.", 3, PrefixEv.getPriority("exp"));
        assertEquals("Power, exponentiation, root and logarithm have the highest priority.", 3, PrefixEv.getPriority("sqrt"));
        assertEquals("Power, exponentiation, root and logarithm have the highest priority.", 3, PrefixEv.getPriority("log"));
    }

    @Test
    public void infixToPrefixTest(){
        try {
            assertEquals("Correct infix should result in the corresponding prefix.", "+ - ^ x 3 * 6 ^ x 2 + * 4 x 12", PrefixEv.infixToPrefix("( x ^ 3 - 6 * x ^ 2 ) + ( 4 * x + 12 )"));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        try{
            PrefixEv.infixToPrefix("y + 3");
            fail("Invalid token: y");
        } catch (IOException e){
            assertEquals("y", e.getMessage());
        }

        try{
            PrefixEv.infixToPrefix("! - 0");
            fail("Invalid token: !");
        } catch (IOException e){
            assertEquals("!", e.getMessage());
        }
    }

    @Test
    public void fTest(){
        List<String> v1 = Arrays.asList("/ * + x 1 5 2".split(" ")),
                v2 = Arrays.asList("- % 11 3 ^ x 2".split(" ")),
                v3 = Arrays.asList("* sqrt x ^ exp 1 log x".split(" ")),
                v4 = Arrays.asList("+ + sin x cos x tan x".split(" ")),
                f1 = Arrays.asList("log x".split(" ")),
                f2 = Arrays.asList("sqrt x".split(" ")),
                f3 = Arrays.asList("/ 1 x".split(" ")),
                f4 = Arrays.asList("% 1 x".split(" "));


        try { Collections.reverse(v1);
            assertEquals((Double)5.0, PrefixEv.f(1.0, v1), 1e-15);
        } catch (IOException e){ }


        try { Collections.reverse(v2);
            assertEquals((Double)(-2.41), PrefixEv.f(-2.1, v2), 1e-15);
        } catch (IOException e){ }


        try { Collections.reverse(v3);
            assertEquals((Double)8.0, PrefixEv.f(4.0, v3), 1e-15);
        } catch (IOException e){ }


        try { Collections.reverse(v4);
            assertEquals((Double)(-0.9914190316148560404665), PrefixEv.f(3.0, v4), 1e-15);
        } catch (IOException e){ }

        try {
            Collections.reverse(f1);
            PrefixEv.f(-1.0, f1);
            fail("Log of negative value is undefined");
        } catch (IOException e){
            assertEquals("NaN", e.getMessage());
        }

        try {
            Collections.reverse(f2);
            PrefixEv.f(-2.0, f2);
            fail("Root of negative value is not permitted");
        } catch (IOException e){
            assertEquals("NaN", e.getMessage());
        }

        try {
            Collections.reverse(f3);
            PrefixEv.f(0.0, f3);
            fail("Division by 0 is not permitted");
        } catch (IOException e){
            assertEquals("NaN", e.getMessage());
        }

        try {
            Collections.reverse(f4);
            PrefixEv.f(0.0, f4);
            fail("Modulo by 0 is not permitted");
        } catch (IOException e){
            assertEquals("NaN", e.getMessage());
        }
    }

    @Test
    public void evaluateTest(){
        Pair<Double, Double> p0 = new Pair<>(1.0, 2.0),
                p1 = new Pair<>(2.0, 1.0);

        try{
            List<Pair<Double, Double>> res = PrefixEv.evaluate("2 / x", 0.0, 2.0, 1.0);
            assertEquals(p0, res.get(0));
            assertEquals(p1, res.get(1));
        } catch(IOException e){ }

    }

}
