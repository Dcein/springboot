package dcein.springboot.demo.algorithm;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

/**
 * @Auther: DingCong
 * @Description: 二分法插入排序
 * @@Date: Created in 10:51 2018/5/21
 */
@Slf4j
public class BinaryInsertSort {

    /**
     *时空复杂度及稳定性与插入排序是一样的
     * 思想：
     * 对于有序的序列二分查找效率比顺序查找高很多，基于此，在将无序区的第一个元素插
     * 入到有序区相应位置时，用二分查找寻找该位置而不是顺序查找，可以减少关
     * 键字比较的次数但是关键字移动的次数仍然是没有改变的，所以其实际的效果与直接插
     * 入排序相当，只需注意二分查找思想的运用。
     */
    public void biInsertSort(int data[])
    {
        log.info("排序前:"+ Arrays.toString(data));
        int i, j, low, high, mid;
        int tmp;
        for (i = 1; i < data.length; ++i)
        {
            tmp = data[i];
            low=0;
            high=i-1;
            while (low <= high)
            {
                mid = (low + high) / 2;
                log.info("mid:"+data[mid]);
                if (tmp < data[mid])
                    high = mid - 1;
                else
                    low = mid + 1;
            }
            for (j = i - 1; j >= high + 1; --j)//high+1 is mid
                data[j + 1] = data[j];
            data[high + 1] = tmp;
            log.info("排序中:"+ Arrays.toString(data));
    }
        log.info("排序后:"+ Arrays.toString(data));
    }

}
