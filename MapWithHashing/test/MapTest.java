import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Sheng Wang, Bolong Zhang
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert!map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert!map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOnePairConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testTwoPairsConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testMultiPairsConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "#", "2341");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "#", "2341");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testStrangePairsConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("132145ASDXq",
                "341CCSEFz", "def&#w[]", "456", "AAAA123", "====CBA");
        Map<String, String> mExpected = this.createFromArgsRef("132145ASDXq",
                "341CCSEFz", "def&#w[]", "456", "AAAA123", "====CBA");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddInToEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        m.add("1", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddIntoOnePair() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana");
        m.add("2", "banana");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddIntoManyPairs() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("132145ASDXq",
                "341CCSEFz", "def&#w[]", "456");
        Map<String, String> mExpected = this.createFromArgsRef("132145ASDXq",
                "341CCSEFz", "def&#w[]", "456", "AAAA123", "====CBA");
        m.add("AAAA123", "====CBA");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        Pair<String, String> p = m.remove("1");
        Pair<String, String> pExpected = mExpected.remove("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveLeavingOne() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana");
        Pair<String, String> p = m.remove("2");
        Pair<String, String> pExpected = mExpected.remove("2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveLeavingTwo() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "132145ASDXq", "341CCSEFz");
        Pair<String, String> p = m.remove("2");
        Pair<String, String> pExpected = mExpected.remove("2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyLeavingEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        Pair<String, String> p = m.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(p.key()));
        mExpected.remove(p.key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyLeavingOne() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana");
        Pair<String, String> p = m.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(p.key()));
        mExpected.remove(p.key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyLeavingTwo() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "132145ASDXq", "341CCSEFz");
        Pair<String, String> p = m.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(p.key()));
        mExpected.remove(p.key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testValueOnePair() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.value("1"), m.value("1"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testValueTwoPairs() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.value("2"), m.value("2"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testValueMultiPairs() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "132145ASDXq", "341CCSEFz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.value("2"), m.value("2"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyOnePairTrue() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.hasKey("1"), m.hasKey("1"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyOnePairFalse() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.hasKey("2"), m.hasKey("2"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyMultiPairsTrue() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "132145ASDXq", "341CCSEFz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.hasKey("132145ASDXq"), m.hasKey("132145ASDXq"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyMultiPairsFalse() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "132145ASDXq", "341CCSEFz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.hasKey("JSGKSR123"), m.hasKey("JSGKSR123"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptySize() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeOnePair() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeTwoPairs() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("2", "banana",
                "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("2", "banana",
                "132145ASDXq", "341CCSEFz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeMultiPairs() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("1", "apple", "2",
                "banana", "132145ASDXq", "341CCSEFz");
        Map<String, String> mExpected = this.createFromArgsRef("1", "apple",
                "2", "banana", "132145ASDXq", "341CCSEFz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }
}
