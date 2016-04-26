import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Sheng Wang
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("0", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber m = new NaturalNumber2(21);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("3", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testReduceToGCD_56_63() {
        NaturalNumber n = new NaturalNumber2(56);
        NaturalNumber m = new NaturalNumber2(63);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("7", n.toString());
        assertEquals("0", m.toString());
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("0", n.toString());
        assertTrue(result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("1", n.toString());
        assertTrue(!result);
    }

    @Test
    public void testIsEven_100() {
        NaturalNumber n = new NaturalNumber2(100);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("100", n.toString());
        assertTrue(result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("1", n.toString());
        assertEquals("0", p.toString());
        assertEquals("2", m.toString());
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("1", n.toString());
        assertEquals("18", p.toString());
        assertEquals("19", m.toString());
    }

    @Test
    public void testPowerMod_23_9_43() {
        NaturalNumber n = new NaturalNumber2(23);
        NaturalNumber p = new NaturalNumber2(9);
        NaturalNumber m = new NaturalNumber2(43);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("35", n.toString());
        assertEquals("9", p.toString());
        assertEquals("43", m.toString());
    }

    @Test
    public void testPowerMod_999_19_29() {
        NaturalNumber n = new NaturalNumber2(999);
        NaturalNumber p = new NaturalNumber2(19);
        NaturalNumber m = new NaturalNumber2(29);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("6", n.toString());
        assertEquals("19", p.toString());
        assertEquals("29", m.toString());
    }

    /*
     * Tests of isWitnessToCompositeness
     */

    @Test
    public void testisWitnessToCompositeness_2_4() {
        NaturalNumber w = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals("2", w.toString());
        assertEquals("4", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisWitnessToCompositeness_35_224() {
        NaturalNumber w = new NaturalNumber2(5);
        NaturalNumber n = new NaturalNumber2(24);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals("5", w.toString());
        assertEquals("24", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisWitnessToCompositeness_3_6() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber n = new NaturalNumber2(6);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals("3", w.toString());
        assertEquals("6", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisWitnessToCompositeness_3_7() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber n = new NaturalNumber2(7);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals("3", w.toString());
        assertEquals("7", n.toString());
        assertTrue(!result);
    }

    /*
     * Tests of isPrime2
     */

    @Test
    public void testisPrime2_2() {
        NaturalNumber n = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals("2", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisPrime2_3() {
        NaturalNumber n = new NaturalNumber2(3);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals("3", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisPrime2_73() {
        NaturalNumber n = new NaturalNumber2(73);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals("73", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisPrime2_997() {
        NaturalNumber n = new NaturalNumber2(997);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals("997", n.toString());
        assertTrue(result);
    }

    @Test
    public void testisPrime2_4() {
        NaturalNumber n = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals("4", n.toString());
        assertTrue(!result);
    }

    @Test
    public void testisPrime2_875() {
        NaturalNumber n = new NaturalNumber2(875);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals("875", n.toString());
        assertTrue(!result);
    }

    /*
     * Tests of generateNextLikelyPrime
     */

    @Test
    public void testgenerateNextLikelyPrime_2() {
        NaturalNumber n = new NaturalNumber2(2);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("2", n.toString());
    }

    @Test
    public void testgenerateNextLikelyPrime_3() {
        NaturalNumber n = new NaturalNumber2(3);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("3", n.toString());
    }

    @Test
    public void testgenerateNextLikelyPrime_4() {
        NaturalNumber n = new NaturalNumber2(4);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("5", n.toString());
    }

    @Test
    public void testgenerateNextLikelyPrime_84() {
        NaturalNumber n = new NaturalNumber2(84);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("89", n.toString());
    }

    @Test
    public void testgenerateNextLikelyPrime_770() {
        NaturalNumber n = new NaturalNumber2(770);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("773", n.toString());
    }
}