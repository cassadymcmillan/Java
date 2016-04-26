import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;
import components.statement.StatementKernel.Kind;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Wayne Heym
 * @author bolong Zhang, Sheng Wang
 */
public abstract class StatementTest {

    /**
     * The name of a file containing a sequence of BL statements.
     */
    private static final String FILE_NAME_1 = "data/statement-sample.bl";

    // TODO: define file names for additional test inputs

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructor = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructor = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     *
     * Creates and returns a block {@code Statement}, of the type of the
     * implementation under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed for the sequence of
     *            statements to go in the block statement
     * @return the constructed block statement
     * @ensures
     *
     *          <pre>
     * createFromFile = [the block statement containing the statements
     * parsed from the file]
     *          </pre>
     */
    private Statement createFromFileTest(String filename) {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        return s;
    }

    /**
     *
     * Creates and returns a block {@code Statement}, of the reference
     * implementation type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed for the sequence of
     *            statements to go in the block statement
     * @return the constructed block statement
     * @ensures
     *
     *          <pre>
     * createFromFile = [the block statement containing the statements
     * parsed from the file]
     *          </pre>
     */
    private Statement createFromFileRef(String filename) {
        Statement s = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        return s;
    }

    private static final String SFILE = "data/statementTest.bl";

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();

