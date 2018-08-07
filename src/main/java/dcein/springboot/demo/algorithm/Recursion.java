package dcein.springboot.demo.algorithm;

import org.junit.jupiter.api.Test;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 16:37 2018/7/25
 */
public class Recursion {

    /**
     * 斐波那契数列 形如：1 1 2 3 5 8 ...
     * 核心思想：第一个数和第二个数都为1,以后后边的数字为前面两个数字之和,一直在重复两个数字之和
     *
     * @param num
     * @param flag
     * @return
     */
    public int beginRecursion(int num, String flag) {
        System.out.println("打印:[本次数字:" + num + ",标志函数:" + flag + "]");

        //递归终止条件(必须条件,防止无限循环)
        if (num == 1 || num == 2) {
            System.out.println("打印:[本次数字:" + num + ",标志函数:endHoop]");
            return 1;
        }

        //递归进行
        int res = beginRecursion(num - 1, "left") + beginRecursion(num - 2, "right");

        return res;
    }


    /**
     * 阶乘
     * @return
     */
    public long factorial(int n) {
        System.out.println("[本次n:" + n + "]");

        //结束循环条件
        if (n == 1) {
            return 1;
        }

        //递归进行
        long res = factorial(n - 1) * n;
        System.out.println("[本次阶乘结果:" + res + "]");

        return res;

    }


    /**
     * 幂函数
     * @param x
     * @param y
     * @return
     */
    public long power(int x, int y) {
        System.out.println("[底数:" + x + ",指数:" + y + "]");
        long res = x;

        while (y != 1){
            y--;
            res = res *x;
        }

        System.out.println(res);
        return res;
    }

    @Test
    public void test() {
        power(2,50);
    }


}
