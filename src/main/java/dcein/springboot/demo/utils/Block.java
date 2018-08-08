package dcein.springboot.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: DingCong
 * @Description: 模拟单片机数字钟，纪念白林锋
 * @@Date: Created in 14:28 2018/8/7
 */
public class Block {

    public static String[][] num0 = {{"*","*","*","*","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*","*","*","*","*"}};
    public static String[][] num1 = {{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "}};
    public static String[][] num2 = {{"*","*","*","*","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{"*","*","*","*","*"},{"*"," "," "," "," "},{"*"," "," "," "," "},{"*","*","*","*","*"}};
    public static String[][] num3 = {{"*","*","*","*","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{"*","*","*","*","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{"*","*","*","*","*"}};
    public static String[][] num4 = {{"*"," "," ","*"," "},{"*"," "," ","*"," "},{"*"," "," ","*"," "},{"*","*","*","*","*"},{" "," "," ","*"," "},{" "," "," ","*"," "},{" "," "," ","*"," "}};
    public static String[][] num5 = {{"*","*","*","*","*"},{"*"," "," "," "," "},{"*"," "," "," "," "},{"*","*","*","*","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{"*","*","*","*","*"}};
    public static String[][] num6 = {{"*","*","*","*","*"},{"*"," "," "," "," "},{"*"," "," "," "," "},{"*","*","*","*","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*","*","*","*","*"}};
    public static String[][] num7 = {{"*","*","*","*","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{" "," "," "," ","*"}};
    public static String[][] num8 = {{"*","*","*","*","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*","*","*","*","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*","*","*","*","*"}};
    public static String[][] num9 = {{"*","*","*","*","*"},{"*"," "," "," ","*"},{"*"," "," "," ","*"},{"*","*","*","*","*"},{" "," "," "," ","*"},{" "," "," "," ","*"},{"*","*","*","*","*"}};

    //step2.存放闪烁点
    public static String[][] dot = new String[][]{{" "," "," "," "," "},{" "," "," "," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," ","*"," "," "},{" "," "," "," "," "},{" "," "," "," "," "}};

    public static String[][][] numAll = new String[][][] { num0, num1, num2, num3, num4, num5, num6, num7, num8, num9 };
    public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public static void main(String[] args) {
        print();
    }

    public static void print() {
        String[][][] newStr = getDateArray();
        for (int i = 0; i < 7; i++) {              //相当于单片机上每个数码管的行数
            for (int k = 0; k < newStr.length; k++) {
                for (int j = 0; j < 5; j++) {      //单片机每个数码管的列数
                    System.out.print(newStr[k][i][j]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static String[][][] getDateArray() {
        String[][][] dateArray = new String[5][][]; //一共五个位置
        String dateStr = sdf.format(new Date());
        char[] dateChars = dateStr.toCharArray();
        for (int i = 0; i < dateChars.length; i++) {
            switch (dateChars[i]) {
                case ':':
                    dateArray[i] = dot;
                    break;
                default:
                    dateArray[i] = numAll[Integer.valueOf(String.valueOf(dateChars[i]))];
                    break;
            }
        }

        return dateArray;
    }

}

