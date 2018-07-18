package dcein.springboot.demo.algorithm;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

/**
 * @Auther: DingCong
 * @Description: 直接插入排序
 * @@Date: Created in 10:31 2018/5/21
 */
@Slf4j
public class InsertSort {


    /**
     * 空间复杂度：
     *    只有辅助变量， 没有与问题规模相关的辅存消耗，O(1)
     * 时间复杂度：
     *    最好情况，初始数组为正序（此处为递增），O(n)；最坏情况，初始数组为反序，O(n2)；平均时间复杂度为O(n2).
     * 稳定性：
     *    当arr[i]=arr[i-1]时，相对位置不变，所以是稳定的排序
     * 思想：
     *    将原序列分为有序区和无序区，每次外部循环将无序区的第一个元素插入到有序区的适当位置，同时有序区元素加1，无序区元素减1，这样直到无序区的元素为0
     */
    public void insert_sort(int[] arr){
        log.info("直接插入排序...");
        log.info("排序前:"+ Arrays.toString(arr));
        int i,j,temp;
        for (i=1; i< arr.length;++i){
            temp = arr[i];
            j = i -1;
            int time = 0;
            while (j>=0 && temp<arr[j]){
                time ++;
                arr[j+1] = arr[j];
                --j;
                log.info("排序次数"+time);
            }
            //若j<0则temp是有序区的最小元素，若temp>=arr[j]则将temp放在arr[j]的后面arr[j+1]处
            arr[j+1] = temp;
            log.info("排序中:"+Arrays.toString(arr));
        }
        log.info("排序后:"+Arrays.toString(arr));
    }

}
