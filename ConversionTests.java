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

    //Unit Tests
    @Test
    public void testArrayToEmptyBST() {
        Integer[] empty = {};
        BST<Integer> result = Conversion.arrayToBST(empty);
        assertNull(result);
    }

    @Test
    public void testArrayToBST() {
        Integer[] array = {2, 4, 7};
        BST<Integer> result = Conversion.arrayToBST(array);
        
        assertEquals(Integer.valueOf(4), result.getData());
        assertEquals(Integer.valueOf(2), result.getLeft().getData());
        assertEquals(Integer.valueOf(7), result.getRight().getData());
    }

    @Test
    public void testEmptyBinaryTreeToDLL() {
        DLL<Integer> result = Conversion.binaryTreeToDLL(null);
        assertNull(result.getHead());
        assertNull(result.getTail());
    }

    @Test
    public void testBinaryTreeToDLL(){
        BST<Integer> tree = new BST<>(5);
        tree.insert(2);
        tree.insert(9);

        DLL<Integer> result = Conversion.binaryTreeToDLL(tree);

        //DLL should be 2 <-> 5 <-> 9
        BinaryTree<Integer> head = result.getHead();
        BinaryTree<Integer> middle = head.getRight();
        BinaryTree<Integer> tail = middle.getRight();

        assertEquals(Integer.valueOf(2), head.getData());
        assertEquals(Integer.valueOf(5), middle.getData());
        assertEquals(Integer.valueOf(9), tail.getData());

        assertNull(head.getLeft());
        assertEquals(head.getRight(), middle);
        assertEquals(middle.getLeft(), head);
        assertEquals(middle.getRight(), tail);
        assertEquals(tail.getLeft(), middle);
        assertNull(tail.getRight());
    }

}

