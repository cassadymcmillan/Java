import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program counts word occurrences in a given input file and outputs an
 * HTML document with a table of the words and counts listed in alphabetical
 * order.
 *
 * @author Sheng Wang
 */
public final class WordCounter {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private WordCounter() {

    }

    /**
     * Read from {@code SimpleReader} line by line.
     *
     * @param inFile
     *            the file to read from
     * @return queue that contains the string of every line.
     */
    public static Queue<String> getLineFromFile(SimpleReader inFile) {
        Queue<String> queue = new Queue1L<String>();
        // Check if it is the end of the stream
        while (!inFile.atEOS()) {
            String s = inFile.nextLine();
            // Check if the string is empty
            if (!s.isEmpty()) {
                queue.enqueue(s);
            }
        }
        return queue;
    }

    /**
     * Get words from {@code Queue<String>}.
     *
     * @param queue
     *            the queue of Strings to split
     * @return the queue that contains every word of the string.
     */
    public static Queue<String> getWordFromLine(Queue<String> queue) {
        Queue<String> wordQueue = new Queue1L<String>();
        // Check if it is empty
        while (queue.length() > 0) {
            String line = queue.dequeue();
            // Split the line according to the separators
            String word[] = line.split("[,+-.;?! ]");
            // Add all words into a queue
            for (String i : word) {
                if (!i.isEmpty()) {
                    wordQueue.enqueue(i);
                }
            }
        }
        return wordQueue;
    }

    /**
     * Create the map of the words and their corresponding occurrences from
     * {@code Queue<String>}.
     *
     * @param wordQueue
     *            the queue of the words
     * @return map that contains words and their occurrences.
     */
    public static Map<String, Integer> getWordMap(Queue<String> wordQueue) {
        Map<String, Integer> wordMap = new Map1L<String, Integer>();
        for (String i : wordQueue) {
            // Check if the map has the word
            if (!wordMap.hasKey(i)) {
                // Add the word into the map
                wordMap.add(i, 1);
            } else {
                // Increase the value of the exited word
                int value = wordMap.value(i);
                value++;
                wordMap.replaceValue(i, value);
            }
        }
        return wordMap;
    }

    /**
     * Find and remove the min of {@code Queue<String>}.
     *
     * @param q
     *            the q of words to sort
     * @return the min word.
     */
    public static String removeMin(Queue<String> q) {
        String min = q.front();
        // Fine the minimum alphabetized term
        for (String i : q) {
            if (min.compareToIgnoreCase(i) > 0) {
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
     * Sort words alphabetically.
     *
     * @param wordMap
     *            the map that contains all elements need to be sorted
     * @return the queue with sorted elements
     */
    public static Queue<String> sort(Map<String, Integer> wordMap) {
        Queue<String> queue = new Queue1L<String>();
        Queue<String> sortedqQueue = new Queue1L<String>();
        // Copy the words of the map into the queue
        for (Pair<String, Integer> i : wordMap) {
            queue.enqueue(i.key());
        }
        // Sort the queue
        while (queue.length() > 0) {
            sortedqQueue.enqueue(removeMin(queue));
        }
        return sortedqQueue;
    }

    /**
     * Print the html page named with {@code String}.
     *
     * @param fileName
     *            the name of the output file
     * @param outFileName
     *            the name of the output file
     * @param q
     *            the queue contains all words needed to be printed
     * @param map
     *            the map with the words and occurrences
     */
    public static void printPage(Queue<String> q, Map<String, Integer> map,
            String fileName, String outFileName) {
        SimpleWriter outFile = new SimpleWriter1L(outFileName);
        // Print the heading
        outFile.print("<html>\n");
        outFile.print("<head>\n");
        outFile.print(
                "<title>Words Counted in data/" + fileName + "</title>\n");
        outFile.print("</head>\n");
        // Print the body
        outFile.print("<body>\n");
        outFile.print("<h2>Words Counted in data/" + fileName + "</h2>\n");
        outFile.print("<hr>\n");
        outFile.print("<table border=\"1\">\n");
        outFile.print("<tr>\n");
        outFile.print("<th>Words</th>\n");
        outFile.print("<th>Counts</th>\n");
        outFile.print("</tr>\n");
        // Use a for loop to print each word and occurrence
        for (String i : q) {
            outFile.print("<tr>\n");
            outFile.print("<td>" + i + "</td>\n");
            outFile.print("<td>" + map.value(i) + "</td>\n");
            outFile.print("</tr>\n");
        }
        outFile.print("</table>\n");
        outFile.print("</body>\n");
        outFile.print("</html>");
        outFile.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        //Prompt the user to enter the input file name
        out.print("Input file: ");
        String fileName = in.nextLine();
        //Prompt the user to enter the output file name
        out.print("Output file: ");
        String outFileName = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(fileName);
        // Create a queue with lines
        Queue<String> lineQueue = getLineFromFile(inFile);
        // Create a queue with words
        Queue<String> wordQueue = getWordFromLine(lineQueue);
        // Create a map with words and occurrences
        Map<String, Integer> wordMap = getWordMap(wordQueue);
        // Create a queue with sorted words
        Queue<String> Orderedqueue = sort(wordMap);
        // Print the html page
        printPage(Orderedqueue, wordMap, fileName, outFileName);
        in.close();
        inFile.close();
        out.close();
    }

}
