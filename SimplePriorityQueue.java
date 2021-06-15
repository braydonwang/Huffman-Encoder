/**
 * [SimplePriorityQueue.java]
 * The program holds the priority queue class that sorts the frequency of every node in the binary tree in ascending order.
 * It has a head and a tail node that represents the front and the end of the queue.
 * The queue can have items added into it and will remove the highest priority node in the queue.
 * In this case, the lower the frequency, the higher the priority and vice versa.
 * @author Braydon Wang
 * @version 1.0, Oct 22, 2020
 */

class SimplePriorityQueue<T>{
  
  /** The head node of the queue. */
  private Node<T> head;
  /** The tail node of the queue. */
  private Node<T> tail;
  
  /**
   * Creates an object from the priority queue class.
   */
  
  SimplePriorityQueue(){
    head = null;
    tail = null;
  }
  
  /**
   * This method adds a new node to the queue in the right spot to keep the queue sorted by priority
   * @param item       the item that the new node holds
   * @param priority  the priority of the new node
   * @param left         the new node's left child
   * @param right      the new node's right child
   */
  
  public void enqueue(T item, int priority, Node<T> left, Node<T> right) {
    
    //If there is nothing in the queue, make the head equal to the new node added
    if (head == null) {
      head = new Node<T>(item,priority,left,right,null,null);
      
    //If the queue only holds one item, make either the tail or head equal to the new node depending on its priority
    } else if (tail == null) {
      
      //Make the tail equal to the new node if it has a higher priority than the current head node
      if (priority > head.getPriority()){
        tail = new Node<T>(item,priority,left,right,null,head);
        head.setNext(tail);
        
      //Make the head equal to the new node if it has a lower priority than the current head node
      } else {
        tail = head;
        head = new Node<T>(item,priority,left,right,tail,null);
      }
      
      //If there are two or more items in the queue, add it in whenever the priority is less than the next node
    } else {
      
      /** A temporary node to hold the current node being referenced. */
      Node<T> tempNode = head;
      
      //if the priority is less than the head, replace the head with the new node
      if (priority < head.getPriority()) {
        head = new Node<T>(item,priority,left,right,tempNode,null);
        tempNode.setPrev(head);
        
      //if the priority is greater than or equal to the tail, replace the tail with the new node
      } else if (priority >= tail.getPriority()) {
        tempNode = tail;
        tail = new Node<T>(item,priority,left,right,null,tempNode);
        tempNode.setNext(tail);
        
        //If the new node fits somewhere between the head and the tail, loop through the queue starting from the head
      } else {
        //loop through every node until you reach the tail
        while (tempNode.getNext() != null) {
          /** The next node of the queue. */
          Node<T> nextNode = tempNode.getNext();
          
          //Make the new node fit in between the current node and the next node if its priority lies between theirs
          if (nextNode.getPriority() >= priority) {
            tempNode.setNext(new Node<T>(item,priority,left,right,nextNode,tempNode));
            nextNode.setPrev(new Node<T>(item,priority,left,right,nextNode,tempNode));
            return;
          }
          
          //Progress to the next node in the queue
          tempNode = tempNode.getNext();
        }
      }
    }
  }
  
  /**
   * This method removes the first node in the queue, also known as the node with the highest priority/lowest frequency
   * @return the node that was removed
   */
  
  public Node<T> dequeue() {
    
    //if there are no nodes in the queue, return null
    if (head == null) {
      return null;
    }
    
    /** The temporary node that is currently being referenced. */
    Node<T> tempNode = head;
    //the head is now the next node that it refers to
    head = tempNode.getNext();
    //if the head's next node is the tail, remove the tail completely
    if (tempNode.getNext() == tail) {
      tail = null;
    }
    
    //return the node that was removed
    return tempNode;
  }
  
  /**
   * This method returns the size of the priority queue
   * @return the size of the queue
   */
  
  public int size() {
    
    //if there are no elements, return a size of 0
    if (head == null) {
      return 0;
      
    //if there are only one element, return a size of 1
    } else if (tail == null) {
      return 1;
      
    //if there are two or more elements in the queue, loop through every single node in the queue
    } else {
      /** A counter used to count the number of nodes encountered so far. */
      int count = 1;
      /** A temporary node that is currently being referenced. */
      Node<T> tempNode = head;
      
      //loop through every node until you reach the tail
      while (tempNode.getNext() != null) {
        //progress to the next node
        tempNode = tempNode.getNext();
        //increase the counter by 1
        count++;
      }
      
      //return the final value of the number of nodes encountered
      return count;
    }
  }
}
