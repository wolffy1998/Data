package cn.mango.array;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author mango
 * @Since 2020/3/15 15:49
 **/
public class Demo4_10 {

    public static void main(String[] args) {
        // 这样定义的数组是个矩阵
        int[][] a = new int[3][3];
        // 基本数据类型的包装类默认值是null
        Integer[][] b = new Integer[3][3];
        // Arrays.deepToString()方法用于将多为数组转化为字符串
        System.out.println(Arrays.deepToString(b));
        // 创建一个二维数组对象，其中包含5个一维数组对象，5个一维数组对象又可以包含多个对象通过数字索引访问
        // s表示二维数组对象，s[索引]表示一维数组对象，s[索引][索引]具体不是数组
        String[][] s = new String[5][];
        for (int i=0;i<s.length;i++) {
           s[i] = new String[i+1];
           for (int j=0;j<=i;j++) {
               s[i][j] = "*";
           }
        }
        for (int i=0;i<s.length;i++) {
            for (int j=0;j<s[i].length;j++)
                System.out.print(s[i][j]);
            System.out.println();
        }
    }


}
