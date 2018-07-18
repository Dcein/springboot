package dcein.springboot.demo.algorithm;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

/**
 * @Auther: DingCong
 * @Description: 希尔排序
 * @@Date: Created in 11:03 2018/5/21
 */
@Slf4j
public class ShellSort {

    /**
     *空间复杂度：
     *   只用到了i,j,gap,tmp4个辅助变量，与问题规模无关，空间复杂度为O(1).
     * 时间复杂度：
     *   分析较复杂，一般认为平均时间复杂度为O(n^1.3).
     * 稳定性：
     *   不稳定
     * 思想：
     *   本质上还是属于插入排序，只不过是先对序列分组，然后组内直接插入，同时，分组数
     *   由多到少，组内元素由少到多，顺序性由差到好，直到最后一步组间距为1时，
     *   直接插入排序的数组已经基本有序了
     */
    void shellSort(int data[])
    {
        log.info("排序前:"+ Arrays.toString(data));
        int i, j, gap;
        int tmp;
        gap = data.length / 2;
        while (gap > 0)
        {
            //这样记忆，整个for循环其实就是直接插入排序的过程，只不过将直接插入排序
            //的1->gap罢了，最后当gap=1的时候就是直接插入排序了。
            for (i = gap; i < data.length; ++i)
            {
                tmp = data[i];
                j = i - gap;
                while (j >= 0 && tmp < data[j])
                {
                    data[j + gap] = data[j];
                    j = j - gap;
                }
                data[j + gap] = tmp;
            }
            gap = gap / 2;
        }
        log.info("排序后:"+ Arrays.toString(data));
    }

}
