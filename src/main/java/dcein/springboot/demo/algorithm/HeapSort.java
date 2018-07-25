package dcein.springboot.demo.algorithm;

/**
 * @Auther: DingCong
 * @Description: 堆排序
 * @@Date: Created in 11:15 2018/5/21
 */
public class HeapSort {

    /**
     * 空间复杂度：只用到了四个辅助变量，空间复杂度是O(1).
     * 时间复杂度：最好，最坏，和平均时间复杂度都是O(nlogn).
     * 稳定性：不稳定
     * 思想：本质上是一种树形选择排序思想，将原数组看成为一个完全二叉树的顺序存储结构，利用完全二叉树中双亲节点和孩子节点之间的内在关系，在当前无序区中选择关键字最大（大根堆）或者最小（小根堆）的记录移动到数组的末尾，然后对剩余的元素作同样的操作
     * 适用性：不适宜记录数较少的表，与直接选择排序算法类似
     */


    //算法分为两个主要部分，堆调整（采用筛选算法），与排序
    //建立大根堆，每次将最大的元素移动到末尾
    void heapAdjust(int data[], int start, int end) {
        int tmp = data[start];
        for (int i = 2 * start + 1; i <= end; i *= 2) {
            //这个i<end的判断很重要，若i=end，则证明当前节点start只有一个左孩子节点，就不用继续比较了
            if (i < end && data[i] < data[i + 1])
                ++i;
            if (tmp > data[i])
                break;
            data[start] = data[i];
            start = i;
        }
        data[start] = tmp;
    }

    void heapAdjust1(int data[], int low, int high) {

        int i = low, j = 2 * i + 1;
        int tmp = data[i];
        while (j <= high) {
            if (j < high && data[j] < data[j + 1])
                ++j;
            if (tmp < data[j]) {
                data[i] = data[j];
                i = j;
                j = 2 * i;
            } else
                break;
        }
        data[i] = tmp;
    }

    void heapSort(int data[], int n) {
        int i;
        int tmp;
        //建立初始堆
        for (i = n / 2; i >= 0; --i) {
            heapAdjust(data, i, n - 1);
        }
        //堆排序过程
        for ( i = n - 1; i >= 0; --i) {
            //交换堆顶和最后一个元素
            tmp = data[0];
            data[0] = data[i];
            data[i] = tmp;
            //调整堆满足大根堆的性质
            heapAdjust(data, 0, i - 1);
        }
    }
}
