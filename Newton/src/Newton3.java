import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The program is to prompt the user for numbers and report the square root of
 * that number to within a relative error of no more than 0.01%. The computation
 * must be done using Newton iteration.
 *
 * @author Sheng Wang
 *
 */
public final class Newton3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton3() {
    }

    /**
     * This method calculates the square root of a number by using Newton
     * iteration. It also works as the number equals 0.
     */
    private static double sqrt(double x, double z) {
        /*
         * Put your code for myMethod here
         */
        if (x != 0) {
            double r = x;
            while (Math.abs(r * r - x) / x > z * z) {
                r = (r + x / r) / 2.0;
            }
            return r;
        } else {
            return 0;
        }
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
        /*
         * Put your main program code here; it may call sqrt method as shown
         */
        out.print("Please input a number: ");
        double n = in.nextDouble();
        out.print("Please input the value of ¦Å: ");
        double e = in.nextDouble();
        double root = sqrt(n, e);
        out.println("The square root of " + n + " is: " + root);
        /*
         * Ask the user whether they wish to calculate another square root
         */
        String ans;
        out.print(
                "Do you want to calculate another square root?  Input y or n: ");
        ans = in.nextLine();
        while (ans.equals("y")) {
            out.print("Please input a number: ");
            n = in.nextDouble();
            root = sqrt(n, e);
            out.println("The square root of " + n + " is: " + root);
            out.print(
                    "Do you want to calculate another square root?  Input y or n: ");
            ans = in.nextLine();
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
