package net.newString.crux.core.sort;

/**
 * Created by Aaron on 2017/3/14.
 */
public class QuickSort {


    //选定划分元素位置的
    public static int partitionP(int[] value, int start, int end, int pivotId) {
        int temp = value[pivotId];
        value[pivotId] = value[end];
        value[end] = temp;
        return partition(value,start,end);
    }

    //利用给定值进行划分
    public static int partitionK(int[] value,int start,int end,int pivot){
        int i = start, j = start;
        while (j < end) {
            if (value[j] < pivot) {
                if (i != j) {
                    int temp = value[i];
                    value[i] = value[j];
                    value[j] = temp;
                }
                i++;
            }
            j++;
        }
        return i;
    }


    //不选定顺序的 单线扫描
    public static int partition(int[] value, int start, int end) {
        int i = start, j = start;
        int pivot = value[end];

        while (j < end) {
            if (value[j] < pivot) {
                if (i != j) {
                    int temp = value[i];
                    value[i] = value[j];
                    value[j] = temp;
                }
                i++;
            }
            j++;
        }
        //pivot 归位
        int temp = value[i];
        value[i] = pivot;
        value[end] = temp;

        return i;
    }

    //双向扫描方法
    public static int partitionD(int value[], int start, int end) {
        int left = start, right = end - 1;
        while (true) {
            while (value[left] <= value[end] && left < end) {
                left++;
            }
            while (value[right] > value[end] && right >= 1) {
                right--;
            }
            if (left >= right)
                break;
            /**
             * 交换
             * */
            int temp = value[left];
            value[left] = value[right];
            value[right] = temp;
            left++;
            right--;
        }
        int temp = value[left];
        value[left] = value[end];
        value[end] = temp;

        return left;
    }



    public static int BFPRT(int[] value,int start, int end, int K){
        if(end-start<=4){ //小于5个数字直接插入排序获取中间位置
            InsertionSort.insertionSort(value,start,end);
            return value[start+K];
        }
        int t = start - 1; //当前替换到前面的中位数的下标
        for (int st = start, ed; (ed = st + 4) <= end; st += 5) //每5个进行处理
        {
            InsertionSort.insertionSort(value, st, ed); //5个数的排序
            t++;
            int temp = value[t];
            value[t] = value[st+2];
            value[st+2] = temp;
        }
        //从start 到 start + t 就是所有算出来的中位数区间，右移一位直接获取对应中位数的中位数的偏移量
        int pivot = start +t >> 1;

        BFPRT(value, start, t, pivot-start+1);//不关心中位数的值，保证中位数在正确的位置

        int m = partitionP(value, start, end, pivot),
                cur = m - start + 1;

        if (K == cur)
            return value[m];                   //刚好是第id个数
        else if(K < cur) return BFPRT(value, start, m-1, K);//第id个数在左边
        else return BFPRT(value, m+1, end, K-cur);         //第id个数在右边

    }


    public static void QuickSort(int[] value, int start, int end) {

        if (start < end) {
            int pivot = partitionD(value, start, end);
            QuickSort(value, start, pivot - 1);
            QuickSort(value, pivot + 1, end);
        }
    }


    public static void main(String[] args) {
//        QuickSort(ConstantValues.random1, 0, ConstantValues.random1.length - 1);
//        for (int i : ConstantValues.random1) {
//            System.out.print(i + ",");
//        }

        int mid= BFPRT(ConstantValues.random1,0,ConstantValues.random1.length-1,48);
        System.out.println("MID value"+mid);
        partitionK(ConstantValues.random1,0,ConstantValues.random1.length-1,mid);

        for(int i : ConstantValues.random1){
            System.out.print(i + ",");
        }
    }
}
