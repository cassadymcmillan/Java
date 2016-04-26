import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Sheng Wang, Bolong Zhang
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert!set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert!set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("123");
        s.add("123");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToOne() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("abc");
        Set<String> sExpected = this.createFromArgsRef("123", "abc");
        s.add("123");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToMany() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("abc", "4de", "5fg");
        Set<String> sExpected = this.createFromArgsRef("123", "abc", "4de",
                "5fg");
        s.add("123");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddUpperCaseLetter() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("abc", "4de", "5fg");
        Set<String> sExpected = this.createFromArgsRef("XYZ", "abc", "4de",
                "5fg");
        s.add("XYZ");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToStrange() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("a%g", "4d!", "~~fg");
        Set<String> sExpected = this.createFromArgsRef("12#", "a%g", "4d!",
                "~~fg");
        s.add("12#");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1");
        String removedTest = s.remove("1");
        String removedRef = sExpected.remove("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(removedRef, removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveLeavingOne() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "A");
        Set<String> sExpected = this.createFromArgsRef("1", "A");
        String removedTest = s.remove("A");
        String removedRef = sExpected.remove("A");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(removedRef, removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveLeavingMany() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "A.?{}||&&",
                "1234456&^@#!FDRwe");
        Set<String> sExpected = this.createFromArgsRef("1", "A.?{}||&&",
                "1234456&^@#!FDRwe");
        String removedTest = s.remove("1234456&^@#!FDRwe");
        String removedRef = sExpected.remove("1234456&^@#!FDRwe");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(removedRef, removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyLeavingEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1");
        String removedTest = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removedTest));
        /*
         * Assert that values of variables match expectations
         */
        sExpected.remove(removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyLeavingOne() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "1234456&^@#!FDRwe");
        Set<String> sExpected = this.createFromArgsRef("1",
                "1234456&^@#!FDRwe");
        String removedTest = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removedTest));
        /*
         * Assert that values of variables match expectations
         */
        sExpected.remove(removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyLeavingMany() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "A.?{}||&&",
                "1234456&^@#!FDRwe", "23RRagf");
        Set<String> sExpected = this.createFromArgsRef("1", "A.?{}||&&",
                "1234456&^@#!FDRwe", "23RRagf");
        String removedTest = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removedTest));
        /*
         * Assert that values of variables match expectations
         */
        sExpected.remove(removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyLeavingTwentyFive() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z");
        String removedTest = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removedTest));
        /*
         * Assert that values of variables match expectations
         */
        sExpected.remove(removedTest);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsInOneStringSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("1"), s.contains("1"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsNotInOneStringSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("2"), s.contains("2"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsWithEmptySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("1"), s.contains("1"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsInTwoStringSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "AbC32!@#");
        Set<String> sExpected = this.createFromArgsRef("1", "AbC32!@#");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("1"), s.contains("1"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsNotInTwoStringSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "AbC32!@#");
        Set<String> sExpected = this.createFromArgsRef("1", "AbC32!@#");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("ASDF!@#$7890"),
                s.contains("ASDF!@#$7890"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsInManyStringSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("a"), s.contains("a"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsNotInManyStringSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.contains("Z"), s.contains("Z"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeOne() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeZero() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeTwo() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("1", "AbC32!@#");
        Set<String> sExpected = this.createFromArgsRef("1", "AbC32!@#");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeMany() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }
}
