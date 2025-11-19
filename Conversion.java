/**
 * Class to implement tree conversions
 *
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class Conversion {
  /** Converts a sorted array to a balanced BST */
  public static <T extends Comparable<T>> BST<T> arrayToBST(T[] arr) {
    if (arr == null || arr.length == 0) {
      return null;
    }
    return arrayToBSTRecur(arr, 0, arr.length - 1);
  }

  public static <T extends Comparable<T>> BST<T> arrayToBSTRecur(T[] arr, int lowIndex, int highIndex) {
    //base case: current segment is empty
    if (lowIndex > highIndex) {
      return null;
    }

    int mid = lowIndex + (highIndex - lowIndex)/2; //find middle element to keep balance

    BST<T> root = new BST<> (arr[mid]); //create BST with middle element as the root

    //divides active portion around middle element/pivot and recursively building left and right subtrees
    BST<T> leftSubtree = arrayToBSTRecur(arr, lowIndex, mid - 1);
    BST<T> rightSubtree = arrayToBSTRecur(arr, mid + 1, highIndex);

    //make subtrees left and right children of root
    root.setLeft(leftSubtree);
    root.setRight(rightSubtree);

    return root;
  }

  /** Convert BinaryTree to DLL */
  public static <S extends Comparable<S>> DLL<S> binaryTreeToDLL(BinaryTree<S> t) {
    if (t == null) {
      return new DLL<>();
    }
    
    //convert tree to DLL and get the head and tail
    BinaryTree<S>[] result = convertToDLLRecur(t);
    return new DLL<>(result[0], result[1]);
  }

  public static <S extends Comparable<S>> DLL<S> convertToDLLRecur(BinaryTree<S> t) {
    if (t == null) {
      return null;
    }

    BinaryTree<S>[] leftList = convertToDLLRecur(t.getLeft()); //convert left subtree to DLL
    BinaryTree<S>[] rightList = convertToDLLRecur(t.getRight()); //convert right subtree to DLL

    //initialize head and tail (of the new list) as the root
    BinaryTree<S> head = t;
    BinaryTree<S> tail = t;

    //connect leftList to the left of the root
    if (leftList != null) {
      leftList[1].setRight(t); //leftList's tail should point to root
      t.setLeft(leftList[1]); //root should point to leftList's tail because DLL
      head = leftList[0]; //new head is leftList's head 
    }

    //connect rightList to the right of the root
    if (rightList != null) {
      t.setRight(rightList[0]); //root should point to rightList's head
      rightList[0].setLeft(t); //rightList's head should point to root
      tail = rightList[1]; //new tail is rightList's tail
    }

    //return the new head and new tail of the combined list
    //@SuppressWarnings("unchecked")
    BinaryTree<S>[] result = new BinaryTree[2];
    result[0] = head;
    result[1] = tail;
    return result;
  }
}
