package com.example.enid.myapplication.dataS;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by big_love on 2016/11/30.
 * 阻塞队列 常用于生产者、消费者场景
 * 在队列为空时，获取元素的线程会等待队列变为非空
 * 在队列满时，存储元素的线程会等待队列可用
 */

public class BlockingQueueTest {
    private static final int OUT_TIME = 5 * 1000;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(100, true);
    private LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(100);
    private BlockingQueue<String> mQueue = new LinkedBlockingDeque<>(3);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //插入方法
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 插入方法 抛出异常
     *
     * @param s
     */
    public void add(String s) {
        boolean add = arrayBlockingQueue.add(s);
    }

    /**
     * 插入方法 返回特殊值
     *
     * @param s
     */
    public void offer(String s) {
        boolean offer = arrayBlockingQueue.offer(s);
    }

    /**
     * 超时退出
     *
     * @param s
     */
    public void offerExit(String s) {
        try {
            boolean offer = arrayBlockingQueue.offer(s, OUT_TIME, TIME_UNIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一直堵塞
     *
     * @param s
     */
    public void put(String s) {
        try {
            arrayBlockingQueue.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //移除方法
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 抛出异常
     */
    public void remove() {
        arrayBlockingQueue.remove();
    }

    /**
     * 返回特殊值
     */
    public void poll() {

        arrayBlockingQueue.poll();
    }

    /**
     * 超时退出
     */
    public void pollExit() {
        try {
            arrayBlockingQueue.poll(OUT_TIME, TIME_UNIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一直堵塞
     */
    public void take() {
        try {
            arrayBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //检查方法
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 返回第一个元素，抛出异常
     */
    public void element() {
        String element = arrayBlockingQueue.element();
    }

    /**
     * 返回第一个元素，返回特殊值
     */
    public void peek() {
        arrayBlockingQueue.peek();
    }

}
