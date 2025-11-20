/**
 * Implements binary search trees. Extends BinaryTree and implements BST operations.
 * @param <E> type of elements stored in tree, needs to implement Comparable
 * @author Caitlyn Kim
 * @version Fall 2025
 */
public class BST<E extends Comparable<E>> extends BinaryTree<E> implements BST_Ops<E> {
    
    /**
     * This constructor creates a leaf node
     * @param data element to store in root node
     * @return new BST instance with only root
     */
    public BST(E data) {
        super(data);
    }
    
    /**
     * Creates branch node with left and right children
     * @param data element to store in root
     * @param left left child of node
     * @param right right child of node
     * @return new BST instance with children
     */
    public BST(E data, BinaryTree<E> left, BinaryTree<E> right) {
        super(data);
        this.setLeft(left); //checks that left is type BST
        this.setRight(right); //checks that right is type BST
    }

    /**
     * This constructor creates a deep copy of the entire tree structure
     * @param tree tree to copy
     * @return new BST instance that's a copy of input tree
     */
    public BST(BinaryTree<E> tree) {
        super(tree.getData());
        this.setLeft(tree.getLeft());
        this.setRight(tree.getRight());
    }

    /**
     * Override inherited manipulator to accept only BST. Sets left child.
     * @left left subtree to set
     * @throws UnsupportedOperationException if left isn't a BST instance
     */
    public void setLeft(BinaryTree<E> left) {
        if ((left==null)||(left instanceof BST<E>)) {
            super.setLeft(left);
        } else {
            throw new UnsupportedOperationException("Only BST children allowed");
        }
    }

    /**
     * Override inherited manipulator to accept only BST. Sets right child.
     * @right right subtree to set
     * @throws UnsupportedOperationException if right isn't BST instance
     */
    public void setRight(BinaryTree<E> right) {
        if ((right==null)||(right instanceof BST<E>)) {
            super.setRight(right);
        } else {
            throw new UnsupportedOperationException("Only BST children allowed");
        }
    }

    /**
     *  Returns the node of the given element, or null if not found
     *
     *  @param data The element to search
     *  @return the node of the given element, or null if not found
     */
    public BST<E> lookup(E data){
        //stop condition: current node is null
        if (this.getData() == null) {
            return null;
        }

        //compare element to search with current node's data
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
     *  @param data The element to insert
     * @throws IllegalStateException if attempt to insert into an empty root
     */
    public void insert(E data) {
        if (this.getData() == null) {
            throw new IllegalStateException("Can't insert into an empty root node.");
        }

        //compares new element with current node's data
        int comparison = data.compareTo(this.getData());

        if (comparison < 0) { //insert in left subtree
            if (this.getLeft() == null) {
                this.setLeft(new BST<E>(data)); //create new node and set it as left child
            } else {
                ((BST<E>) this.getLeft()).insert(data); //recurse into left subtree
            }
        } else if (comparison > 0) { //insert in right subtree
            if (this.getRight() == null) {
                this.setRight(new BST<E>(data)); //create new node and set it as right child
            } else {
                ((BST<E>) this.getRight()).insert(data); //recurse into right subtree
            }
        } else { //if duplicate found - tree doesn't change
            return;
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
        //compares element to delete with current node's data
        int comparison = evictee.compareTo(this.getData());

        if (comparison < 0) {
            if (this.getLeft() != null) {
                this.setLeft(((BST<E>) this.getLeft()).deleteWithCopyLeft(evictee));
            }
            return this;
        } else if (comparison > 0) {
            if (this.getRight() != null) {
                this.setRight(((BST<E>) this.getRight()).deleteWithCopyLeft(evictee));
            }
    
            return this;
        } else { //comparison == 0
            return deleteNode(this); //found the node to delete and use copy-left deletion method
        }
    }

    /**
     * Method that performs the copy-left deletion
     * @param evictee
     * @return tree as modified
     */
    private BST<E> deleteNode(BST<E> evictee) {
        //if node is a leaf
        if (evictee.getLeft() == null && evictee.getRight() == null) {
            return null;
        } else if (evictee.getRight() == null) { //if node only has left child
            return ((BST<E>) evictee.getLeft());
        } else if (evictee.getLeft() == null) { //if node only has right child
            return ((BST<E>) evictee.getRight());
        } else { //if node has left and right children, use copy-left method
            BST<E> rightMost = findRightMost((BST<E>) evictee.getLeft()); //find rightmost node in left subtree
            evictee.setData(rightMost.getData()); //copy rightMost node's data to current node
            evictee.setLeft(((BST<E>) evictee.getLeft()).deleteWithCopyLeft(rightMost.getData())); //access left subtree and delete rightmost node

            return evictee;
        }
    }

    /**
     * Finds rightmost node/biggest node in a subtree
     * @param node parent of subtree
     * @return rightmost node in a subtree
     */
    private BST<E> findRightMost(BST<E> node) {
        BST<E> current = node;
        while (current.getRight() != null) {
            current = (BST<E>) current.getRight();
        }
        return current;
    }

    /**
     *  Apply left rotation
     *  Returns the modified tree because the root node 
     *  may have changed
     *
     *  @return tree as modified
     */
    public BST<E> rotateLeft() {
        //check if rotation is possible (root must have right child)
        if (this.getRight() == null) {
            return this; //can't rotate, return current node
        }

        //references to nodes involved
        BST<E> oldRoot = this;
        BST<E> newRoot = (BST<E>) oldRoot.getRight();
        BST<E> leftSubtreeOfNewRoot = (BST<E>) newRoot.getLeft();

        //newRoot's left child becomes the oldRoot
        newRoot.setLeft(oldRoot);

        //oldRoot's right child becomes what was newRoot's left child
        oldRoot.setRight(leftSubtreeOfNewRoot);

        //return new root of rotated tree
        return newRoot;
    }

    /**
     *  Apply right rotation
     *  Returns the modified tree because the root node 
     *  may have changed
     *
     *  @return tree as modified
     */
    public BST<E> rotateRight() {
        //check if rotation is possible (root must have left child)
        if (this.getLeft() == null) {
            return this; //can't rotate, return current node
        }

        //references to nodes involved
        BST<E> oldRoot = this;
        BST<E> newRoot = (BST<E>) oldRoot.getLeft();
        BST<E> rightSubtreeOfNewRoot = (BST<E>) newRoot.getRight();

        //newRoot's right child becomes the oldRoot
        newRoot.setRight(oldRoot);

        //oldRoot's left child becomes what was newRoot's right child
        oldRoot.setLeft(rightSubtreeOfNewRoot);

        //return new root of rotated tree
        return newRoot;
    }

}
