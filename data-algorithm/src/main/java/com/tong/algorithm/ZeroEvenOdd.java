package com.tong.algorithm;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: ZeroEvenOdd
 * @time: 2019/10/17 14:22
 * @desc:
 */
public class ZeroEvenOdd {
    private int n;

    private static int i = 0;
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (i % 2 == 0 ) {
            printNumber.accept(0);
            i++;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
         while(i % 4 == 1) {
             printNumber.accept(i / 2 + 2);
             i++;
         }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
         while(i % 4 == 3 ) {
             printNumber.accept(i / 2 + 1);
             i++;
         }
    }


}