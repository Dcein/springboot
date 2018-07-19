package dcein.springboot.demo.algorithm;

/**
 * @Auther: DingCong
 * @Description: 直接选择排序
 * @@Date: Created in 11:14 2018/5/21
 */
public class SelectSort {


    /**
     *空间复杂度：只用到了i,j,k,tmp四个辅助变量，故空间复杂度为O(1).
     * 时间复杂度：无论表的初始状态如何，比较次数都达到O(n^2),故直接选择排序的最好和最坏
     * 时间复杂度都是O(n^2).
     * 稳定性：不稳定，如将{5，3，2，5，4，1}排序，第一趟就改变了两个5的相对位置。可以
     * 看成是交换排序和直接插入排序的综合，但是直接插入和冒泡排序都是稳定的，而该
     * 算法是不稳定的
     * 思想：每一趟从待排序的记录中选择关键字最小的记录，顺序放在已排好序子表的最后，知道
     * 全部记录排序完毕
     * 适用性：适合从大量记录中选择一部分排序记录，如从10000个记录中选择关键字大小为前10的记录
     */
    void selectSort(int data[], int n)
    {
        int tmp, k;
        for (int i = 0; i < n - 1; ++i)
        {
            k = i;
            for (int j = i + 1; j < n; ++j)
            {
                if (data[j] < data[k])
                    k = j;
            }
            if (k != i)//若k=i则证明已经是有序的了
            {
                tmp = data[i];
                data[i] = data[k];
                data[k] = tmp;
            }
        }
    }
}
