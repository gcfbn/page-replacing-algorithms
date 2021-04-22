public class ArrayHelper {

    /**
     * Checks if array contains requested value and returns it's index
     * @return if array contains empty frames, method returns it's index
     * in other case returns -1
     */
    public static int indexOf(int value, int[] array){
        for (int i=0; i<array.length; i++){
            if (array[i] == value)
                return i;
        }
        return -1;
    }
}
