package dcein.springboot.demo.algorithm;

import org.junit.jupiter.api.Test;

/**
 * @Auther: DingCong
 * @Description: 问题解决有好多相同的方法
 * @@Date: Created in 16:37 2018/7/25
 */
public class Recursion {

    public int beginRecursion(int num) {

        //递归终止条件(必须条件,防止无限循环)
        if (num == 1 || num == 2) {
            return 1;
        }

        //递归进行
        int res = beginRecursion(num - 1) + beginRecursion(num - 2);

        return res;
    }

    @Test
    public void test() {
        int i = beginRecursion(5);
        System.out.println(i);
    }


}
