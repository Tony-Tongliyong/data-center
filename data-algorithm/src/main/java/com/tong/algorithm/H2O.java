package com.tong.algorithm;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: H2O
 * @time: 2019/10/17 14:04
 * @desc:
 */
public class H2O {
    public static int H = 0;

    public static int O = 0;

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while(H / 2 > O / 3){
            Thread.yield();
        }
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        H++;
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while(H / 2 < O / 3){
            Thread.yield();
        }
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        O++;
    }
}