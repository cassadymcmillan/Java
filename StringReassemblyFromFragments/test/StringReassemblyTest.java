import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    /*
     * Tests two strings Combination
     */
    @Test
    public void testCombination1() {
        String str1 = "My name is Sh";
        String str2 = "is Sheng Wang.";
        String result = StringReassembly.combination(str1, str2, 5);
        assertEquals("My name is Sheng Wang.", result);
    }

    /*
     * Tests two same strings Combination
     */
    @Test
    public void testCombination2() {
        String str1 = "Sheng Wang.";
        String str2 = "Sheng Wang.";
        String result = StringReassembly.combination(str1, str2, 11);
        assertEquals("Sheng Wang.", result);
    }

    /*
     * Tests two strings Combination, one's beginning is the other ending
     */
    @Test
    public void testCombination3() {
        String str1 = "Sheng Wang";
        String str2 = "Wang Sheng";
        String result = StringReassembly.combination(str1, str2, 4);
        assertEquals("Sheng Wang Sheng", result);
    }

    /*
     * Tests the set has substring of the incoming string
     */
    @Test
    public void testAddToSetAvoidingSubstrings1() {
        Set<String> strSet = new Set1L<String>();
        strSet.add("My name is Sheng Wang.");
        strSet.add("I want to sleep.");
        String str = "I feel tired and I want to sleep.";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);

        Set<String> expected = new Set1L<String>();
        expected.add("My name is Sheng Wang.");
        expected.add("I feel tired and I want to sleep.");

        assertEquals(expected, strSet);
    }

    /*
     * Tests the set has no substring of the incoming string
     */
    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> strSet = new Set1L<String>();
        strSet.add("My name is Sheng Wang.");
        String str = "I feel tired and I want to sleep.";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);

        Set<String> expected = new Set1L<String>();
        expected.add("My name is Sheng Wang.");
        expected.add("I feel tired and I want to sleep.");

        assertEquals(expected, strSet);
    }

    /*
     * Tests the incoming string is a substring of the set
     */
    @Test
    public void testAddToSetAvoidingSubstrings3() {
        Set<String> strSet = new Set1L<String>();
        strSet.add("My name is Sheng Wang.");
        strSet.add("I feel tired and I want to sleep.");
        String str = "I want to sleep.";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);

        Set<String> expected = new Set1L<String>();
        expected.add("My name is Sheng Wang.");
        expected.add("I feel tired and I want to sleep.");

        assertEquals(expected, strSet);
    }

    /*
     * Tests the file contains lines of substring
     */
    @Test
    public void testLinesFromInput1() {
        SimpleReader in = new SimpleReader1L("data/cheer-8-2.txt");
        Set<String> fragments = StringReassembly.linesFromInput(in);

        Set<String> expected = new Set1L<String>();
        expected.add("Bucks -- Beat");
        expected.add("Go Bucks");
        expected.add("o Bucks -- B");
        expected.add("Beat Mich");
        expected.add("Michigan~");

        in.close();
        assertEquals(expected, fragments);
    }

    /*
     * Tests the file does not contain lines of substring
     */
    @Test
    public void testLinesFromInput2() {
        SimpleWriter out = new SimpleWriter1L("data/testLines.txt");
        out.println("My name is");
        out.println("Sheng Wang.");
        out.println("I want to");
        out.println("sleep.");

        SimpleReader in = new SimpleReader1L("data/testLines.txt");
        Set<String> fragments = StringReassembly.linesFromInput(in);

        Set<String> expected = new Set1L<String>();
        expected.add("My name is");
        expected.add("Sheng Wang.");
        expected.add("I want to");
        expected.add("sleep.");

        in.close();
        out.close();
        assertEquals(expected, fragments);
    }

    /*
     * Tests whether all ~ are replaced to \n
     */
    @Test
    public void testPrintWithLineSeparators() {
        SimpleWriter out1 = new SimpleWriter1L("data/testSeparator1.txt");
        String text = "My name is Sheng Wang ~ I feel tired ~ I want to sleep.";
        StringReassembly.printWithLineSeparators(text, out1);

        SimpleWriter out2 = new SimpleWriter1L("data/testSeparator2.txt");
        String expectedText = "My name is Sheng Wang \n I feel tired \n I want to sleep.";
        out2.print(expectedText);

        SimpleReader in1 = new SimpleReader1L("data/testSeparator1.txt");
        SimpleReader in2 = new SimpleReader1L("data/testSeparator2.txt");
        String s1, s2;
        while (!in1.atEOS() && !in2.atEOS()) {
            s1 = in1.nextLine();
            s2 = in2.nextLine();
            assertEquals(s2, s1);
        }
        in1.close();
        in2.close();
        out1.close();
        out2.close();
    }
}
