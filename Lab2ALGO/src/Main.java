import sun.security.util.Length;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {
    public static double loadFactor = 1;
    public static int[] list =  new int[10000];
    public static int m = (int) (list.length/loadFactor);
    //Modified variables
    public static long runningTimeMod = 0;
    public static int hashingUsedMod = 0;
    public static int nrOfCollMod = 0;
    public static int probesUsedMod = 0;
    public static int longestProbChainMod = 0;
    //Linear variables
    public static long runningTimeLin = 0;
    public static int hashingUsedLin = 0;
    public static int nrOfCollLin = 0;
    public static int probesUsedLin = 0;
    public static int longestProbChainLin = 0;
    public static ArrayList<Triplet<Integer,Integer,Integer>> hashTableMod = new ArrayList<>();
    public static ArrayList<Integer> hashTableLin = new ArrayList<>();


    public static void main(String[] args) {

        String path = "C:/Users/Robin/OneDrive/SkrivBord/LTU Kurser/Algoritmer och datastrukturer/Lab2/Values.txt";

        Random rand = new Random();


        //creates a empty hashtable.
        for (int i = 0; i < m; i++) {
            hashTableMod.add(new Triplet<>(0, 0, null));
        }

        //creates a empty hashtable.
        for (int i = 0; i < m; i++) {
            hashTableLin.add(null);
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(100);
        }




        long start = System.nanoTime();
        modifiedProbing(list);
        long end = System.nanoTime();
        runningTimeMod = end - start;

        System.out.println("Secs: " + runningTimeMod*0.000000001);
        System.out.println("amount of hashes: " + hashingUsedMod);
        System.out.println("jumps:" + probesUsedMod);
        System.out.println("longest jump:" + longestProbChainMod);
        System.out.println("number of collision:" + nrOfCollMod);

        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.println(m);
            writer.println(runningTimeMod);
            writer.println(hashingUsedMod);
            writer.println(nrOfCollMod);
            writer.println(probesUsedMod);
            writer.println(longestProbChainMod);
            writer.close();
            writer.println(m);
            writer.println(runningTimeLin);
            writer.println(hashingUsedLin);
            writer.println(nrOfCollLin);
            writer.println(probesUsedLin);
            writer.println(longestProbChainLin);
            writer.close();

        }catch (FileNotFoundException e){
            System.out.println("file not found");
        }catch (UnsupportedEncodingException e){
            System.out.println("shit");
        }
    }

    public static void modifiedProbing(int[] test){
        //variables
        int index;

        //loops through the given list with Integers.
        for (int x : test
        ) {
            //checks if the first index is occupied
            index = h(x, m);
            if (hashTableMod.get(index).getValue() != null) {
                nrOfCollMod++;
                if (hashTableMod.get(index).getLup() <= hashTableMod.get(index).getLdown()) {
                    //Calls f1 at the index + Lups index where we haven't checked yet.
                    hashTableMod = f1(x, hashTableMod,index);
                } else {
                      hashTableMod = f2(x,hashTableMod,index);
                }
            } else {
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
        int i = 1;
        int newIndex;
        int probeChain;
        for(int x : test) {
            index = h(x,m);
            if (hashTableLin.get(index) != null) {
                probeChain++;
                nrOfCollLin++;
                newIndex = h(x+i,m);
                if(hashTableLin.get(newIndex) == null){
                    hashTableLin.add(x);
                    if(longestProbChainLin < probeChain){
                        longestProbChainLin = probeChain;
                   }
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
            newIndex = h(x+i, m);
            probesUsed++;
            probeChain++;
            //if we find an empty spot, add element and count chain.
            if(arr.get(newIndex).getValue() == null){
                arr.get(newIndex).setValue(x);
                add = arr.get(index).getLup();
                arr.get(index).setLup(add+1);
                //Longest probe chain
                if(longestProbChain < probeChain){
                    longestProbChain = probeChain;
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
            newIndex = h(x, m) - i;
            if(newIndex < 0 ){
                newIndex = m + newIndex;
            }
            probesUsed++;
            probeChain++;
            //if we find an empty spot, add element and count chain.
            if(arr.get(newIndex).getValue() == null){
                arr.get(newIndex).setValue(x);
                add = arr.get(index).getLdown();
                arr.get(index).setLDown(add+1);
                //Longest probe chain
                if(longestProbChain < probeChain){
                    longestProbChain = probeChain;
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
    public static int h(int x, int m) {
        hashingUsed++;
        return x % m;
    }

}