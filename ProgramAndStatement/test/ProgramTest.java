import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Wayne Heym
 * @author Bolong Zhang, Sheng Wang
 */
public abstract class ProgramTest {

    /**
     * The name of a file containing a BL program.
     */
    private static final String FILE_NAME_1 = "data/program-sample.bl";

    // TODO: define file names for additional test inputs

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     *
     * Creates and returns a {@code Program}, of the type of the implementation
     * under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileTest(String filename) {
        Program p = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     *
     * Creates and returns a {@code Program}, of the reference implementation
     * type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileRef(String filename) {
        Program p = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    private static final String INST = "data/instruction.bl";
    private static final String BODY = "data/body.bl";

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();

        /*
         * The call
         */
        Program pTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test replaceName.
     */
    @Test
    public final void testReplaceName() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        String newName = "Replacement";
        String nameRef = pRef.replaceName(newName);

        /*
         * The call
         */
        String nameTest = pTest.replaceName(newName);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(nameRef, nameTest);
    }

    /**
     * Test newContext.
     */
    @Test
    public final void testNewContext() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> cRef = pRef.newContext();

        /*
         * The call
         */
        Map<String, Statement> cTest = pTest.newContext();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test replaceContext.
     */
    @Test
    public final void testReplaceContext() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> cReplacementRef = pRef.newContext();
        Map<String, Statement> cReplacementTest = pTest.newContext();
        String oneName = "one";
        Map<String, Statement> cObtainedRef = pRef
                .replaceContext(cReplacementRef);
        Pair<String, Statement> oneRef = cObtainedRef.remove(oneName);
        /* cObtainedRef now has just "two" */
        cReplacementRef.add(oneRef.key(), oneRef.value());
        pRef.replaceContext(cReplacementRef);
        /* pRef's context now has just "one" */

        /* Make the reference call, replacing "one" with "two": */
        cReplacementRef = pRef.replaceContext(cObtainedRef);

        Map<String, Statement> cObtainedTest = pTest
                .replaceContext(cReplacementTest);
        Pair<String, Statement> oneTest = cObtainedTest.remove(oneName);
        /* cObtainedTest now has just "two" */
        cReplacementTest.add(oneTest.key(), oneTest.value());
        pTest.replaceContext(cReplacementTest);
        /* pTest's context now has just "one" */

        /*
         * The call
         */
        cReplacementTest = pTest.replaceContext(cObtainedTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(cReplacementRef, cReplacementTest);
        assertEquals(cObtainedRef, cObtainedTest);
    }

    /**
     * Test newBody.
     */
    @Test
    public final void testNewBody() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bRef = pRef.newBody();

        /*
         * The call
         */
        Statement bTest = pTest.newBody();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * Test replaceBody.
     */
    @Test
    public final void testReplaceBody() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bReplacementRef = pRef.newBody();
        Statement bReplacementTest = pTest.newBody();
        Statement bObtainedRef = pRef.replaceBody(bReplacementRef);
        Statement firstRef = bObtainedRef.removeFromBlock(0);
        /* bObtainedRef now lacks the first statement */
        bReplacementRef.addToBlock(0, firstRef);
        pRef.replaceBody(bReplacementRef);
        /* pRef's body now has just the first statement */

        /* Make the reference call, replacing first with remaining: */
        bReplacementRef = pRef.replaceBody(bObtainedRef);

        Statement bObtainedTest = pTest.replaceBody(bReplacementTest);
        Statement firstTest = bObtainedTest.removeFromBlock(0);
        /* bObtainedTest now lacks the first statement */
        bReplacementTest.addToBlock(0, firstTest);
        pTest.replaceBody(bReplacementTest);
        /* pTest's context now has just the first statement */

        /*
         * The call
         */
        bReplacementTest = pTest.replaceBody(bObtainedTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bReplacementRef, bReplacementTest);
        assertEquals(bObtainedRef, bObtainedTest);
    }

    // More test cases

    /**
     * Test replaceName with instruction file
     */
    @Test
    public final void testReplaceNameINST() {
        /*
         * Initialize the environment
         */
        Program pgTest = this.createFromFileTest(INST);
        Program pgRef = this.createFromFileRef(INST);
        String newName = "NewNewNew";
        String nameRef = pgRef.replaceName(newName);

        /*
         * Call the function
         */
        String nameTest = pgTest.replaceName(newName);

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(pgRef, pgTest);
        assertEquals(nameRef, nameTest);
    }

