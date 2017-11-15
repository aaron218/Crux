package net.newstring.crux.core.sort;

/**
 */
public class InsertionSort {


    public static void insertionSort(int[] value, int start, int end) {
        int j;
        for (int i = start; i <= end; i++) {
            int temp = value[i];
            for (j = i; j > 0; j--) {
                if (value[j - 1] > temp) {
                    value[j] = value[j - 1];
                } else {
                    break;
                }
            }
            value[j] = temp;
        }
    }

    public static void main(String[] args) {
        insertionSort(ConstantValues.random1, 0, ConstantValues.random1.length - 1);
        for (int i : ConstantValues.random1) {
            System.out.print(i + ",");
        }
    }

}
