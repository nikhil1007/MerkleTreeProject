/**
 * @author Nikhil Kashyap
 */
package edu.cmu.andrew.nikhilka;
import java.util.Random;

/**
 * This class maintains a list of Integer values as an increasing singlyLinkedList
 */
public class OrderedLinkedListOfIntegers {
    private ObjectNode head;
    private ObjectNode iterator;
    private ObjectNode link;
    private Object data;

    /**
     * head - points to the first node of the linked list
     * iterator - used to iterate through the linked list
     */
    public OrderedLinkedListOfIntegers(){
        head = null;
        iterator = null;
        data = null;
        link = null;
    }

    /**
     * Checks if the iterator is pointing to the last element of a Ordered LinkedList or not
     * Theta(1), constant time complexity
     * @return
     * Boolean value false if the iterator points to the last element of the Ordered LinkedList, else true.
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
     */
    public void next(){
        iterator = iterator.getLink();
    }

    /**
     * Points the iterator to the first node on the linked List (Head)
     * Theta(1), constant time complexity
     */
    public void reset(){

        iterator = head;
    }

    /**
     * Adds new ObjectNode into ordered linked list maintaining the increasing order of Integer data.
     * Theta(n), linear time complexity
     * @param number
     * Integer value to insert into the ordered linked list
     * @precondition
     * Needs to be a valid Integer value.
     * @postcondition
     * A new ObjectNode with data "number" will be added to linked list such that its data continue to be in ascending
     * order.
     */
    public void sortedAdd(int number){
        ObjectNode prev = null;

        //the list is empty
        if(this.isEmpty()){
            head = iterator = new ObjectNode(number, null);
        }
        //edge case: if number has to be placed at head
        else if(number <= (int)this.head.getData()){
            head = new ObjectNode(number,head);
        }
        //list is not empty
        else{
            this.reset();

            //iterate till we find a number greater than to_insert_number
            while(this.hasNext() && number > (int) iterator.getData()){
                prev = iterator;
                this.next();
            }

            ObjectNode temp = new ObjectNode(number, iterator);
            prev.setLink(temp);
        }
    }

    /**
     * Given 2 ordered linked list this method merges them into one list maintaining the ascending order of ObjectNode
     * data i.e. Integers
     * Theta(n) - linear time complexity
     *
     * @precondition
     * The lists should have valid Object data as valid Integer values. Both list should be null terminated
     * @return
     * Merged List of type OrderedLinkedListOfIntegers with ascending order of ObjectNode data
     */
    public static OrderedLinkedListOfIntegers merge(OrderedLinkedListOfIntegers List_1, OrderedLinkedListOfIntegers List_2){
        OrderedLinkedListOfIntegers mergedList = new OrderedLinkedListOfIntegers();

        if(List_1.isEmpty()){
            return List_2;
        }
        if(List_2.isEmpty()){
            return List_1;
        }

        List_1.reset();
        List_2.reset();

        if((int) List_1.head.getData() <= (int) List_2.head.getData()){
            mergedList.head = mergedList.iterator = List_1.head;
            List_1.next();
        }
        else{
            mergedList.head = mergedList.iterator = List_2.head;
            List_2.next();
        }

        while (List_1.hasNext() && List_2.hasNext()){
            if((int) List_1.iterator.getData() <= (int) List_2.iterator.getData()){
                mergedList.iterator.setLink(List_1.iterator);
                List_1.next();
                mergedList.next();
            }
            else{
                mergedList.iterator.setLink(List_2.iterator);
                List_2.next();
                mergedList.next();
            }
        }

        if(List_1.isEmpty()){
            mergedList.iterator.setLink(List_2.iterator);
        }
        else if(List_2.isEmpty()){
            mergedList.iterator.setLink(List_1.iterator);
        }
    return mergedList;
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
     * Method to determine if SinglyLinkedList object is empty.
     * Theta(1), constant time complexity.
     * @return
     * Boolean value true if the singlyLinkedList has no nodes, else false
     */
    private boolean isEmpty() {
        return head == null;
    }

    public static void main(String[] args) {
        OrderedLinkedListOfIntegers list_1 = new OrderedLinkedListOfIntegers();
        OrderedLinkedListOfIntegers list_2 = new OrderedLinkedListOfIntegers();
        Random rand = new Random();

        for(int i = 0; i < 20; i++){
            int rand_int1 = rand.nextInt(1000);
            int rand_int2 = rand.nextInt(1000);
            list_1.sortedAdd(rand_int1);
            list_2.sortedAdd(rand_int2);
        }

        System.out.println("Elements in list_1: " + list_1);
        System.out.println("Elements in list_2: " + list_2);
        System.out.println("Elements after merge: " + merge(list_1,list_2));
    }
}
