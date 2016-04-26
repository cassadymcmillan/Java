import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program asks the user what constant ¦Ì should be approximated, and then
 * asks in turn for each of the four personal numbers w, x, y, and z. The
 * program should then calculate and report the values of the exponents a, b, c,
 * and d that bring the de Jager formula as close as possible to ¦Ì, as well as
 * the value of the formula waxbyczd and the relative error of the approximation
 * to the nearest hundredth of one percent.
 *
 * @author Sheng Wang
 *
 */
public final class ABCDGuesser1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
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

        out.print("Enter the value of constant ¦Ì: ");
        double mu = in.nextDouble(); // Input the value of the constant ¦Ì

        out.print("Enther four positive number: ");
        double w = in.nextDouble(); // Input the value of the w
        double x = in.nextDouble(); // Input the value of the x
        double y = in.nextDouble(); // Input the value of the y
        double z = in.nextDouble(); // Input the value of the z

        double[] exp = { -5, -4, -3, -2, -1, -1.0 / 2.0, -1.0 / 3.0, -1.0 / 4.0,
                0, 1.0 / 4.0, 1.0 / 3.0, 1.0 / 2.0, 1, 2, 3, 4, 5 }; // The values of exponents

        double bestEstimate = 0; // The best estimate value
        double a, b, c, d; // The value after power
        double bestIndex1 = 0; // The best estimate value of exponent for w
        double bestIndex2 = 0; // The best estimate value of exponent for x
        double bestIndex3 = 0; // The best estimate value of exponent for y
        double bestIndex4 = 0; // The best estimate value of exponent for z

        int i = 0;
        while (i < exp.length) {
            a = Math.pow(w, exp[i]);

            int j = 0;
            while (j < exp.length) {
                b = Math.pow(x, exp[j]);

                int k = 0;
                while (k < exp.length) {
                    c = Math.pow(y, exp[k]);

                    int n = 0;
                    while (n < exp.length) {
                        d = Math.pow(z, exp[n]);

                        // Estimate value of each time
                        double currentEstimate = a * b * c * d;

                        // Make the best estimate value closest to the constant ¦Ì
                        if (Math.abs(currentEstimate - mu) < Math
                                .abs(bestEstimate - mu)) {
                            bestEstimate = currentEstimate;
                            bestIndex1 = exp[i];
                            bestIndex2 = exp[j];
                            bestIndex3 = exp[k];
                            bestIndex4 = exp[n];
                        }
                        n++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }

        out.println("The most appropriate value for a, b, c and d is : "
                + bestIndex1 + " " + bestIndex2 + " " + bestIndex3 + " "
                + bestIndex4);

        out.println("Best estimate: " + bestEstimate);

        double error = Math.abs((bestEstimate - mu) / mu) * 100.0;
        out.println("Relative error = " + error + "%");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
