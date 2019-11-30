
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //variables
        int[] ok = {35, 2, 1, 3, 75, 5, 6, 7,42,100};
        int m = ok.length;
        int index;
        ArrayList<Triplet<Integer,Integer,Integer>> hasTable = new ArrayList<>();

        for(int i = 0; i < m; i++){
            hasTable.add(new Triplet<Integer, Integer, Integer>(1,1,null));
        }

        //loops through the given list with Integers.
        for (int x: ok
             ) {
            index = h(x,m);
            if(hasTable.get(index).getValue() != null){

               // if(hasTable.get(index).getLup() < hasTable.get(index).getLdown()){
                    //Calls f1 at the index + Lups index where we haven't checked yet.
                    hasTable = f1(x,hasTable,index);

               // }
            }else{
                //If the hashtable position is empty, add x.
                hasTable.get(index).setValue(x);
            }
        }

        for (int i = 0; i < hasTable.size(); i++){

            System.out.println("Index: " + i + " Lup:" + hasTable.get(i).getLup() + " och value:" + hasTable.get(i).getValue());
        }

    }

    public static ArrayList<Triplet<Integer,Integer,Integer>> f1(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr, int index){
        //Variables
        int m = arr.size();
        int j = 1;
        int tempIndex = index + arr.get(index).getLup();
        int add;

        while(true){
            //in case tempIndex is out of range.
            if(tempIndex >= m){
                tempIndex = tempIndex % m;
            }

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

    public static int h(int x, int m) {
        return x % m;
    }
}