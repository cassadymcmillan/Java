import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * @author Sheng Wang
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        // Declare the NaturalNumber variables
        NaturalNumber lowEnough = new NaturalNumber2(0);
        NaturalNumber tooHigh = new NaturalNumber2();
        NaturalNumber two = new NaturalNumber2(2);
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber guessRoot = new NaturalNumber2();
        NaturalNumber difference = new NaturalNumber2();

        // Initialize tooHigh as n
        tooHigh.copyFrom(n);
        // Increase tooHigh by 1
        tooHigh.increment();

        // Initialize guessRoot as n
        guessRoot.copyFrom(n);
        // Half the guessRoot
        guessRoot.divide(two);

        // Initialize difference as n
        difference.copyFrom(n);

        // Except the n=1
        if (n.compareTo(one) > 0) {

            // Use the while loop to narrow the interval
            while (difference.compareTo(one) > 0) {
                // Declare the NaturalNumber variables
                NaturalNumber guessValue = new NaturalNumber2();
                NaturalNumber lowValue = new NaturalNumber2();
                NaturalNumber highValue = new NaturalNumber2();
                NaturalNumber lowCopy = new NaturalNumber2();
                difference = new NaturalNumber2();

                // Copy the value of guessRoot
                guessValue.copyFrom(guessRoot);

                // Power the guessValue to r
                guessValue.power(r);

                // Upgrade the value of lowEnough or tooHigh
                if (n.compareTo(guessValue) >= 0) {
                    lowEnough.copyFrom(guessRoot);
                } else {
                    tooHigh.copyFrom(guessRoot);
                }

                // Copy the lowEnough or tooHigh to do calculation
                lowValue.copyFrom(lowEnough);
                highValue.copyFrom(tooHigh);

                // Upgrade guessRoot by interval halving
                lowValue.add(highValue);
                lowValue.divide(two);
                guessRoot.copyFrom(lowValue);

                // Upgrade the difference between lowEnough or tooHigh
                lowCopy.copyFrom(lowEnough);
                difference.copyFrom(tooHigh);
                difference.subtract(lowCopy);
            }
            // Get the root of n
            n.copyFrom(guessRoot);
        }
    }

    private static NaturalNumber productOfDigits1(NaturalNumber n) {
        NaturalNumber product = new NaturalNumber2(1);
        NaturalNumber ten = new NaturalNumber2(10);
        int i = 0;
        i = n.divideBy10();
        if (n.compareTo(ten) > 0) {
            productOfDigits1(n);
        } else {
            NaturalNumber j = new NaturalNumber2(i);
            product.multiply(j);
        }
        return product;
    }

    private static NaturalNumber productOfDigits2(NaturalNumber n) {
        NaturalNumber product = new NaturalNumber2(1);
        NaturalNumber zero = new NaturalNumber2(0);
        int i = n.divideBy10();
        if (n.compareTo(zero) > 0) {
            productOfDigits1(n);
        } else {
            NaturalNumber j = new NaturalNumber2(i);
            product.multiply(j);
        }
        return product;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        NaturalNumber n = new NaturalNumber2();
        NaturalNumber c = new NaturalNumber2();
        c.copyFrom(n);
        out.print(c);

        out.close();
    }

}
