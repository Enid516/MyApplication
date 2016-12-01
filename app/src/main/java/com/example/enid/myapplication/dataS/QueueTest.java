package com.example.enid.myapplication.dataS;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by big_love on 2016/11/30.
 * Queue FIFO 队列 先进先出 数据结构
 * {@link , http://www.cnblogs.com/end/archive/2012/10/25/2738493.html}
 */

public class QueueTest {
    private static final int LIMIT_SIZE =10;
    Queue<String> mQueue = new LinkedList<>();

    /**
     * 在mQueue 最后添加一个元素，因容量限制，在添加元素失败时抛出异常
     * @param s
     */
    private void add(String s){
        mQueue.add(s);
    }


    /**
     * 在mQueue 最后添加一个元素
     * @param s
     */
    private void offer(String s){
        mQueue.offer(s);
    }

    /**
     *返回第一个元素，并在队列中删除,队列为空时抛出异常
     */
    private void remove(){
        mQueue.remove();
    }

    /**
     * 返回第一个元素，并在队列中删除,队列为空时返回null
     */
    private void poll(){
        mQueue.poll();
    }

    /**
     * 返回第一个元素，队列为空时抛出异常
     */
    private void element(){
        mQueue.element();
    }

    /**
     * 返回第一个元素，队列为空时返回null
     */
    private void peek() {
        mQueue.peek();
    }

    private void offerLimit(String s){
        if (mQueue.size() >= 10) {
            mQueue.poll();
            mQueue.offer(s);
        }
    }
}
