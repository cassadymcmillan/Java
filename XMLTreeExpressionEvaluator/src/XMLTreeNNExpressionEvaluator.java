import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code NN}.
 *
 * @author Sheng Wang
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires
     *
     *           <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     *           </pre>
     *
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        // Create three variables and one constant
        NaturalNumber result = new NaturalNumber2();
        NaturalNumber temp1 = new NaturalNumber2();
        NaturalNumber temp2 = new NaturalNumber2();
        NaturalNumber ZERO = new NaturalNumber2(0);

        // Check if the label has attribute
        if (!exp.hasAttribute("value")) {
            // Determine the operation
            if (exp.label().equals("plus")) {
                temp1.copyFrom(evaluate(exp.child(0)));
                temp2.copyFrom(evaluate(exp.child(1)));
                temp1.add(temp2);
                result.copyFrom(temp1);
            }
            if (exp.label().equals("minus")) {
                temp1.copyFrom(evaluate(exp.child(0)));
                temp2.copyFrom(evaluate(exp.child(1)));
                // Check the preconditions
                if (temp1.compareTo(temp2) < 0) {
                    Reporter.fatalErrorToConsole(
                            "Program terminated. Fatal error detected. Subtraction returns a negative number.");
                }
                temp1.subtract(temp2);
                result.copyFrom(temp1);
            }
            if (exp.label().equals("times")) {
                temp1.copyFrom(evaluate(exp.child(0)));
                temp2.copyFrom(evaluate(exp.child(1)));
                temp1.multiply(temp2);
                result.copyFrom(temp1);
            }
            if (exp.label().equals("divide")) {
                temp1.copyFrom(evaluate(exp.child(0)));
                temp2.copyFrom(evaluate(exp.child(1)));
                // Check the preconditions
                if (temp2.compareTo(ZERO) <= 0) {
                    Reporter.fatalErrorToConsole(
                            "Program terminated. Fatal error detected. Cannot divide by 0.");
                }
                temp1.divide(temp2);
                result.copyFrom(temp1);
            }
        } else {
            // Initialize the value of result
            result = new NaturalNumber2(
                    Integer.parseInt(exp.attributeValue("value")));
        }

        return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}