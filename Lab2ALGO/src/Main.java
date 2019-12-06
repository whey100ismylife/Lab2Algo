import sun.security.util.Length;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {
    public static double loadFactor = 0.75;
    public static int[] list =  new int[10];
    public static int m = (int) (list.length/loadFactor);
    public static int hashingUsed = 0;
    public static int probesUsed = 0;
    public static long runningTime = 0;
    public static int nrOfColl = 0;
    public static ArrayList<Triplet<Integer,Integer,Integer>> hasTable = new ArrayList<>();
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
            hasTable.add(new Triplet<>(0, 0, null));
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(1000);
        }

        //
        long start = System.nanoTime();
        while (true) {
            boolean tests = modifiedProbing(list);
            if (tests) {
                break;
            }
        }
        long end = System.nanoTime();
        runningTime = end - start;
    }

    public static boolean modifiedProbing(int test[]) {
        //variables
        int index;

        //Hashtable
        ArrayList<Triplet<Integer, Integer, Integer>> hashTable = new ArrayList<>();


        //loops through the given list with Integers.
        for (int x : test
        ) {
            //checks if the first index is occupied
            index = h(x, m);
            if (hashTable.get(index).getValue() != null) {
                nrOfColl++;
                if (hashTable.get(index).getLup() <= hashTable.get(index).getLdown()) {
                    //Calls f1 at the index + Lups index where we haven't checked yet.
                    hashTable = f1(x, hashTable);
                } else {
                    //  hasTable = f2(x,hasTable,index);
                }
            } else {
                hashTable.get(index).setValue(x);
            }
        }
        return true;
    }


    /**
     *
     * @param x Integer to insert into hashtable.
     * @param arr hashtable array.
     * @return hashtable array.
     *
     * f1() searches for the position to put x in the hashtable, by moving upwards in the hashtable.
     */
    public static ArrayList<Triplet<Integer,Integer,Integer>> f1(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr) {
        //Variables
        int add;
        int probeChain = 0;
        int i = 1;
        int index;


        while (true) {
            index = h(x, m) + i;
            if(arr.get(index).getValue() != null){
                probesUsed++;
                probeChain++;
                i++;
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