        /*
         * The call
         */
        Statement sTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test kind of a WHILE statement.
     */
    @Test
    public final void testKindWhile() {
        /*
         * Setup
         */
        final int whilePos = 3;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(whilePos);
        Statement sRef = sourceRef.removeFromBlock(whilePos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test addToBlock at an interior position.
     */
    @Test
    public final void testAddToBlockInterior() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = sRef.newInstance();
        Statement nestedTest = sTest.removeFromBlock(1);
        Statement nestedRef = sRef.removeFromBlock(1);
        sRef.addToBlock(2, nestedRef);

        /*
         * The call
         */
        sTest.addToBlock(2, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test removeFromBlock at the front leaving a non-empty block behind.
     */
    @Test
    public final void testRemoveFromBlockFrontLeavingNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement nestedRef = sRef.removeFromBlock(0);

        /*
         * The call
         */
        Statement nestedTest = sTest.removeFromBlock(0);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nestedRef, nestedTest);
    }

    /**
     * Test lengthOfBlock, greater than zero.
     */
    @Test
    public final void testLengthOfBlockNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        int lengthRef = sRef.lengthOfBlock();

        /*
         * The call
         */
        int lengthTest = sTest.lengthOfBlock();

        /*
         * Evaluation
         */
        assertEquals(lengthRef, lengthTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test assembleIf.
     */
    @Test
    public final void testAssembleIf() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sourceTest.newInstance();
        Condition c = sourceTest.disassembleIf(nestedTest);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIf(c, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleIf.
     */
    @Test
    public final void testDisassembleIf() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIf(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIf(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test assembleIfElse.
     */
    @Test
    public final void testAssembleIfElse() {
        /*
         * Setup
         */
        final int ifElsePos = 2;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sourceTest.newInstance();
        Statement elseBlockTest = sourceTest.newInstance();
        Condition cTest = sourceTest.disassembleIfElse(thenBlockTest,
                elseBlockTest);
        Statement sTest = blockTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIfElse(cTest, thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, thenBlockTest);
        assertEquals(emptyBlock, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleIfElse.
     */
    @Test
    public final void testDisassembleIfElse() {
        /*
         * Setup
         */
        final int ifElsePos = 2;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sTest.newInstance();
        Statement elseBlockTest = sTest.newInstance();
        Statement thenBlockRef = sRef.newInstance();
        Statement elseBlockRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIfElse(thenBlockRef, elseBlockRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIfElse(thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(cRef, cTest);
        assertEquals(thenBlockRef, thenBlockTest);
        assertEquals(elseBlockRef, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test assembleWhile.
     */
    @Test
    public final void testAssembleWhile() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(1);
        Statement sourceRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sourceTest.newInstance();
        Statement nestedRef = sourceRef.newInstance();
        Condition cTest = sourceTest.disassembleIf(nestedTest);
        Condition cRef = sourceRef.disassembleIf(nestedRef);
        Statement sRef = sourceRef.newInstance();
        sRef.assembleWhile(cRef, nestedRef);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleWhile(cTest, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleWhile.
     */
    @Test
    public final void testDisassembleWhile() {
        /*
         * Setup
         */
        final int whilePos = 3;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(whilePos);
        Statement sRef = blockRef.removeFromBlock(whilePos);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleWhile(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleWhile(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test assembleCall.
     */
    @Test
    public final void testAssembleCall() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef().newInstance();
        Statement sTest = this.constructorTest().newInstance();

        String name = "look-for-something";
        sRef.assembleCall(name);

        /*
         * The call
         */
        sTest.assembleCall(name);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleCall.
     */
    @Test
    public final void testDisassembleCall() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(0);
        Statement sRef = blockRef.removeFromBlock(0);
        String nRef = sRef.disassembleCall();

        /*
         * The call
         */
        String nTest = sTest.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nRef, nTest);
    }

    // More testcases

    /**
     * Test addToBlock on file2.
     */
    @Test
    public final void testAddToBlockInterior2() {
        /*
         * Initialize the environment
         */
        Statement sTest = this.createFromFileTest(SFILE);
        Statement sRef = this.createFromFileRef(SFILE);
        Statement emptyBlock = sRef.newInstance();
        Statement nestedTest = sTest.removeFromBlock(1);
        Statement nestedRef = sRef.removeFromBlock(1);
        sRef.addToBlock(0, nestedRef);

        /*
         * Call the function
         */
        sTest.addToBlock(0, nestedTest);

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleCall on file 2
     */
    @Test
    public final void testDisassembleCall2() {
        /*
         * Initialize the environment
         */
        Statement blockTest = this.createFromFileTest(SFILE);
        Statement blockRef = this.createFromFileRef(SFILE);
        Statement sTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        String nRef = sRef.disassembleCall();

        /*
         * Call the function
         */
        String nTest = sTest.disassembleCall();

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(sRef, sTest);
        assertEquals(nRef, nTest);
    }

    /**
     * Test disassembleIfElse on file 2
     */
    @Test
    public final void testDisassembleIfElse2() {
        /*
         * Initialize the environment
         */
        final int ifElsePos = 0;
        Statement blockTest = this.createFromFileTest(SFILE);
        Statement blockRef = this.createFromFileRef(SFILE);
        Statement sTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sTest.newInstance();
        Statement elseBlockTest = sTest.newInstance();
        Statement thenBlockRef = sRef.newInstance();
        Statement elseBlockRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIfElse(thenBlockRef, elseBlockRef);

        /*
         * Call the function
         */
        Condition cTest = sTest.disassembleIfElse(thenBlockTest, elseBlockTest);

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(cRef, cTest);
        assertEquals(thenBlockRef, thenBlockTest);
        assertEquals(elseBlockRef, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test kind of a if else on file 2
     */
    @Test
    public final void testKindIfElse2() {
        /*
         * Set up
         */
        final int pos = 0;
        Statement sourceTest = this.createFromFileTest(SFILE);
        Statement sourceRef = this.createFromFileRef(SFILE);
        Statement sourceTestIfElse = sourceTest.removeFromBlock(pos);
        Statement sourceRefIfElse = sourceRef.removeFromBlock(pos);

        Kind kTest = sourceTestIfElse.kind();
        Kind kRef = sourceRefIfElse.kind();

        assertEquals(kRef, kTest);
        assertEquals(sourceRef, sourceTest);
    }

    /**
     * Test kind of a while statement with file 2.
     */
    @Test
    public final void testKindWhile2() {
        /*
         * Set up
         */
        final int pos = 0;
        Statement sourceTest = this.createFromFileTest(SFILE);
        Statement sourceRef = this.createFromFileRef(SFILE);
        Statement sourceTestIfElse = sourceTest.removeFromBlock(pos);
        Statement sourceRefIfElse = sourceRef.removeFromBlock(pos);

        Statement s1Test = sourceTest.newInstance();
        Statement s2Test = sourceTest.newInstance();
        Condition cTest = sourceTestIfElse.disassembleIfElse(s1Test, s2Test);
        Statement sTest = s2Test.removeFromBlock(pos);
        sourceTestIfElse.assembleIfElse(cTest, s1Test, s2Test);

        Statement s1Ref = sourceRef.newInstance();
        Statement s2Ref = sourceRef.newInstance();
        Condition cRef = sourceRefIfElse.disassembleIfElse(s1Ref, s2Ref);
        Statement sRef = s2Ref.removeFromBlock(pos);
        sourceRefIfElse.assembleIfElse(cRef, s1Ref, s2Ref);

        Kind kTest = sTest.kind();
        Kind kRef = sRef.kind();

        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);

    }

    /**
     * Test LengthOfBlock on file 2
     */
    @Test
    public final void testLengthOfBlockEmpty2() {
        /*
         * Initialize the environment
         */
        Statement sTest = this.createFromFileTest(SFILE);
        Statement sRef = this.createFromFileRef(SFILE);
        sTest.removeFromBlock(1);
        sTest.removeFromBlock(0);
        sRef.removeFromBlock(1);
        sRef.removeFromBlock(0);
        int lengthRef = sRef.lengthOfBlock();

        /*
         * Call the function
         */
        int lengthTest = sTest.lengthOfBlock();

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(lengthRef, lengthTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test removeFromBlock on file 2
     */
    @Test
    public final void testRemoveFromBlockFrontLeavingNonEmpty2() {
        /*
         * Initialize the environment
         */
        Statement sTest = this.createFromFileTest(SFILE);
        Statement sRef = this.createFromFileRef(SFILE);
        Statement nestedRef = sRef.removeFromBlock(0);

        /*
         * Call the function
         */
        Statement nestedTest = sTest.removeFromBlock(0);

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(sRef, sTest);
        assertEquals(nestedRef, nestedTest);
    }

    /**
     * Test removeFromBlock leaving an empty block behind.
     */
    @Test
    public final void testRemoveFromBlockLeavingEmpty2() {
        /*
         * Initialize the environment
         */
        Statement sTest = this.createFromFileTest(SFILE);
        Statement sRef = this.createFromFileRef(SFILE);
        Statement nestedRef = sRef.removeFromBlock(1);
        nestedRef = sRef.removeFromBlock(0);
        /*
         * Call the function
         */
        Statement nestedTest = sTest.removeFromBlock(1);
        nestedTest = sTest.removeFromBlock(0);

        /*
         * Check if the test case satisfy the requirement
         */
        assertEquals(sRef, sTest);
        assertEquals(nestedRef, nestedTest);
    }

}
