import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for array/BST/DLL conversion functions.
 *
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class ConversionTests {
    /** Helper method: verify that two arrays contain the same sequence. */
    private static <T> void verifyArray(T[] expected, T[] actual) {
        assertEquals("Array lengths differ", expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Mismatch at position " + i, expected[i], actual[i]);
        }
    }

    /** Helper method: verify that DLL nodes and data match expected array. */
    private static <T> void verifyList(DLL<T> list, T[] arr) {
        if (arr.length == 0) {
            assertNull("Head should be null for empty list", list.getHead());
            assertNull("Tail should be null for empty list", list.getTail());
        } else {
            assertNull("Head's previous should be null", list.getHead().getLeft());
            assertNull("Tail's next should be null", list.getTail().getRight());

            for (int i = 0; i < arr.length; i++) {
                BinaryTree<T> pos = list.getHead();
                for (int j = 0; j < i; j++) pos = pos.getRight();

                BinaryTree<T> pos2 = list.getTail();
                for (int j = 0; j < arr.length - 1 - i; j++) pos2 = pos2.getLeft();

                assertSame("Node mismatch at position " + i, pos, pos2);
                assertEquals("Value mismatch at position " + i, arr[i], pos.getData());
            }
        }
    }

    public void testLookUp() {
        BST<Integer> root = new BST<>(5);
        root.insert(2);
        root.insert(8);

        BST<Integer> result1 = root.lookup(8);
        assertNotNull("Should find existing element: ", result1);
        assertEquals(Integer.valueOf(8), result1.getData());

        BST<Integer> result2 = root.lookup(5);
        assertNotNull("Should find root: ", result2);
        assertEquals(Integer.valueOf(5), result2.getData());
        assertSame(root, result2);
    }

    public void testLookUpNotFound() {
        BST<Integer> root = new BST<>(5);
        root.insert(2);
        root.insert(8);

        BST<Integer> result = root.lookup(0);
        assertNull("Shouldn't find non-existing element: ", result);
    }

    public void testInsert() {
        BST<Integer> root = new BST<>(15); //tree only has root node with value 15
        assertEquals(Integer.valueOf(15), root.getData());
        assertNull(root.getLeft());
        assertNull(root.getRight());
    }

    public void testInsertLeftChild() {
        BST<Integer> root = new BST<>(15);
        root.insert(7);

        assertNotNull("Root's left child should exist: ", root.getLeft());
        assertEquals(Integer.valueOf(7), root.getLeft().getData());
        assertNull("Root's right child should be null: ", root.getRight());
    }
    
    public void testInsertRightChild() {
        BST<Integer> root = new BST<>(15);
        root.insert(20);
        
        assertNotNull("Root's right child should exist: ", root.getRight());
        assertEquals(Integer.valueOf(20), root.getRight().getData());
        assertNull("Root's left child should be null: ", root.getLeft());
    }
    
    public void testInsertDuplicate() {
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        root.insert(7); //duplicate node
        
        assertEquals(Integer.valueOf(15), root.getData());
        assertEquals(Integer.valueOf(7), root.getLeft().getData());
        assertNull("Root's right child should be null: ", root.getRight());
        assertNull("Left child of root's left child should be null: ", root.getLeft().getLeft()); //check there are no additional nodes after inserting duplicate
    }

    public void testDeleteLeaf() {
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        root.insert(20);

        root = root.deleteWithCopyLeft(7);

        assertNotNull("Root should still exist: ", root);
        assertEquals(Integer.valueOf(15), root.getData());
        assertNull("Root's left child should be deleted: ", root.getLeft());
        assertNotNull("Root's right child should still exist: ", root.getRight());
        assertEquals(Integer.valueOf(20), root.getRight().getData());
    }

    public void testDeleteNodeWithOneChild() {
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        root.insert(20);
        root.insert(3);

        root = root.deleteWithCopyLeft(7);

        assertNotNull("Root should still exist: ", root);
        assertEquals(Integer.valueOf(15), root.getData());
        assertNotNull("Root's left child should still exist: ", root.getLeft());
        assertEquals(Integer.valueOf(3), root.getLeft().getData()); //3 promoted to parent's position
        assertEquals(Integer.valueOf(20), root.getRight().getData());
    }

    public void testDeleteNodeWithTwoChildren() {
        BST<Integer> root1 = new BST<>(15);
        root1.insert(7);
        root1.insert(20);

        root1 = root1.deleteWithCopyLeft(15);

        assertNotNull("New root should exist: ", root1);
        assertEquals(Integer.valueOf(7), root1.getData()); //7 promoted to root
        assertNull("New root shouldn't have left child: ", root1.getLeft());
        assertNotNull("New root's right child should exist: ", root1.getRight());
        assertEquals(Integer.valueOf(20), root1.getRight().getData());

        BST<Integer> root2 = new BST<>(18);
        root2.insert(3);
        root2.insert(24);
        root2.insert(1);
        root2.insert(5);

        root2 = root2.deleteWithCopyLeft(3);

        assertNotNull("Root should still exist: ", root2);
        assertEquals(Integer.valueOf(18), root2.getData());
        assertNotNull("Root's left child should still exist: ", root2.getLeft());
        assertEquals(Integer.valueOf(1), root2.getLeft().getData()); //1 promoted to parent, rightmost node in left subtree of 3
        assertNotNull("Right child of root's left child should still exist: ", root2.getLeft().getRight());
        assertEquals(Integer.valueOf(5), root2.getLeft().getRight().getData());
    }

    public void testRotateLeft() {
        BST<Integer> root1 = new BST<>(18);
        root1.insert(3); //root only has left child

        BST<Integer> newRoot1 = root1.rotateLeft();

        assertSame(root1, newRoot1);
        assertEquals(Integer.valueOf(18), newRoot1.getData());

        BST<Integer> root2 = new BST<>(10);
        root2.insert(15);
        root2.insert(14);
        root2.insert(18);

        BST<Integer> newRoot2 = root2.rotateLeft();

        assertEquals(Integer.valueOf(15), newRoot2.getData());
        assertEquals(Integer.valueOf(10), newRoot2.getLeft().getData());
        assertEquals(Integer.valueOf(18), newRoot2.getRight().getData());
        assertEquals(Integer.valueOf(14), newRoot2.getLeft().getRight().getData());
    }

    public void testRotateRight() {
        BST<Integer> root1 = new BST<>(18);
        root1.insert(21); //root only has right child

        BST<Integer> newRoot1 = root1.rotateRight();

        assertSame(root1, newRoot1);
        assertEquals(Integer.valueOf(18), newRoot1.getData());

        BST<Integer> root2 = new BST<>(10);
        root2.insert(5);
        root2.insert(3);
        root2.insert(7);

        BST<Integer> newRoot2 = root2.rotateRight();

        assertEquals(Integer.valueOf(5), newRoot2.getData());
        assertEquals(Integer.valueOf(3), newRoot2.getLeft().getData());
        assertEquals(Integer.valueOf(10), newRoot2.getRight().getData());
        assertEquals(Integer.valueOf(7), newRoot2.getRight().getLeft().getData());
    }

}

