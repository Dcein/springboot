package dcein.springboot.demo.algorithm;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

/**
 * @Auther: DingCong
 * @Description: 冒泡排序
 * @@Date: Created in 9:54 2018/5/21
 */
@Slf4j
public class BubbleSort {


    /**
     * 时间复杂度：
     *   当原始序列“正序”排列时，冒泡排序总的比较次数为n-1，移动次数为0，也就是说冒泡排序在最好情况下的时间复杂度为O(n)；
     *   当原始序列“逆序”排序时，冒泡排序总的比较次数为n(n-1)/2，移动次数为3n(n-1)/2次，所以冒泡排序在最坏情况下的时间复杂度为O(n^2)；
     * 空间复杂度：
     *   冒泡排序排序过程中需要一个临时变量进行两两交换，所需要的额外空间为1，因此空间复杂度为O(1)。
     * 稳定性：
     *   冒泡排序在排序过程中，元素两两交换时，相同元素的前后顺序并没有改变，所以冒泡排序是一种稳定排序算法。
     * 思想：
     *   将数组头部看成水面，数组尾部看成水底，最小（或最大）的元素上浮（或下沉）直到结束，采用的是比较元素大小然后交换元素值的思想，每次都选择未排序的元素中最小或最大元素送达指定的位置。
     */
    public void bubble_sort(int [] arr){
        log.info("开始进行冒泡排序...");
        log.info("排序前:"+ Arrays.toString(arr));
        int i,j,temp;
        for(i = 0;i<arr.length-1;i++){
            for (j = 0;j<arr.length-i-1;j++){
                if (arr[j]>arr[j+1]){
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    log.info("临时变量数值:"+temp);
                }
            }
        }
        log.info("排序后:"+Arrays.toString(arr));
    }

    //经典冒泡排序算法，以后以这个为准
    void bubbleSort(int data[], int n)
    {
        int i, j, tmp, flag;
        for (i = 0; i < n - 1; ++i)
        {
            flag = 0;
            for (j = 0; j < n - i - 1; ++j)
            {
                if (data[j] > data[j + 1])
                {
                    tmp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = tmp;
                    flag = 1;
                }
            }
            if (flag == 0)
                return;
        }
    }

}
