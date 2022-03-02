/**
 * @author Nikhil Kashyap
 */

package edu.cmu.andrew.nikhilka;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * This class reads the lines from given files and tries to construct a merkle tree and find the Merkle Root node of it.
 */
public class MerkleTree {

    /**
     * leafs - initially all the plain text from a file is read and stored in a node as part of SinglyLinkedList ObjectNode
     * hashes - contains the Hash value of each sentence at each node corresponding to leafs node
     * merkleRoot - is the root node of the merkle tree
     */
    SinglyLinkedList leafs;
    SinglyLinkedList hashes;
    ObjectNode merkleRoot;

    public MerkleTree(String file) throws NoSuchAlgorithmException {
        leafs = new SinglyLinkedList();
        hashes = new SinglyLinkedList();
        leafs.reset();
        hashes.reset();
        readFile(file);
        populateHashes();
        calculateRootNode();
    }

    /**
     * Reads each line from a file and populates the leafs linked list with each node containing a line from file.
     * Theta(n), linear time complexity (depending on the size of file)
     * @param file
     * Is a valid file containing at least one line of plain text.
     *
     * @precondition
     * leafs is an object of SinglyLinkedList which is also initialized appropriately
     * @postcondition
     * Leafs is populated with each node having a line from the file as its ObjectNode data.
     */
    public void readFile(String file){
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                leafs.addAtEndNode(data);
            }
            myReader.close();

            //Forcing leafs to have even number of nodes
            if(!(leafs.countNodes() % 2 == 0)){
                leafs.addAtEndNode(leafs.getLast());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Calculates hash value for each ObjectNode data i.e. each line in file + stores them in hashes linked list
     * Theta(n), linear time complexity
     * @precondition
     * leafs linked list is populated and null terminated. hashes is an object of singlyLinkedList, also initialized
     * appropriately
     * @postcondition
     * Each node of hashes linked list will contain the hash value of corresponding leafs node.
     * @throws NoSuchAlgorithmException
     */
    public void populateHashes() throws NoSuchAlgorithmException {
        String hash;
        leafs.reset();
        while (leafs.hasNext()){
            hash = h((String) leafs.next());
            hashes.addAtEndNode(hash);
        }
        hashes.reset();
    }


    /**
     * Calculates the Merkle Root node using the hashes linked list by:
     *      a) combining the adjacent hash values and then generating a single hash value.
     *      b) Check if the resulting # of nodes is odd, if so duplicate the last node to make it even.
     *      c) Do (a) & (b) until there is only 1 node which would be our Merkle Root Node
     *  Theta(n), Linear Time complexity
     * @precondition
     * The hashes linked list is populated with correct hash values + it is null terminated
     * @postcondition
     * The hashes linked list will have one node which is the Merkle Root Node for the given file
     * @throws NoSuchAlgorithmException
     */
    public void calculateRootNode() throws NoSuchAlgorithmException {
        while (true){
            hashes.reset();
            hashes = combine(hashes);

            if(hashes.countNodes() == 1)
                break;

            if(hashes.countNodes() % 2 != 0){
                hashes.addAtEndNode(hashes.getLast());
            }
        }

        merkleRoot = hashes.getHead();
    }

    /**
     * Works in conjunction with calculateRootNode. Here we reduce the nodes of the given linked list by half by combining
     * and re-hashing the hash values of adjacent nodes of linked list.
     * Theta(n), linear time complexity
     * @param list
     * Is a null terminated singlyLinkedList object. It has even number of nodes
     * @return
     * An object of SinglyLinkedList which is half the size of the list passed to it. The data of each node is combined
     * hash of 2 previously passed adjacent node hash values.
     * @throws NoSuchAlgorithmException
     */

    public SinglyLinkedList combine(SinglyLinkedList list) throws NoSuchAlgorithmException {
        list.reset();
        SinglyLinkedList temp = new SinglyLinkedList();
        String combinedHash = "";

        while (list.hasNext()){
            combinedHash = h(list.next() + (String)list.next());
            temp.addAtEndNode(combinedHash);
        }
    return temp;
    }

    /**
     * Generates Hash for the given String.
     * Theta(1), constant time complexity
     * @param text
     * Must be a valid object of type String
     * @return
     * SHA-256 Hash of the given plain text
     * @throws NoSuchAlgorithmException
     */
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

    //Main function to find the merkle root of different files with the help of instance methods
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String TargetRoot = "A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58";
        String[] files = {"CrimeLatLonXY1990_Size2.csv", "CrimeLatLonXY1990_Size3.csv", "smallFile.txt", "CrimeLatLonXY.csv"};

        System.out.println();
        System.out.println("------------------------------------- Merkle Root Node Finder ------------------------------------------");
        for(String f : files) {
            MerkleTree merkleTree = new MerkleTree(f);
            System.out.println("Calculating root for file : " + f);
            System.out.println("The merkleRootNode is : " + merkleTree.merkleRoot.getData().toString());
            if (merkleTree.merkleRoot.getData().toString().equals(TargetRoot)) {
                System.out.println();
                System.out.println("Hurray!! the file which has the merkleRoot is : " + f);
            } else {
                System.out.println("Not the RootNode we are looking for!! Moving on");
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------");
        }
    }
}
