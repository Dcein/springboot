/*
package web.example.demo.algorithm;

*/
/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 11:21 2018/5/21
 *//*

public class MergeSort {


    //一趟归并过程，将两个有序的子表合成一个新的有序表
    void merge(int data[], int low, int mid, int high)
    {
        int i = low, j = mid + 1, k = 0;
        //临时存储排好序的数组
        int[] tmp = new int[high - low + 1];
        while (i <= mid && j <= high)
        {
            if (data[i] < data[j])
                tmp[k++] = data[i++];
            else
                tmp[k++] = data[j++];
        }
        while (i <= mid)
            tmp[k++] = data[i++];
        while (j <= high)
            tmp[k++] = data[j++];

        for (int i = low, k = 0; i <= high; i++, k++)
            data[i] = tmp[k];
        //delete tmp;
    }

    //递归形式分别对数组的左右两个子数组归并排序，然后merge成一个新的有序数组
    void mergeSortR(int data[], int low, int high)
    {
        int mid;
        if (low < high)
        {
            mid = (low + high) / 2;
            mergeSortR(data, low, mid);
            mergeSortR(data, mid + 1, high);
            merge(data, low, mid, high);
        }
    }
    //自顶向下的二路归并排序算法
    void mergeSort(int data[], int n)
    {
        mergeSortR(data, 0, n - 1);
    }
}
*/
