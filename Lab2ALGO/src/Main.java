import sun.security.util.Length;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {
    public static double loadFactor = 1;
    public static int[] list =  new int[10];
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
            hashTableMod.add(new Triplet<>(0, 0, null));
        }

        //creates a empty hashtable.
        for (int i = 0; i < m; i++) {
            hashTableLin.add(null);
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(10);
        }


        //
        long startMod = System.nanoTime();
        modifiedProbing(list);
        long endMod = System.nanoTime();
        long startLin = System.nanoTime();
        linearProbing(list);
        long endLin = System.nanoTime();

        runningTimeMod = endMod - startMod;
        runningTimeLin = endLin - startLin;


        System.out.println("Modified start!");
        System.out.println("Secs: " + runningTimeMod*0.000000001);
        System.out.println("amount of hashes: " + hashingUsedMod);
        System.out.println("jumps:" + probesUsedMod);
        System.out.println("longest jump:" + longestProbChainMod);
        System.out.println("number of collision:" + nrOfCollMod);
        System.out.println("-------- modified end --------");

        System.out.println("Secs: " + runningTimeLin*0.000000001);
        System.out.println("amount of hashes: " + hashingUsedLin);
        System.out.println("jumps:" + probesUsedLin);
        System.out.println("longest jump:" + longestProbChainLin);
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
        int newIndex;
        int probeChain = 0;

        //loops through the lists elements
        for(int x : test) {
            index = hL(x,m);
            //if the spot is taken, start linear probing
            if (hashTableLin.get(index) != null) {
                probeChain++;
                nrOfCollLin++;
                int i = 1;
                //Linear probing
                while(true){
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
            newIndex = hM(x+i, m);
            probesUsedMod++;
            probeChain++;
            //if we find an empty spot, add element and count chain.
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
            newIndex = hM(x, m) - i;
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
        return x % m;
    }

    public static int hL(int x, int m) {
        hashingUsedLin++;
        return x % m;
    }

}