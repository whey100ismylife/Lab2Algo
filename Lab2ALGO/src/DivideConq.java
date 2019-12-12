/**
 * @author roblof-8, johlax-8, wesjon-5
 */

public class DivideConq {


    public static void main(String[] args) {

        int[] testArray = {5,21,34,15,10,30,20,100,55,43,75,43,22,26,39,29};
        int[] smallest = divideAndConq(testArray);

        System.out.println("The 4 smallest are :");
        for (int i:smallest
        ) {
            System.out.println(" " + i);
        }
        System.out.println("and the 4th smallest:" + smallest[3]);
    }


    /**
     *
     * @param arr list to divide
     * @return the 4th smallest array
     */
    public static int[] divideAndConq(int[] arr){


        //Base case (with an array of size 4)
        if(arr.length == 4){
            //Insertion sort the array.
            for (int i = 0; i < arr.length; i++){
                int current = arr[i];
                int position = i;
                while (position > 0 && arr[position-1] > current){
                    arr[position] = arr[position-1];
                    position--;
                }
                arr[position] = current;
            }
            //Returns the sorted array of size 4
            return arr;
        }

        //if not base case, divide and conquer! until base case!
        int mid =  arr.length/2;
        int[] left = new int[mid];
        int[] right = new int[mid];

        //puts the elements to the left side
        for(int i  = 0; i < mid; i++){
            left[i] = arr[i];
        }
        //puts the elements to right
        int x =0;
        for(int i = mid; i <arr.length; i++){
            right[x] = arr[i];
            x++;
        }

        int[] leftBack = divideAndConq(left);
        int[] rightBack = divideAndConq(right);


        int[] smallestOfBoth = new int[4];
        int l = 0;
        int r = 0;

        //Compares the two arrays the get the smallest 4 elements.
        for(int i = 0; i < 4; i++){
            if(leftBack[l] <= rightBack[r]){
                smallestOfBoth[i] = leftBack[l];
                l++;
            }else{
                smallestOfBoth[i] = rightBack[r];
                r++;
            }
        }

        //Returns the smallest 4.
        return smallestOfBoth;
    }
}
