package dcein.springboot.demo.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer.Vanilla.std;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

/**
 * @Auther: DingCong
 * @Description: 快速排序
 * @@Date: Created in 11:08 2018/5/21
 */
@Slf4j
public class QuickSort {

    /**
     * 空间复杂度：主要是递归时所需的栈空间，平均空间复杂度为O(nlongn)。
     * 时间复杂度：主要的时间都花费在划分上面，最好情况，每次划分的基准都是无序区的‘中值’记录，O(nlogn);最坏情况，原数组本身是有序的，此时O(n^2)。平均时间复杂度为O(nlogn)。
     * 稳定性： 不稳定
     * 思想：分治的思想，将大问题转化为小问题，递归的思想，最重要的过程就是划分，划分结束了，数组也就排好序了，快速排序是排序算法中非常重要的一种
     */
    //快排，数据结构书上的方法,递归，以后以这个为准
    void quickSort(int data[], int start, int end) {
        log.info("排序前:"+ Arrays.toString(data));
        int i = start, j = end;
        int pivot;
        if (start < end) {
            //每次递归都取无序区的第一个元素作为中心基准，这个地方可以改进为随机的方法
            pivot = data[start];
            while (i != j) {
                while (j > i && data[j] > pivot)
                    --j;
                data[i] = data[j];
                while (i < j && data[i] < pivot)
                    ++i;
                data[j] = data[i];
            }
            data[i] = pivot;
            quickSort(data, start, i - 1);
            quickSort(data, i + 1, end);
        }
        log.info("排序后:"+Arrays.toString(data));
    }

}
