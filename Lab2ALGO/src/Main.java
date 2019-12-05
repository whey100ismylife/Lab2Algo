import sun.security.util.Length;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {
    public static double maxLOAD = 0.75;
    public static double currentLoadFactor = 0.0;
    public static int m = 10;
    public static int[] list =  new int[10];
    public static int hashingUsed = 0;
    public static int probesUsed = 0;
    public static long runningTime = 0;
    public static int nrOfColl = 0;
    public static int longestCollChain = 0;


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

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(1000);
        }

        //
        long start = System.nanoTime();
        while (true) {
            boolean tests = modifiedProbing(list, m);
            if (tests) {
                break;
            }
        }
        long end = System.nanoTime();
        runningTime = end - start;
    }

    public static boolean modifiedProbing(int test[], int m) {
        //variables
        int index;

        //Hashtable
        ArrayList<Triplet<Integer, Integer, Integer>> hasTable = new ArrayList<>();

        //creates a empty hastable.
        for (int i = 0; i < m; i++) {
            hasTable.add(new Triplet<>(1, 1, null));
        }

        //loops through the given list with Integers.
        for (int x : test
        ) {
            //checks load factor
            if (currentLoadFactor < maxLOAD) {
                //checks if the first index is occupied
                index = h(x, m);
                if (hasTable.get(index).getValue() != null) {
                    nrOfColl++;
                    if (hasTable.get(index).getLup() <= hasTable.get(index).getLdown()) {
                        //Calls f1 at the index + Lups index where we haven't checked yet.
                        //hasTable = f1(x, hasTable, index);
                    } else {
                        //  hasTable = f2(x,hasTable,index);
                    }
                } else {
                    hasTable.get(index).setValue(x);
                }
            }
        }
        return true;
    }


    /**
     *
     * @param x Integer to insert into hashtable.
     * @param arr hashtable array.
     * @param index position to start in the hashtable.
     * @return hashtable array.
     *
     * f1() searches for the position to put x in the hashtable, by moving upwards in the hastable.
     */
   /* public static ArrayList<Triplet<Integer,Integer,Integer>> f1(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr, int index){
        //Variables
        int j = 1;
        int tempIndex = index + arr.get(index).getLup();
        int add;

            //if the tempIndex is empty, add the element to the list and save the new lup value.
            if(arr.get(tempIndex).getValue() == null){
                arr.get(tempIndex).setValue(x);
                add = arr.get(index).getLup();
                arr.get(index).setLup(add+j);
                return arr;
            }
            j++;
            tempIndex++;
        }
    }

*/
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