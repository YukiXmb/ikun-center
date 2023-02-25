package com.yuki.usercenter;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第一个整数：");
        int i1 = scanner.nextInt();
        System.out.println("请输入第二个整数：");
        int i2 = scanner.nextInt();
        System.out.println("请输入第三个整数：");
        int i3 = scanner.nextInt();
        int a;
        if (i1 > i2){
            a = i1;
        } else {
            a = i2;
        }
        if (a < i3){
            a = i3;
        }
        System.out.println("最大的整数为：" + a);
    }
}
