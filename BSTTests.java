import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Binary Search Tree (BST) class.
 *
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class BSTTests {
    /** Helper method: verifies that a BinaryTree has the expected structure and contents. */
    private static <T> void verifyBT(BinaryTree<? extends T> t, T[][] contents) {
        for (int i = 0; i <= contents.length; i++) {
            int nj = (int) Math.pow(2, i);
            for (int j = 0; j < nj; j++) {
                int h = (int) Math.pow(2, i - 1);
                int k = j;
                BinaryTree<?> node = t;

                while (h > 0 && node != null) {
                    if (k >= h) node = node.getRight();
                    else node = node.getLeft();
                    k = k % h;
                    h /= 2;
                }

                // Compare expected and actual structure
                if ((i == contents.length || contents[i][j] == null) && node != null) {
                    fail("Row " + i + " position " + j +
                         " should be null but found data: " + node.getData());
                } else if (i < contents.length && contents[i][j] != null) {
                    if (node == null) {
                        fail("Row " + i + " position " + j +
                             " should be " + contents[i][j] + " but found null");
                    } else {
                        assertEquals("Row " + i + " position " + j + 
                                     " expected " + contents[i][j] + 
                                     " but got " + node.getData(),
                                     contents[i][j], node.getData());
                    }
                }
            }
        }
    }

    // Sample tests...
    @Test
    public void testBSTInsertions() {
        Integer[][] gt1 = {{5}};
        Integer[][] gt2 = {{5},{null,7}};

        BST<Integer> tree = new BST<>(5);
        verifyBT(tree, gt1);

        tree.insert(7);
        verifyBT(tree, gt2);
    }

    @Test
    public void testInsertLeftChild() {
        Integer[][] expected = {{15}, {7, null}};
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        verifyBT(root, expected);
    }
    
    @Test
    public void testInsertRightChild() {
        Integer[][] expected = {{15}, {null, 20}};
        BST<Integer> root = new BST<>(15);
        root.insert(20);
        verifyBT(root, expected);
    }
    
    @Test
    public void testInsertDuplicate() {
        Integer[][] expected = {{15}, {7, null}};
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        root.insert(7); // duplicate node
        verifyBT(root, expected);
    }

    @Test
    public void testDeleteLeaf() {
        Integer[][] initial = {{15}, {7, 20}};
        Integer[][] afterDelete = {{15}, {null, 20}};
        
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        root.insert(20);
        verifyBT(root, initial); // verify initial structure

        root = root.deleteWithCopyLeft(7);
        verifyBT(root, afterDelete); // verify structure after deletion
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        Integer[][] initial = {{15}, {7, 20}, {3, null, null, null}};
        Integer[][] afterDelete = {{15}, {3, 20}};
        
        BST<Integer> root = new BST<>(15);
        root.insert(7);
        root.insert(20);
        root.insert(3);
        verifyBT(root, initial);

        root = root.deleteWithCopyLeft(7);
        verifyBT(root, afterDelete); //3 is promoted to parent/left child of root
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        // First test case: deleting root
        Integer[][] initial1 = {{15}, {7, 20}};
        Integer[][] afterDelete1 = {{7}, {null, 20}};
        
        BST<Integer> root1 = new BST<>(15);
        root1.insert(7);
        root1.insert(20);
        verifyBT(root1, initial1);

        root1 = root1.deleteWithCopyLeft(15);
        verifyBT(root1, afterDelete1);

        // Second test case: deleting root's left child
        Integer[][] initial2 = {{18}, {3, 24}, {1, 5, null, null}};
        Integer[][] afterDelete2 = {{18}, {1, 24}, {null, 5, null, null}};
        
        BST<Integer> root2 = new BST<>(18);
        root2.insert(3);
        root2.insert(24);
        root2.insert(1);
        root2.insert(5);
        verifyBT(root2, initial2);

        root2 = root2.deleteWithCopyLeft(3);
        verifyBT(root2, afterDelete2);
    }

    @Test
    public void testRotateLeft() {
        // First case: root only has left child
        BST<Integer> root1 = new BST<>(18);
        root1.insert(3);
        BST<Integer> newRoot1 = root1.rotateLeft();
        assertSame(root1, newRoot1);

        // Second case: more complex rotation 
        Integer[][] initial = {{10}, {null, 15}, {14, 18, null, null}};
        Integer[][] afterRotate = {{15}, {10, 18}, {null, 14, null, null}};
        
        BST<Integer> root2 = new BST<>(10);
        root2.insert(15);
        root2.insert(14);
        root2.insert(18);
        verifyBT(root2, initial);

        BST<Integer> newRoot2 = root2.rotateLeft();
        verifyBT(newRoot2, afterRotate);
    }

    @Test
    public void testRotateRight() {
        // First case: root only has right child
        BST<Integer> root1 = new BST<>(18);
        root1.insert(21);
        BST<Integer> newRoot1 = root1.rotateRight();
        assertSame(root1, newRoot1);

        // Second case: more complex rotation
        Integer[][] initial = {{10}, {5, null}, {3, 7, null, null}};
        Integer[][] afterRotate = {{5}, {3, 10}, {null, null, 7, null}};
        
        BST<Integer> root2 = new BST<>(10);
        root2.insert(5);
        root2.insert(3);
        root2.insert(7);
        verifyBT(root2, initial);

        BST<Integer> newRoot2 = root2.rotateRight();
        verifyBT(newRoot2, afterRotate);
    }
}