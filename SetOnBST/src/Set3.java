import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions
 *
 *                  <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 *                  </pre>
 *
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Sheng Wang, Bolong Zhang
 *
 */
public class Set3<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        boolean result = false;
        /*
         * Create left and right subtrees
         */
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        /*
         * Check whether x is in tree recursively
         */
        if (t.size() > 0) {
            T root = t.disassemble(left, right);
            result = (root.equals(x) || isInTree(left, x)
                    || isInTree(right, x));
            t.assemble(root, left, right);
        }
        return result;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        /*
         * Create left and right subtrees
         */
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        /*
         * If tree has no node, just insert x as the root. Else, determine
         * whether x goes into left or right tree; Call this method recursively
         * until x become a leaf in the tree
         */
        if (t.size() == 0) {
            t.assemble(x, t.newInstance(), t.newInstance());
        } else {
            T root = t.disassemble(left, right);
            if (x.compareTo(root) < 0) {
                insertInTree(left, x);
            } else {
                insertInTree(right, x);
            }
            t.assemble(root, left, right);
        }
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures
     *
     *          <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     *          </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        /*
         * Create container for left and right subtrees
         */
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        /*
         * Disassemble the tree and store the root for result
         */
        T root = t.disassemble(left, right);
        T result = root;
        /*
         * If left subtree has nodes, call the method recursively until the
         * smallest node becomes the root of t. Else, when right subtree has
         * nodes, set the tree to the right tree
         */
        if (left.size() > 0) {
            result = removeSmallest(left);
            t.assemble(root, left, right);
        } else if (right.size() > 0) {
            t.transferFrom(right);
        }
        return result;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures
     *
     *          <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     *          </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        /*
         * Create containers for left and right subtrees
         */
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        /*
         * Disassemble the tree
         */
        T root = t.disassemble(left, right);
        T result = root;
        /*
         * Determine whether x is in left or right subtree, call this method
         * recursively to find x and remove it
         */
        if (x.compareTo(root) < 0) {
            result = removeFromTree(left, x);
            t.assemble(root, left, right);
        } else if (x.compareTo(root) > 0) {
            result = removeFromTree(right, x);
            t.assemble(root, left, right);
        } else {
            if (right.size() == 0) {
                t.transferFrom(left);
            } else {
                T smallest = removeSmallest(right);
                t.assemble(smallest, left, right);
            }
        }
        // Return the result
        return result;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.tree = new BinaryTree1<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3() {

        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3<T> localSource = (Set3<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert!this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
