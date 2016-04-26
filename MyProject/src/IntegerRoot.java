import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Compute integer power with interval halving and test it.
 *
 * @author Put your name here
 *
 */
public final class IntegerRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private IntegerRoot() {
    }

    /**
     * Returns {@code n} to the power {@code p}.
     *
     * @param n
     *            the number to which we want to apply the power
     * @param p
     *            the power
     * @return the number to the power
     * @requires Integer.MIN_VALUE <= n ^ (p) <= Integer.MAX_VALUE and p >= 0
     * @ensures power = n ^ (p)
     */
    private static int power(int n, int p) {
        int result = 1, count = 0;
        while (count < p) {
            result *= n;
            count++;
        }
        return result;
    }

    /**
     * Returns the {@code r}-th root of {@code n}.
     *
     * @param n
     *            the number to which we want to apply the root
     * @param r
     *            the root
     * @return the root of the number
     * @requires n >= 0 and r > 0
     * @ensures root ^ (r) <= n < (root + 1) ^ (r)
     */
    private static int root(int n, int r) {
        int low = 0;
        int high = n + 1;
        int i = (high - low) / 2;

        while (high - low > 1) {
            if (n >= power(i, r)) {
                low = i;
            } else {
                high = i;
            }
            i = (high + low) / 2;
        }

        return low;
    }

    private static boolean isPalindrome(String s) {
        int length = s.length();
        if (length <= 1) {
            return true;
        } else if (s.charAt(length - 1) == s.charAt(0)) {
            return isPalindrome(s.substring(1, length - 1));
        } else {
            return false;
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final int[] numbers = { 0, 0, 0, 1, 1, 1, 82, 82, 82, 82, 82, 3, 9, 27,
                81, 243 };
        final int[] roots = { 1, 2, 5, 1, 2, 17, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };
        final int[] results = { 0, 0, 0, 1, 1, 1, 82, 9, 4, 3, 2, 3, 3, 3, 3,
                3 };

        for (int i = 0; i < numbers.length; i++) {
            int x = root(numbers[i], roots[i]);
            if (x == results[i]) {
                out.println();
            } else {
                out.println();
            }
        }
        String a = "abcba";

        out.print(isPalindrome(a));

        out.close();
    }

}
