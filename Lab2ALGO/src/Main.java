import sun.security.util.Length;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {
    public static double loadFactor = 1;
    public static int[] list =  new int[100000];
    public static int m = (int) (list.length/loadFactor);
    public static int hashingUsed = 0;
    public static int probesUsed = 0;
    public static long runningTime = 0;
    public static int nrOfColl = 0;
    public static ArrayList<Triplet<Integer,Integer,Integer>> hashTable = new ArrayList<>();
    public static int longestProbChain = 0;


    public static void main(String[] args) {
        /*
        String path = "C:/Users/Robin/OneDrive/SkrivBord/LTU Kurser/Algoritmer och datastrukturer/Lab2/Values.txt";
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();

        }catch (FileNotFoundException e){
            System.out.println("file not found");
        }catch (UnsupportedEncodingException e){
            System.out.println("shit");
        }
        */
        Random rand = new Random();


        //creates a empty hashtable.
        for (int i = 0; i < m; i++) {
            hashTable.add(new Triplet<>(0, 0, null));
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(1000);
        }

        //Test list
        //int[] list = {1,1,2,3,2,5,6,7,2,1,2,2};

        //
        long start = System.nanoTime();
        modifiedProbing(list);
        long end = System.nanoTime();
        runningTime = end - start;

        System.out.println("Secs: " + runningTime*0.000000001);
        System.out.println("amount of hashes: " + hashingUsed);
        System.out.println("jumps:" + probesUsed);
        System.out.println("longest jump:" + longestProbChain);
        System.out.println("number of collision:" + nrOfColl);
    }

    public static void modifiedProbing(int test[]){
        //variables
        int index;


        //loops through the given list with Integers.
        for (int x : test
        ) {
            //checks if the first index is occupied
            index = h(x, m);
            if (hashTable.get(index).getValue() != null) {
                nrOfColl++;
                if (hashTable.get(index).getLup() <= hashTable.get(index).getLdown()) {
                    //Calls f1 at the index + Lups index where we haven't checked yet.
                    hashTable = f1(x, hashTable,index);
                } else {
                      hashTable = f2(x,hashTable,index);
                }
            } else {
                hashTable.get(index).setValue(x);
            }
        }
        for(int i = 0; i < m; i++){
            System.out.println("Index:" + i + " Lup:" + hashTable.get(i).getLup() + " Ldown:" + hashTable.get(i).getLdown() + " value:" + hashTable.get(i).getValue());
        }
    }


    /**
     *
     * @param x Integer to insert into hashtable.
     * @param arr hashtable array.
     * @return hashtable array.
     *
     * f1() searches for the position to put x in the hashtable, by moving upwards in the hashtable.
     */
    public static ArrayList<Triplet<Integer,Integer,Integer>> f1(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr, int index) {
        //Variables
        int probeChain = 0;
        int i = arr.get(index).getLup();
        int newIndex;

        //Starts probing
        while (true) {
            ++i;
            newIndex = h(x+i, m);
            probesUsed++;
            probeChain++;
            //if we find an empty spot, add element and count chain.
            if(arr.get(newIndex).getValue() == null){
                arr.get(newIndex).setValue(x);
                arr.get(index).setLup(i);
                //Longest probe chain
                if(longestProbChain < probeChain){
                    longestProbChain = probeChain;
                }
                return arr;
            }
        }
    }

    public static ArrayList<Triplet<Integer,Integer,Integer>> f2(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr, int index) {
        //Variables
        int probeChain = 0;
        int i = arr.get(index).getLdown();
        int newIndex;

        //Starts probing
        while (true) {
            i++;
            newIndex = h(x, m) - i;
            if(newIndex < 0 ){
                newIndex = (m) - i;
            }
            probesUsed++;
            probeChain++;
            //if we find an empty spot, add element and count chain.
            if(arr.get(newIndex).getValue() == null){
                arr.get(newIndex).setValue(x);
                arr.get(index).setLDown(i);
                //Longest probe chain
                if(longestProbChain < probeChain){
                    longestProbChain = probeChain;
                }
                return arr;
            }
        }
    }


    /**
     *
     * @param x Integer in the list
     * @param m Length of the list
     * @return index in the hashtable list.
     */
    public static int h(int x, int m) {
        hashingUsed++;
        return x % m;
    }

}