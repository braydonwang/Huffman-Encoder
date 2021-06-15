/* -------- IMPORTS ---------- */
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/* -------------------------- */

/**
 * [HuffmanCoding.java]
 * The program allows the user to encode data from a file using the huffman coding method.
 * The program will take the frequency of every byte in the file to create a binary tree.
 * Using this binary tree, the program can map every byte in the file to a shorter binary representation based on frequency.
 * Essentially, more frequent bytes are represented with shorter bits, while less frequent ones may have more bits.
 * The program will display into a seperate file the data's new content, including the string representation of the tree and the encoded message.
 * It is up to the other program to decode the message using the specified binary tree.
 * @author Braydon Wang
 * @version 1.0, Oct 22, 2020
 */

class HuffmanCoding {
  public static void main(String[] args) throws Exception{
    
    /** The scanner class used for input from the console. */
    Scanner sc = new Scanner(System.in);
    
    //Getting the name of the file that the user specifies
    System.out.println("Enter the name of the file that you want to read from: ");
    /** The name of the inputted file. */
    String name = sc.next();
    
    /** The map used to count the frequency of every byte in the file. */
    HashMap<Byte,Integer> frequency = new HashMap<Byte,Integer>();
    /** The file input stream class used to get input in the form of bytes. */
    FileInputStream in = null;
    
    //Getting input from the file
    try {
      //Initializing input from the specified file
      in = new FileInputStream(name);
      int c;
      
      //Reading the individual bytes in the file
      while ((c = in.read()) != -1) {
        /** The current byte read from the file. */
        byte ch = (byte)c;
        //Updating the frequency of the byte encountered
        frequency.put(ch,frequency.getOrDefault(ch,0)+1);
      }
    } finally {
      //Closing the input class
      if (in != null) {
        in.close();
      }
    }
    
    /** A priority queue used to sort the frequency of every node in the binary tree. */
    SimplePriorityQueue<Byte> q = new SimplePriorityQueue<Byte>();
    /** The binary tree used to find the new representation of every byte in the file. */
    BinaryTree<Byte> tree = new BinaryTree<Byte>();
    
    //Place every byte and its corresponding frequencies in the queue as a leaf node of the binary tree
    for (byte x: frequency.keySet()) {
      q.enqueue(x,frequency.get(x),null,null);
    }
    
    //Loop through every node in the priority queue until there is only one left
    while (q.size() > 1) {
      
      /** The smallest frequency node in the queue. */
      Node<Byte> node1 = q.dequeue(); //remove it from the queue
      /** The second smallest frequency in the queue. */
      Node<Byte> node2 = q.dequeue(); //remove it from the queue
      
      //Create a new node that has node1 and node2 as its leaves with the sum of both of their frequencies
      //Add this new node back into the priority queue
      q.enqueue(null,node1.getPriority()+node2.getPriority(),node1,node2);
    }
    
    //The last node in the queue is the root of the binary tree
    tree.setRoot(q.dequeue());
    
    /** A hashmap that stores the new binary representation of each byte. */
    HashMap<Byte,String> encodedBytes = new HashMap<Byte,String>();
    //Traverse the binary tree to find the new encoded representation of every byte
    tree.findEncodedBytes(encodedBytes);
    
    //Traverse the binary tree to get the string equivalent of the tree with brackets around nodes
    String convertedString = tree.convertToString();
    
    /** The number of letters of the file's extension. */
    int lengthOfExtension = name.length() - name.indexOf(".") - 1;
    /** The file name containing the new encoded data with the extension of .MZIP */
    String fileName = name.substring(0,name.length()-lengthOfExtension-1) + ".MZIP";
    /** The file output stream used to output bytes to a file. */
    FileOutputStream out = new FileOutputStream(fileName);
    
    //Output the filename of the original file with the extension in all caps
    for (int i = 0; i < name.length(); i++) {
      
      //Print the original file name excluding the extension as is
      if (i < name.length()-lengthOfExtension) {
        out.write(name.charAt(i)); 
      } else {
        //If the extension is already in caps, keep it as is
        if (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') {
          out.write(name.charAt(i)); 
          
        //Otherwise, convert the extension to capital letters
        } else {
          out.write(name.charAt(i)-32); 
        }
      }
    }
    
    //Carriage return
    out.write(13);
    //Moving to the next line (ascii numerical value of 'ENTER' = 10)
    out.write(10);
    
    //Ouput the converted string that represents the binary tree
    for (int i = 0; i < convertedString.length(); i++) {
      out.write(convertedString.charAt(i));
    }
    
    //Carriage return
    out.write(13);
    //Moving to the next line
    out.write(10);
    
    /** A variable used to store the number of bits in the final encoded message. */
    int numberOfBits = 0;
    //For every byte in the file, add the number of bits that its new representation will contribute
    for (byte x: frequency.keySet()) {
      //The length of the new binary representation of the byte multiplied with the frequency of that specific byte in the file
      //is how many bits that it will take up in the encoded message
      numberOfBits += frequency.get(x) * encodedBytes.get(x).length();
    }
    
    /** The number of bytes in the encoded message after calculating the number of bits. */
    int numberOfBytes = (int)Math.ceil(numberOfBits/8.0);
    /** The final encoded message with every byte filled with the original byte's corresponding representation. */
    byte[] encodedMessage = new byte[numberOfBytes];
    /** The index of the encoded message array. */
    int index = 0;
    /** The number of extra bits at the end of the encoded message. */
    int extraBits = 0;
    /** The individual byte message that is currently being added on to. */
    String byteMessage = "";
    
    //Getting the input from the original file
    try {
      
      /** Initializing the class used to take in the input, specifically in byte form */
      in = new FileInputStream(name);
      /** The current byte from the file. */
      int c;
      
      //Reading the individual bytes
      while ((c = in.read()) != -1) {
        /** The current byte read from the file. */
        byte ch = (byte)c;
        /** The new binary representation of that specific byte. */
        String str = encodedBytes.get(ch);
        
        //Add the new representation of the original byte if it does not make the current byte message's length larger than 8
        if (byteMessage.length() + str.length() <= 8) {
          byteMessage += str;
          
        //If you cannot simply add the new representation without overflowing, you must carry on remainder of the new representation
        //to the next byte message
        } else {
          /** The length of the byte message currently. */
          int length = byteMessage.length();
          //Add what you can from the new representation to the current byte message without going over 8 bits
          byteMessage += str.substring(0,8-length);
          
          //Convert the byte message from string to an actual byte stored in the byte array, encoded message
          convertStringToByte(encodedMessage,byteMessage,index);
          //Increment the index of the encoded message to progress to the next byte slot
          index++;
          
          //Create another byte message with the remaining end of the new representation
          byteMessage = str.substring(8-length);
          
          //Constantly loop until the byte message's length is less than 8 bits
          while (byteMessage.length() >= 8) {
            
            /** A temporary string used to hold the first 8 bits of the byte message. */
            String tempMessage = byteMessage.substring(0,8);
            
            //Convert the byte message from string to an actual byte stored in the byte array, encoded message
            convertStringToByte(encodedMessage,tempMessage,index);
            //Increment the index of the encoded message to progress to the next byte slot
            index++;
            
            //Create another byte message exlcuding the first 8 bits used to make the previous byte
            byteMessage = byteMessage.substring(8);
          }
        }
      }
    } finally {
      //If the byte message still has bits inside of it, there are extra bits that need to be considered
      if (byteMessage.length() > 0) {
        
        //The number of extra bits is equal to the number of bits in the byte message subtracted from 8, the maximum bits allowed in a byte
        extraBits = 8 - byteMessage.length();
        
        //Place zeros at the end to fill up the extra bits
        for (int i = 0; i < extraBits; i++) {
          byteMessage += "0";
        }
        
        //Convert the byte message from string to an actual byte stored in the byte array, encoded message
        convertStringToByte(encodedMessage,byteMessage,index);
      }
      
      //Closing the input class when doing reading input
      if (in != null) {
        in.close();
      }
    }
    
    //Output the number of extra bits into the file, converted into byte form
    out.write(extraBits+'0');
    
    //Carriage return
    out.write(13);
    //Moving to next line
    out.write(10);
    
    //Output the byte array that holds the encoded message
    out.write(encodedMessage);
    
    //Closing the output class
    out.close();
  }
  
  /**
   * This method converts a string in binary form to a byte and stores it in a byte array
   * @param encodedMessage  the byte array used to hold the new byte that has been created
   * @param message                the binary string that needs to be converted
   * @param index                     the current index of the byte array that should store the new byte value
   */
  
  public static void convertStringToByte(byte[] encodedMessage, String message, int index) {
    for (int i = 7; i >= 0; i--) {
      //If the specified character at that index is a 1, add two to the power of that index to the byte
      if (message.charAt(7-i) == '1') {
        encodedMessage[index] += 1 << i;
      }
    }
  }
}