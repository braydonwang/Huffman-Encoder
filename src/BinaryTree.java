/* -------- IMPORTS ---------- */
import java.util.HashMap;
/* -------------------------- */

/**
 * [BinaryTree.java]
 * The program holds the binary tree class that is used to map every byte in the original file to a new representation.
 * The connection with the left child represents the bit '0', while the right child represents the bit '1'.
 * @author Braydon Wang
 * @version 1.0, Oct 22, 2020
 */

class BinaryTree<T> {
  
  /** The root node of the binary tree. */
  private Node<T> root;
  
  /**
   * Creates an object from the binary tree class.
   */
  
  BinaryTree() {
    root = null;
  }
  
  /**
   * This method sets the root of the tree to the specified new root
   * @param root  the new root of the tree
   */
  
  public void setRoot(Node<T> root) {
    this.root = root;
  }
  
  /**
   * This method finds the new encoded representation of each byte
   * @param encodedBytes  the hashmap used to store every byte's corresponding representation
   */
  
  public void findEncodedBytes(HashMap<Byte,String> encodedBytes) {
    recursiveFindEncodedBytes(encodedBytes,root,"");
  }
  
  /**
   * This method recursively traverses the tree to find the encoded representation of every byte
   * @param encodedBytes  the hashmap used to store every byte's corresponding representation
   * @param node                 the current node that the method is looking at
   * @param newByte            the new representation of the byte so far from the traversal
   */
  
  public void recursiveFindEncodedBytes(HashMap<Byte,String> encodedBytes, Node<T> node, String newByte) {
    if (node.isLeaf()) {
      encodedBytes.put((Byte)node.getItem(),newByte);
      return;
    }
    //Moving to the left child adds the bit '0' to the end of the new representation
    recursiveFindEncodedBytes(encodedBytes, node.getLeft(), newByte+"0");
    //Moving to the right child adds the bit '1' to the end of the new representation
    recursiveFindEncodedBytes(encodedBytes, node.getRight(), newByte+"1");
  }
  
  /**
   * This method returns the converted string version of the binary tree
   * @return the converted string
   */
  
  public String convertToString() {
    return recursiveConvertToString(root);
  }
  
  /**
   * This method recursively traverses the tree to convert it into a string representation
   * @param node  the current node being referenced
   * @return           the converted string
   */
  
  public String recursiveConvertToString(Node<T> node) {
    if (node.isLeaf()) {
      return ""+node.getItem();
    }
    //Enclose the left and right child with brackets
    return "(" + recursiveConvertToString(node.getLeft()) + " " + recursiveConvertToString(node.getRight()) + ")";
  }
}