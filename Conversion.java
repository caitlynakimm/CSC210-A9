/**
 * Class to implement tree conversions. Allows for sorted arrays to convert to balanced BSTs and binary trees to doubly linked lists.
 *
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class Conversion {
  /**
   * Converts a sorted array to a balanced BST
   * @param <T> type of elements stored in tree and array, must implement Comparable
   * @param arr sorted array of elements to convert
   * @return balanced BST<T> with all elements from the array or null if array is null or empty
   */
  public static <T extends Comparable<T>> BST<T> arrayToBST(T[] arr) {
    if (arr == null || arr.length == 0) {
      return null;
    }
    return arrayToBSTRecur(arr, 0, arr.length - 1);
  }

  /**
   * Recursive helper that builds balanced BST from a sorted array portion
   * @param <T> type of elements stored in tree, must implement Comparable
   * @param arr sorted array of elements
   * @param lowIndex starting index of current segment
   * @param highIndex ending index of current segment
   * @return root BST<T> node of balanced subtree created
   */
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

  /**
   * Convert BinaryTree to DLL
   * @param <S> type of elements stored in DLL and binary tree, must implement Comparable
   * @param t root of binary tree to convert
   * @return DLL<S> with all elements from binary tree in in-order sequence
   */
  public static <S extends Comparable<S>> DLL<S> binaryTreeToDLL(BinaryTree<S> t) {
    if (t == null) {
      return new DLL<>();
    }
    
    //convert tree to DLL and get the head and tail
    BinaryTree<S>[] result = convertToDLLRecur(t);
    return new DLL<>(result[0], result[1]);
  }

  /**
   * Recursive helper to convert binary tree subtree to a DLL segment 
   * and returns head and tail
   * @param <S> type of elements stored in array of binary trees and binary trees
   * @param t root of current binary tree subtree
   * @return array of two BinaryTree<S> elements
   */
  public static <S extends Comparable<S>> BinaryTree<S>[] convertToDLLRecur(BinaryTree<S> t) {
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

    //return the new head and new tail as an array
    @SuppressWarnings("unchecked")
    BinaryTree<S>[] result = new BinaryTree[2];
    result[0] = head;
    result[1] = tail;
    return result;
  }
}
