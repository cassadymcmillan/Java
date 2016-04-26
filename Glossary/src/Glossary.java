import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Output several web pages of glossary which is a list of difficult or
 * specialized words, or terms, with their definitions, that is usually near the
 * end of a book for facility from an input file.
 *
 * @author Sheng Wang
 */
public final class Glossary {

    /**
     * Private no-argument constructor to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Generates the Map of terms and definitions in the given
     * {@code SimpleReader} into the given (@code Map}.
     *
     * @param in
     *            the given {@code SimpleReader}
     *
     * @ensures
     *
     *          <pre>
     * {@code Map = entries(in)}
     *          </pre>
     *
     * @return map is a Map containing the terms and definitions.
     */
    public static Map<String, String> generateMap(SimpleReader in) {
        Queue<String> queue = new Queue1L<>();
        Map<String, String> map = new Map1L<>();
        // Check whether it is the end of the file
        while (!in.atEOS()) {
            String temp = in.nextLine();
            queue.enqueue(temp);
            temp = in.nextLine();
            String def = "";
            // Check whether it is an empty line
            while (!temp.isEmpty()) {
                def += temp;
                // Check whether it is the end of the file
                if (!in.atEOS()) {
                    // Read the next line
                    temp = in.nextLine();
                } else {
                    temp = "";
                }
            }
            queue.enqueue(def);
        }
        while (queue.length() > 0) {
            // Get the term
            String term = queue.dequeue();
            // Get the definition
            String def = queue.dequeue();
            map.add(term, def);
        }
        return map;
    }

    /**
     * Generates the Queue of terms in the given {@code Map} into the given
     * (@code Queue}.
     *
     * @param m
     *            the given {@code Map}
     *
     * @ensures
     *
     *          <pre>
     * {@code q = entries(in)}
     *          </pre>
     *
     * @return q is a Queue only containing the terms.
     */
    public static Queue<String> generateTermQueue(Map<String, String> m) {
        Queue<String> q = new Queue1L<>();
        // Copy each term into the queue
        for (Pair<String, String> i : m) {
            q.enqueue(i.key());
        }
        return q;
    }

    /**
     * Removes and returns the minimum value from {@code Queue}.
     *
     * @param q
     *            the given queue
     * @updates {@code q}
     * @requires
     *
     *           <pre>
     *           {@code q /= empty_string
     * </pre>
     * @ensures
     *
     *          <pre>
     *          {@code q is permutation of #q  and
     *  for all x: string of character
     *      where (x is in entries (q))
     * </pre>
     * @return the minimum value from {@code q}
     */
    public static String removeMin(Queue<String> q) {
        String min = q.front();
        // Fine the minimum alphabetized term
        for (String i : q) {
            if (min.compareTo(i) > 0) {
                min = i;
            }
        }
        // Remove the minimum alphabetized term
        while (!q.front().equals(min)) {
            q.rotate(1);
        }
        q.dequeue();
        return min;
    }

    /**
     * Sort {@code Queue} alphabetized and returns it as {@code Queue}.
     *
     * @param q
     *            the Queue to be sorted
     *
     * @requires
     *
     *           {@code q /= empty_string
     * @ensures
     *
     *          <pre>
     *          {@code (q * <removeMin>) is permutation of #q  and
     *  for all x: string of character
     *      where (x is in entries (q))
     * </pre>
     * @return a sorted queue from {@code q}
     */
    private static Queue<String> sort(Queue<String> q) {
        Queue<String> sortQueue = new Queue1L<>();
        // Sort the given term queue
        while (q.length() > 0) {
            sortQueue.enqueue(removeMin(q));
        }
        return sortQueue;
    }

    /**
     * Generate a Set of terms in the given {@code Queue} into the given (@code
     * Set}.
     *
     * @param q
     *            the given queue
     * @requires
     *
     *           <pre>
     *           {@code Queue /= null }
     *           </pre>
     *
     * @ensures
     *
     *          <pre>
     *          {@code Queue} = {@code Set}
     *          </pre>
     *
     * @return a set containing the same terms from {@code q}
     */
    public static Set<String> generateSet(Queue<String> q) {
        Set<String> set = new Set1L<>();
        // Copy each term from the queue into the set
        for (String i : q) {
            set.add(i);
        }
        return set;
    }

