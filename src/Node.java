/**
 * [Node.java]
 * The program refers to the node class that is used by both the binary tree and the priority queue.
 * Every node has both a left and right child, as well as a next and previous node to help in the queue.
 * @author Braydon Wang
 * @version 1.0, Oct 22, 2020
 */

class Node<T> {
  
  /** The item value that the node stores. */
  private T item;
  /** The priority value of the node. */
  private int priority;
  /** The node's left child. */
  private Node<T> left;
  /** The node's right child. */
  private Node<T> right;
  /** The node's next element in the queue. */
  private Node<T> next;
  /** The node's previous element in the queue. */
  private Node<T> prev;
  
  /**
   * Creates an object from the node class.
   * @param item       The item value that the node stores
   * @param priority  The priority/frequency of the node
   * @param left         The node's left child
   * @param right      The node's right child
   * @param next       The node's next element in the queue
   * @param prev       The node's previous element in the queue
   */
  
  Node(T item, int priority, Node<T> left, Node<T> right, Node<T> next, Node<T> prev) {
    this.item = item;
    this.priority = priority;
    this.left = left;
    this.right = right;
    this.next = next;
    this.prev = prev;
  }
  
  /**
   * This method returns the value of the node's item
   * @return the item value that the node stores
   */
  
  public T getItem() {
    return this.item;
  }
  
  /**
   * This method returns the priority of the node
   * @return the priority of the node
   */
  
  public int getPriority() {
    return this.priority;
  }
  
  /**
   * This method returns the node's left child
   * @return the node's left child
   */
  
  public Node<T> getLeft() {
    return this.left;
  }
  
  /**
   * This method returns the node's right child
   * @return the node's right child
   */
  
  public Node<T> getRight() {
    return this.right;
  }
  
  /**
   * This method returns the node's next node
   * @return the node's next node
   */
  
  public Node<T> getNext() {
    return this.next;
  }
  
  /**
   * This method returns the node's previous node
   * @return the node's previous node
   */
  
  public Node<T> getPrev() {
    return this.prev;
  }
  
  /**
   * This method sets the next node to a new node
   * @param next  the new next node
   */
  
  public void setNext(Node<T> next) {
    this.next = next;
  }
  
  /**
   * This method sets the previous node to a new node
   * @param prev  the new previous node
   */
  
  public void setPrev(Node<T> prev) {
    this.prev = prev;
  }
  
  /**
   * This method checks if the node is a leaf node or not
   * @return true if the node is a leaf, false otherwise
   */
  
  public boolean isLeaf() {
    if (this.left == null && this.right == null) {
      return true;
    }
    return false;
  }
}