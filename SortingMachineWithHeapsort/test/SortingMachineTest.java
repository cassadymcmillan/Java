import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Sheng Wang, Bolong Zhang
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures
     *
     *          <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     *          </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures
     *
     *          <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     *          </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    // Test cases for constructor()
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    // Test cases for add()
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddOneSame() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc");
        m.add("abc");
        mExpected.add("abc");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddTwo() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "DF$");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "DF$", "123");
        m.add("123");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddTwoSame() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "DF$");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "DF$");
        m.add("DF$");
        mExpected.add("DF$");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D", "DF$");
        m.add("DF$");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddManySame() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        m.add("123");
        mExpected.add("123");

        assertEquals(mExpected, m);
    }

    // Test cases for changeToExtractionMode()
    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeSingleEle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeDuoEle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeMultiEle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45", "!@#fe34DF>*");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45", "!@#fe34DF>*");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    // Test cases for removeFirst()
    @Test
    public final void testRemoveFirstLeavingEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected.removeFirst(), m.removeFirst());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstLeavingOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected.removeFirst(), m.removeFirst());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstLeavingTwo() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected.removeFirst(), m.removeFirst());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstLeavingMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected.removeFirst(), m.removeFirst());
        assertEquals(mExpected, m);
    }

    // Test cases for IsInInsertionMode
    @Test
    public final void testIsInInsertionModeEmptyFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeOneFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTwoFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc",
                "123");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc", "123");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeManyFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeOneTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTwoTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeManyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    // Test cases for order()
    @Test
    public final void test() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        assertEquals(ORDER, m.order());
    }

    // Test cases for size()
    @Test
    public final void testSizeInsertionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeInsertionModdOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc");

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeInsertionModeTwo() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123");

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeInsertionModeMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D");

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeExtractionModeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc");

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeExtractionModeTwo() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc",
                "123");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc", "123");

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeExtractionModeMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc",
                "123", "ac34bg45", "!@#dsf45SD^&", "123454D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc", "123", "ac34bg45", "!@#dsf45SD^&", "123454D");

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }
}
