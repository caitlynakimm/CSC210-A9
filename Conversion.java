/**
 * Class to implement tree conversions
 *
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class Conversion {
  /** Converts a sorted array to a balanced BST */
  public static <T extends Comparable<T>> BST<T> arrayToBST(T[] arr) {
    return arrayToBSTRecur(arr, 0, arr.length - 1);
  }

  public static <T extends Comparable<T>> BST<T> arrayToBSTRecur(T[] arr, int lowIndex, int highIndex) {
    if (lowIndex > highIndex) {
      return null;
    }

    int mid = lowIndex + (highIndex - lowIndex)/2;

    BST<T> root = new BST<> (arr[mid]);

    //divides active portion around middle element/pivot
    BST<T> leftHalfOfRoot = root.arrayToBSTRecur(arr, lowIndex, mid - 1);
    BST<T> RightHalfOfRoot = root.arrayToBSTRecur(arr, mid + 1, highIndex);

    return root;
  }

  /** Convert BinaryTree to DLL */
  public static <S extends Comparable<S>> DLL<S> binaryTreeToDLL(BinaryTree<S> t) {
    return null; // replace this with your implementation
  }
}
