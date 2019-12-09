import sun.security.util.Length;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {
    public static double loadFactor = 1;
    public static int[] list =  new int[800000];
    public static int m = (int) (list.length/loadFactor);
    //Modified variables
    public static long runningTimeMod = 0;
    public static long hashingUsedMod = 0;
    public static long nrOfCollMod = 0;
    public static long probesUsedMod = 0;
    public static long longestProbChainMod = 0;
    //Linear variables
    public static long runningTimeLin = 0;
    public static long hashingUsedLin = 0;
    public static long nrOfCollLin = 0;
    public static long probesUsedLin = 0;
    public static long longestProbChainLin = 0;
    public static ArrayList<Triplet<Integer,Integer,Integer>> hashTableMod = new ArrayList<>();
    public static ArrayList<Integer> hashTableLin = new ArrayList<>();


    public static void main(String[] args) {
        Random rand = new Random();
       // System.out.println(m);


        //creates a empty hashtable.
        for (int i = 0; i < m; i++) {
            hashTableMod.add(new Triplet<>(0, 0, null));
        }

        //creates a empty hashtable.
        for (int i = 0; i < m; i++) {
            hashTableLin.add(null);
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt();
        }

            /*
            long startMod = System.nanoTime();
            modifiedProbing(list);
            long endMod = System.nanoTime();
            runningTimeMod = endMod - startMod;

            */
            long startLin = System.nanoTime();
            linearProbing(list);
            long endLin = System.nanoTime();

            runningTimeLin = endLin - startLin;

        /*
        System.out.println("Modified start!");
        System.out.println("Secs: " + runningTimeMod*0.000000001);
        System.out.println("amount of hashes: " + hashingUsedMod);
        System.out.println("probes:" + probesUsedMod);
        System.out.println("longest probe:" + longestProbChainMod);
        System.out.println("number of collision:" + nrOfCollMod);
        System.out.println("-------- modified end --------");
*/
        System.out.println("Secs: " + runningTimeLin*0.000000001);
        System.out.println("amount of hashes: " + hashingUsedLin);
        System.out.println("probes:" + probesUsedLin);
        System.out.println("longest probes:" + longestProbChainLin);
        System.out.println("number of collision:" + nrOfCollLin);

    }




    public static void modifiedProbing(int[] test){
        //variables
        int index;

        //loops through the given list with Integers.
        for (int x : test
        ) {
            //checks if the first index is occupied
            index = hM(x, m);
            //if not start probing for an empty slot
            if (hashTableMod.get(index).getValue() != null) {
                nrOfCollMod++;
                //checks which of f1 or f2 is going to be used for probing
                if (hashTableMod.get(index).getLup() <= hashTableMod.get(index).getLdown()) {
                    //Calls f1
                    hashTableMod = f1(x, hashTableMod,index);
                } else {
                    //calls f2
                      hashTableMod = f2(x,hashTableMod,index);
                }
            } else {
                //if the index in the hashtable is empty add the element.
                hashTableMod.get(index).setValue(x);
            }
        }
        /*for(int i = 0; i < m; i++){
            System.out.println("Index:" + i + " Lup:" + hashTableMod.get(i).getLup() + " Ldown:" + hashTableMod.get(i).getLdown() + " value:" + hashTableMod.get(i).getValue());
        }*/
    }

    public static void linearProbing(int test[]) {
        //variables
        int index;
        int newIndex;

        //loops through the lists elements
        for(int x : test) {
            int probeChain = 0;
            index = hL(x,m);
            //if the spot is taken, start linear probing, new collision
            if (hashTableLin.get(index) != null) {
                nrOfCollLin++;
                int i = 1;
                //Linear probing
                while(true){
                    probeChain++;
                    probesUsedLin++;
                    newIndex = hL(x+i,m);
                    if(hashTableLin.get(newIndex) == null){
                        hashTableLin.set(newIndex,x);
                        if(longestProbChainLin < probeChain){
                            longestProbChainLin = probeChain;
                       }
                        break;
                   }
                    i++;
                }

            } else {
                hashTableLin.set(index, x);
            }
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
        int i = 1;
        int add;
        int newIndex;

        //Starts probing
        while (true) {
            //hashes the next index.
            newIndex = hM(x+i, m);
            //increments probes used by 1.
            probesUsedMod++;
            probeChain++;
            //if we find an empty spot, add element and count chain and add + 1
            if(arr.get(newIndex).getValue() == null){
                arr.get(newIndex).setValue(x);
                add = arr.get(index).getLup();
                arr.get(index).setLup(add+1);
                //Longest probe chain
                if(longestProbChainMod < probeChain){
                    longestProbChainMod = probeChain;
                }
                return arr;
            }
            i++;
        }
    }

    public static ArrayList<Triplet<Integer,Integer,Integer>> f2(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr, int index) {
        //Variables
        int probeChain = 0;
        int i = 1;
        int newIndex;
        int add;

        //Starts probing
        while (true) {
            newIndex = (hM(x, m)) - i;
            if(newIndex < 0 ){
                newIndex = m + newIndex;
            }
            probesUsedMod++;
            probeChain++;
            //if we find an empty spot, add element and count chain.
            if(arr.get(newIndex).getValue() == null){
                arr.get(newIndex).setValue(x);
                add = arr.get(index).getLdown();
                arr.get(index).setLDown(add+1);
                //Longest probe chain
                if(longestProbChainMod < probeChain){
                    longestProbChainMod = probeChain;
                }
                return arr;
            }
            i++;
        }
    }


    /**
     *
     * @param x Integer in the list
     * @param m Length of the list
     * @return index in the hashtable list.
     */
    public static int hM(int x, int m) {
        hashingUsedMod++;
        return  Math.floorMod(x, m);
    }

    public static int hL(int x, int m) {
        hashingUsedLin++;
        return  Math.floorMod(x,m);
    }

}