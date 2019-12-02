import java.util.ArrayList;

/**
 * @author roblof-8, johlax-8, wesjon-5
 */
public class Main {


    public static void main(String[] args) {
        int[] ok = {0,1,1,3,4,2};
        modifiedProbing(ok);
        linearProbing(ok);
    }

    /**
     * Modified probing
     */
    public static void modifiedProbing(int test[]){
        //variables
        int m = test.length;
        int index;
        ArrayList<Triplet<Integer,Integer,Integer>> hasTable = new ArrayList<>();

        for(int i = 0; i < m; i++){
            hasTable.add(new Triplet<>(1,1,null));
        }

        //loops through the given list with Integers.
        for (int x: test
        ) {
            index = h(x,m);
            if(hasTable.get(index).getValue() != null){

                if(hasTable.get(index).getLup() <= hasTable.get(index).getLdown()){
                    //Calls f1 at the index + Lups index where we haven't checked yet.
                    hasTable = f1(x,hasTable,index);

                }else{
                    hasTable = f2(x,hasTable,index);
                }
            }else{
                //If the hashtable position is empty, add x.
                hasTable.get(index).setValue(x);
            }
        }
        //print out table values
        for (int i = 0; i < hasTable.size(); i++){

            System.out.println("Index: " + i + " Lup:" + hasTable.get(i).getLup() + " Ldown: "+ hasTable.get(i).getLdown() + " och value:" + hasTable.get(i).getValue());
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

    public static ArrayList<Triplet<Integer,Integer,Integer>> f2(int x, ArrayList<Triplet<Integer,Integer,Integer>> arr, int index){
        //Variables
        int m = arr.size();
        int j = 1;
        int tempIndex = index - arr.get(index).getLdown();
        int add;

        while(true){
            //in case tempIndex is out of range.
            if(tempIndex < 0){
                tempIndex = m + tempIndex;
            }

            //if the tempIndex is empty, add the element to the list and save the new lup value.
            if(arr.get(tempIndex).getValue() == null){
                arr.get(tempIndex).setValue(x);
                add = arr.get(index).getLdown();
                arr.get(index).setLDown(add+j);
                return arr;
            }
            j++;
            tempIndex--;
        }
    }
    /**
     *
     * @param x Integer in the list
     * @param m Length of the list
     * @return index in the hashtable list.
     */
    public static int h(int x, int m) {
        return x % m;
    }

    public static void linearProbing(int[] test){
        //Variables
        ArrayList<Integer> hashtable = new ArrayList<>();
        int index;
        int m = test.length;

        //instances the list with null objects
        for (int i = 0; i < m; i++){
            hashtable.add(null);
        }


        for (int x:test
             ) {
            //calls h(x) for index value
            index = h(x,m);
            //if the spot is taken run linearf1()
            if(hashtable.get(index) != null){
                hashtable = linearF1(x,hashtable,index);
            }else{
                hashtable.set(index,x);
            }
        }
        for(int i = 0; i < hashtable.size(); i++){
            System.out.println("Index:" + i + " Value: " + hashtable.get(i));
        }


    }

    public static ArrayList<Integer> linearF1(int x, ArrayList<Integer> arr, int index){
        int tempIndex = index + 1;
        int m = arr.size();

        while(true){

            //range out of bound.
            if(tempIndex == m){
                tempIndex = 0;
            }
            if(arr.get(tempIndex) == null){
                arr.set(tempIndex,x);
                return arr;
            }
            tempIndex++;
        }

    }
}