    /**
     * Prints the index page in a HTML file.
     *
     * @param queue
     *            the Queue containing the terms
     * @requires
     *
     *           <pre>
     *           {@code Queue /= null}
     *           </pre>
     *
     * @ensures
     *
     *          <pre>
     *          {@code outFile.content * [the HTML tags]}
     *          </pre>
     */
    private static void printIndex(Queue<String> queue) {
        // Prints in a folder
        SimpleWriter outFile = new SimpleWriter1L("HTML/index.html");
        // Prints the title
        outFile.print("<html>\n" + "<head>\n" + "<title>Glossary</title>\n"
                + "</head>\n");
        // Prints the header
        outFile.print("<body>\n" + "<h1>Glossary</h1>\n" + "<hr />"
                + "<h2>Index</h2>\n");
        // Prints the terms in a list
        outFile.print("<ul>\n");
        for (int i = 0; i < queue.length(); i++) {
            String temp = queue.dequeue();
            // Prints terms with links
            outFile.print("<li>\n" + "<a href=\"" + temp + ".html\">" + temp
                    + "</a>\n" + "</li>\n");
            queue.enqueue(temp);
        }
        outFile.print("</ul>\n" + "</body>\n" + "</html>\n");
        outFile.close();
    }

    /**
     * Prints the definition page in each HTML file.
     *
     * @param term
     *            the term to be defined
     * @param map
     *            the Map containing the definitions and terms
     * @param set
     *            the set for finding repeated terms to be defined
     * @requires
     *
     *           <pre>
     *           {@code Set /= null}
     *           </pre>
     *
     * @ensures
     *
     *          <pre>
     *          {@code outFile.content * [the HTML tags]}
     *          </pre>
     */
    private static void printDefPage(String term, Set<String> set,
            Map<String, String> map) {
        SimpleWriter outFile = new SimpleWriter1L("HTML/" + term + ".html");
        // Prints the title
        outFile.print("<html>\n" + "<head>\n" + "<title>" + term + "</title>\n"
                + "</head>\n");
        // Prints the header
        outFile.print("<body>\n" + "<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>\n");
        // Prints definition in a quote form
        outFile.print("<blockquote>");
        String temp = map.value(term);
        Queue<String> definition = separatorDef(temp);
        while (definition.length() > 0) {
            temp = definition.dequeue();
            // Check whether the words in the set
            if (set.contains(temp)) {
                outFile.print(
                        "<a href=\"" + temp + ".html\">" + temp + " </a>");
            } else {
                outFile.print(temp + " ");
            }
        }
        outFile.print("</blockquote>\n" + "<hr>\n");
        // Prints return line
        outFile.print("<p>\n" + "Return to <a href=\"index.html\">index</a>.\n"
                + "</p>\n" + "</body>\n" + "</html>");
        outFile.close();
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        for (int i = 0; str.length() > i; i++) {
            char c = str.charAt(i);
            // Puts letters in a set
            if (!strSet.contains(c)) {
                strSet.add(c);
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures
     *
     *          <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     *          </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        boolean isSeparator = false;
        char c = text.charAt(position);
        String string = "";
        if (separators.contains(c)) {
            isSeparator = true;
        }
        while ((isSeparator) && (separators.contains(c))
                && (position < text.length())) {
            string = string + c;
            position++;
            if (position < text.length()) {
                c = text.charAt(position);
            }
        }
        while ((!isSeparator) && (!separators.contains(c))
                && (position < text.length())) {
            string += c;
            position++;
            if (position < text.length()) {
                c = text.charAt(position);
            }
        }
        return string;
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param definition
     *            the definition separated into a Queue
     * @param str
     *            the definition as a String
     *
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires
     *
     *           <pre>
     * {@code 0 <= position < |text|}
     *           </pre>
     *
     * @ensures
     *
     *          <pre>
     * {@code nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)}
     *          </pre>
     */
    public static Queue<String> separatorDef(String text) {
        Queue<String> def = new Queue1L<>();
        String separator = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separator, separatorSet);
        int position = 0;
        while (position < text.length()) {
            String s = nextWordOrSeparator(text, position, separatorSet);
            if (!separatorSet.contains(s.charAt(0))) {
                def.enqueue(s);
            }
            position += s.length();
        }
        return def;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        // Get the file
        out.print("Input file: ");
        String fileName = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(fileName);
        // Get the map
        Map<String, String> glossaryMap = generateMap(inFile);
        // Get the term queue
        Queue<String> termQueue = generateTermQueue(glossaryMap);
        // Get the sorted term queue
        Queue<String> sortTermQueue = sort(termQueue);
        // Prints the index
        printIndex(sortTermQueue);
        Set<String> termSet = generateSet(sortTermQueue);
        // Prints the definition pages
        while (sortTermQueue.length() > 0) {
            String term = sortTermQueue.dequeue();
            printDefPage(term, termSet, glossaryMap);
        }

        in.close();
        out.close();
    }
}
