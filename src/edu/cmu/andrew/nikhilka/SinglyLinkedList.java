/**
 * @author Nikhil Kashyap
 */
package edu.cmu.andrew.nikhilka;

/**
 * This class implements singly linked list of BigIntegers in java. Each node on the list is of type ObjectNode. Each
 * ObjectNode holds a pointer to the next node in the list.
 */
public class SinglyLinkedList {

    /**
     * Head points to the first node of the singly linked list
     * Tail points to the last node of the singly linked list
     * Iterator is used to traverse through the nodes in the linked list
     */
    private ObjectNode head;
    private ObjectNode tail;
    private ObjectNode iterator;
    private int countNodes;

    public SinglyLinkedList(){
        head = null;
        tail = null;
        iterator = null;
    }

    /**
     * Adds a new node at the left most position of the linked list
     * Theta(1), constant time complexity
     * @param c
     * It is the Object to be stored in the data field of ObjectNode
     * @precondition
     * A valid Object is passed to the method's argument
     * @postcondition
     * The method updates the SinglyLinkedList object to insert a new ObjectNode object as head.
     */
    public void addAtFrontNode(Object c){
        head = new ObjectNode(c, head);
        countNodes++;
    }

    /**
     * Adds a new node at the right most position of the linked list
     * Theta(1), constant time complexity
     * @param c
     * It is the Object to be stored in the data field of ObjectNode
     * @precondition
     * A valid Object is passed to the method's argument
     * @postcondition
     * The method updates the SinglyLinkedList object to insert a new ObjectNode object as tail.
     */
    public void addAtEndNode(Object c){
        ObjectNode new_last = new ObjectNode(c, null);

        if (this.isEmpty()) {
            head = new_last;
        }
        else {
            tail.setLink(new_last);
        }
        countNodes ++;
        tail = new_last;
    }

    /*
    Keeps count of nodes in a linked list
    Theta(1), constant time complexity
     */
    public int countNodes(){
        return countNodes;
    }

    /**
     * To access a node in a singlyLinkedList at a specific index
     * Theta(n), linear time complexity
     * @param i
     * Index / position of ObjectNode in singlyLinkedList
     * @precondition
     * The value of i should be less than countNodes
     * @return
     * The data of the ObjectNode at position "i" of the singlyLinkedList
     */
    public Object getObjectAt(int i){
        int index = 0;
        ObjectNode cursor = head;

        while(index != i) {
            cursor = cursor.getLink();
            index++;
        }
    return cursor.getData();
    }

    /**
     * Method to return the data of ObjectNode object which is the tail of the SinglyLinkedList.
     * Theta(1), constant time complexity.
     * @return
     * Data of type Object contained in the tail of SinglyLinkedList
     */
    public Object getLast(){

        return tail.getData();
    }

    public ObjectNode getHead(){
        return head;
    }

    /**
     * Method to return the Object data of the ObjectNode objects as a String.
     * Theta(n), linear time complexity.
     * @Overrides
     * toString in class java.lang.Object
     * @precondition
     * null characters will be disregarded. Must be valid, null-terminating SinglyLinkedList object.
     * @postcondition
     * returns a String containing the Objects in the list.
     */
    public String toString(){
        return head.toString();
    }

    /**
     * Checks if the iterator is pointing to the last element of a singlyLinkedList or not
     * Theta(1), constant time complexity
     * @return
     * Boolean value false if the iterator points to the last element of the singlyLinkedList, else true.
     */
    public boolean hasNext(){
        return iterator != null;
    }

    /**
     * To iterate through the linked list from left to right.
     * Theta(1), constant time complexity
     * @precondition
     * The hasNext() is True
     * @postcondition
     * The iterator points to the next node in the linked list
     * @return
     * Object data of ObjectNode iterator was pointing to.
     */
    public Object next(){
        ObjectNode temp = iterator;
        iterator = iterator.getLink();
        return temp.getData();
    }

    /**
     * Points the iterator to the first node on the linked List (Head)
     * Theta(1), constant time complexity
     */
    public void reset(){
        iterator = head;
    }

    /**
     * Method to determine if SinglyLinkedList object is empty.
     * Theta(1), constant time complexity.
     * @return
     * Boolean value true if the singlyLinkedList has no nodes, else false
     */
    private boolean isEmpty() {
        return head == null && tail == null;
    }

    /**
     * Main class to demonstrate the working of the instance methods of SinglyLinkedList class.
     */
    public static void main(String[] args) {
        String alphabets = "hilkin";
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();

        //Demo using addAtEnd and Front
        for(int i = 0; i < alphabets.length(); i++){
            if(i < alphabets.length()/2)
                singlyLinkedList.addAtEndNode(alphabets.charAt(i));
            else
                singlyLinkedList.addAtFrontNode(alphabets.charAt(i));
        }

        //toString
        System.out.println("Singly LinkedList: " + singlyLinkedList);

        //counting nodes
        System.out.println("Number of nodes: " + singlyLinkedList.countNodes());

        //objectAt
        System.out.println("Node at position 3: " + singlyLinkedList.getObjectAt(3));

        //getLast
        System.out.println("Last node of the list: " + singlyLinkedList.getLast());

        //hasNext, next, reset
        singlyLinkedList.reset();
        System.out.print("Singly LinkedList: ");
        while (singlyLinkedList.hasNext()){
            System.out.print(singlyLinkedList.next());
        }
    }
}
