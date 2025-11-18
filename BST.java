/**
 * Implements binary search trees.
 *
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class BST<E extends Comparable<E>> extends BinaryTree<E> implements BST_Ops<E> {
    
    /** This constructor creates a leaf node */
    public BST(E data) {
        super(data);
    }
    
    /**
     * Creates branch node with left and right children
     * @param data question for this node
     * @param left left child of node
     * @param right right child of node
     */
    public BST(E data, BinaryTree<E> left, BinaryTree<E> right) {
        super(data);
        this.setLeft(left); //checks that left is type BST
        this.setRight(right); //checks that right is type BST
    }

    /** This constructor creates a deep copy of the entire tree structure */ 
    public BST(BinaryTree<E> tree) {
        super(tree.getData());
        this.setLeft(tree.getLeft());
        this.setRight(tree.getRight());
    }

    /** Override inherited manipulator to accept only BST */
    public void setLeft(BinaryTree<E> left) {
        if ((left==null)||(left instanceof BST<E>)) {
            super.setLeft(left);
        } else {
            throw new UnsupportedOperationException("Only BST children allowed");
        }
    }

    /** Override inherited manipulator to accept only BST */
    public void setRight(BinaryTree<E> right) {
        if ((right==null)||(right instanceof BST<E>)) {
            super.setLeft(right);
        } else {
            throw new UnsupportedOperationException("Only BST children allowed");
        }
    }

    /**
     *  Returns the node of the given element, or null if not found
     *
     *  @param query The element to search
     *  @return the node of the given element, or null if not found
     */
    public BST<E> lookup(E data){
        //stop condition: current node is null
        if (this.getData() == null) {
            return null;
        }

        //compare query with current node's data
        int comparison = data.compareTo(this.getData());

        //stop condition: found element
        if (comparison == 0) {
            return this;
        } else if (comparison < 0) { //recursive step: search left subtree
            if (this.getLeft() == null) {
                return null; //element wasn't found
            }

            return ((BST<E>) this.getLeft()).lookup(data);
        } else { //recursive step: search right subtree
            if (this.getRight() == null) {
                return null; //element wasn't found
            }

            return ((BST<E>) this.getRight()).lookup(data);
        }
        
    }

    /**
     *  Inserts a new node into the tree
     *
     *  @param data The element to insert
     */
    public void insert(E data) {
        if (this.getData() == null) {
            throw new IllegalStateException("Can't insert into an empty root node.");
        }

        //compares new element with current node's data
        int comparison = data.compareTo(this.getData());

        //if duplicate found - tree doesn't change
        if (comparison == 0) {
            return;
        } else if (comparison < 0) { //insert in left subtree
            if (this.getLeft() == null) {
                this.setLeft(new BST<E>(data)); //create new node and set it as left child
            } else {
                ((BST<E>) this.getLeft()).insert(data); //recurse into left subtree
            }
        } else { //insert in right subtree
            if (this.getRight() == null) {
                this.setRight(new BST<E>(data)); //create new node and set it as right child
            } else {
                ((BST<E>) this.getRight()).insert(data); //recurse into right subtree
            }
        }
    }

    /**
     *  Deletes the specified element from the tree
     *  Returns the modified tree because the root node 
     *  may have changed
     *  
     *  @param evictee The element to delete
     *  @return tree as modified
     */
    public BST<E> deleteWithCopyLeft(E evictee) {

    }

    /**
     *  Apply left rotation
     *  Returns the modified tree because the root node 
     *  may have changed
     *
     *  @return tree as modified
     */
    public BST<E> rotateLeft() {

    }

    /**
     *  Apply right rotation
     *  Returns the modified tree because the root node 
     *  may have changed
     *
     *  @return tree as modified
     */
    public BST<E> rotateRight() {

    }
}
