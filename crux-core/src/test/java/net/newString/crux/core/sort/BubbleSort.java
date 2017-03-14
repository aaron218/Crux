package net.newString.crux.core.sort;

/**
 * Created by Aaron on 2017/3/14.
 */
public class BubbleSort {


    public static void simpleSort(int[] value, int start, int end) {
        if (start >= end) {
            return;
        }
        for (int i = 0; i <= end; i++) {
            for (int j = 0, temp; j <= end - i - 1; j++) {
                if (value[j] > value[j + 1]) {
                    temp = value[j + 1];
                    value[j + 1] = value[j];
                    value[j] = temp;
                }
            }
        }
    }


    public static void main(String[] args) {
        simpleSort(ConstantValues.random1, 0, ConstantValues.random1.length - 1);
        for (int i : ConstantValues.random1) {
            System.out.print(i + ",");
        }
    }
}