    /**
     * Test newBody with instruction file
     */
    @Test
    public final void testNewBodyINST() {
        /*
         * Initialize the environment
         */
        Program pTest = this.createFromFileTest(INST);
        Program pRef = this.createFromFileRef(INST);
        Statement bRef = pRef.newBody();

        /*
         * Call the function
         */
        Statement bTest = pTest.newBody();

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * Test newContext with instruction file
     */
    @Test
    public final void testNewContextINST() {
        /*
         * Initialize the environment
         */
        Program pTest = this.createFromFileTest(INST);
        Program pRef = this.createFromFileRef(INST);
        Map<String, Statement> cRef = pRef.newContext();

        /*
         * Call the function
         */
        Map<String, Statement> cTest = pTest.newContext();

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test replacebody with instruction and body file
     */

    @Test
    public final void testReplaceBodyWithINST() {
        /*
         * Initialize the environment
         */
        Program pTestINST = this.createFromFileTest(INST);
        Program pRefINST = this.createFromFileRef(INST);

        Program pTestBODY = this.createFromFileTest(BODY);
        Program pRefBODY = this.createFromFileRef(BODY);

        Statement bRefINST = pRefINST.replaceBody(pRefINST.newBody());
        Statement bTestINST = pTestINST.replaceBody(pTestINST.newBody());

        Statement bRefBODY = pRefBODY.replaceBody(pRefBODY.newBody());
        Statement bTestBODY = pTestBODY.replaceBody(pTestBODY.newBody());

        Statement INSTRefReturn = pRefINST.replaceBody(bRefBODY);
        Statement INSTBODYReturn = pRefBODY.replaceBody(bRefINST);

        /*
         * Call the function
         */

        Statement INSTTestReturn = pTestINST.replaceBody(bTestBODY);
        Statement BODYTestReturn = pTestBODY.replaceBody(bTestINST);

        /*
         * Check if the test case satisfy the requirement
         */

        assertEquals(INSTRefReturn, INSTTestReturn);
        assertEquals(INSTBODYReturn, BODYTestReturn);
        assertEquals(bRefBODY, bTestBODY);
        assertEquals(bRefINST, bTestINST);
        assertEquals(pRefINST, pTestINST);
        assertEquals(pRefBODY, pTestBODY);

    }

    /**
     * Test replaceBody with body file
     */

    @Test
    public final void testReplaceBodyBODYWithNew() {
        /*
         * Initialize the environment
         */

        Program pTestBODY = this.createFromFileTest(BODY);
        Program pRefBODY = this.createFromFileRef(BODY);

        Statement bRefBODY = pRefBODY.replaceBody(pRefBODY.newBody());

        /*
         * Call the function
         */

        Statement bTestBODY = pTestBODY.replaceBody(pTestBODY.newBody());

        /*
         * Check if the test case satisfy the requirement
         */

        assertEquals(bRefBODY, bTestBODY);
        assertEquals(pRefBODY, pTestBODY);

    }

    /**
     * Test replaceBody with instruction file and new body
     */
    @Test
    public final void testReplaceBodyINSTWithNew() {
        /*
         * Initialize the environment
         */
        Program pTestINST = this.createFromFileTest(INST);
        Program pRefINST = this.createFromFileRef(INST);

        Statement bRefINST = pRefINST.replaceBody(pRefINST.newBody());

        /*
         * Call the function
         */

        Statement bTestINST = pTestINST.replaceBody(pTestINST.newBody());

        /*
         * Check if the test case satisfy the requirement
         */

        assertEquals(bRefINST, bTestINST);
        assertEquals(pRefINST, pTestINST);

    }

    /**
     * Test replaceName with instruction and body file
     */

    @Test
    public final void testReplaceContextBODYWithINST() {
        /*
         * Initialize the environment
         */
        Program pTestINST = this.createFromFileTest(INST);
        Program pRefINST = this.createFromFileRef(INST);

        Program pTestBODY = this.createFromFileTest(BODY);
        Program pRefBODY = this.createFromFileRef(BODY);

        Map<String, Statement> cRefINST = pRefINST
                .replaceContext(pRefINST.newContext());
        Map<String, Statement> cTestINST = pTestINST
                .replaceContext(pTestINST.newContext());

        Map<String, Statement> cRefBODY = pRefBODY
                .replaceContext(pRefBODY.newContext());
        Map<String, Statement> cTestBODY = pTestBODY
                .replaceContext(pTestBODY.newContext());

        Map<String, Statement> INSTRefReturn = pRefINST
                .replaceContext(cRefBODY);
        Map<String, Statement> INSTBODYReturn = pRefBODY
                .replaceContext(cRefINST);

        /*
         * Call the function
         */

        Map<String, Statement> INSTTestReturn = pTestINST
                .replaceContext(cTestBODY);
        Map<String, Statement> BODYTestReturn = pTestBODY
                .replaceContext(cTestINST);

        /*
         * Check if the test case satisfy the requirement
         */

        assertEquals(INSTRefReturn, INSTTestReturn);
        assertEquals(INSTBODYReturn, BODYTestReturn);
        assertEquals(cRefBODY, cTestBODY);
        assertEquals(cRefINST, cTestINST);
        assertEquals(pRefINST, pTestINST);
        assertEquals(pRefBODY, pTestBODY);

    }

    /**
     * Test replaceContext with body file and new context
     */

    @Test
    public final void testReplaceContextBODYWithNew() {
        /*
         * Initialize the environment
         */

        Program pTestBODY = this.createFromFileTest(BODY);
        Program pRefBODY = this.createFromFileRef(BODY);

        Map<String, Statement> cRefBODY = pRefBODY
                .replaceContext(pRefBODY.newContext());

        /*
         * Call the function
         */

        Map<String, Statement> cTestBODY = pTestBODY
                .replaceContext(pTestBODY.newContext());

        /*
         * Check if the test case satisfy the requirement
         */

        assertEquals(cRefBODY, cTestBODY);
        assertEquals(pRefBODY, pTestBODY);

    }

    /**
     * Test replaceContext with instruction file and new context
     */
    @Test
    public final void testReplaceContextINSTWithNew() {
        /*
         * Initialize the environment
         */
        Program pTestINST = this.createFromFileTest(INST);
        Program pRefINST = this.createFromFileRef(INST);

        Map<String, Statement> cRefINST = pRefINST
                .replaceContext(pRefINST.newContext());

        /*
         * Call the function
         */

        Map<String, Statement> cTestINST = pTestINST
                .replaceContext(pTestINST.newContext());

        /*
         * Check if the test case satisfy the requirement
         */

        assertEquals(cRefINST, cTestINST);
        assertEquals(pRefINST, pTestINST);

    }

}
