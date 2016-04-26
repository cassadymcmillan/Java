import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Sheng Wang, Bolong Zhang
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /*
     * Test NaturalNumber3 constructor with empty arguments
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 constructor with integer arguments
     */
    @Test
    public final void testIntConstructor1() {
        /*
         * Set up variables and call method under test
         */
        int zero = 0;
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 constructor with integer arguments
     */
    @Test
    public final void testIntConstructor2() {
        /*
         * Set up variables and call method under test
         */
        int intMax = 2147483647;
        NaturalNumber n = this.constructorTest(intMax);
        NaturalNumber nExpected = this.constructorRef(intMax);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 constructor with string arguments
     */
    @Test
    public final void testStringConstructor1() {
        /*
         * Set up variables and call method under test
         */
        String zero = "0";
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 constructor with string arguments
     */
    @Test
    public final void testStringConstructor2() {
        /*
         * Set up variables and call method under test
         */
        String bigInt = "398765675434";
        NaturalNumber n = this.constructorTest(bigInt);
        NaturalNumber nExpected = this.constructorRef(bigInt);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 constructor with NaturalNumber arguments
     */
    @Test
    public final void testNNConstructor1() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 constructor with NaturalNumber arguments
     */
    @Test
    public final void testNNConstructor2() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber tf = this.constructorRef(35);
        NaturalNumber n = this.constructorTest(tf);
        NaturalNumber nExpected = this.constructorRef(tf);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 multiplyBy10 method with number 0
     */
    @Test
    public final void test1MultiplyBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.multiplyBy10(0);
        nExpected.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 multiplyBy10 method with number 43
     */
    @Test
    public final void test2MultiplyBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(43);
        NaturalNumber nExpected = this.constructorRef(43);
        n.multiplyBy10(0);
        nExpected.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 multiplyBy10 method with number 0 plus 9
     */
    @Test
    public final void test3MultiplyBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.multiplyBy10(9);
        nExpected.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 multiplyBy10 method with number 43 plus 9
     */
    @Test
    public final void test4MultiplyBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(43);
        NaturalNumber nExpected = this.constructorRef(43);
        n.multiplyBy10(9);
        nExpected.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 divideBy10 method with number 0
     */
    @Test
    public final void test1DivideBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        int result = n.divideBy10();
        int resultExpected = nExpected.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    /*
     * Test NaturalNumber3 divideBy10 method with one digit
     */
    @Test
    public final void test2DivideBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(9);
        NaturalNumber nExpected = this.constructorRef(9);
        int result = n.divideBy10();
        int resultExpected = nExpected.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    /*
     * Test NaturalNumber3 divideBy10 method with multiple digits
     */
    @Test
    public final void test3DivideBy10() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest(12345);
        NaturalNumber nExpected = this.constructorRef(12345);
        int result = n.divideBy10();
        int resultExpected = nExpected.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    /*
     * Test NaturalNumber3 isZero method : true
     */
    @Test
    public final void testIsZero1() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */

        assertEquals(true, n.isZero());
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 isZero method : false
     */
    @Test
    public final void testIsZero2() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, n.isZero());
        assertEquals(nExpected, n);
    }

    /*
     * Test NaturalNumber3 isZero method : true with no arguments constructor
     */
    @Test
    public final void testIsZero3() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, n.isZero());
        assertEquals(nExpected, n);
    }
}
