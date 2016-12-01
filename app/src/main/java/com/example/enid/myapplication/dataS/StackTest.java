package com.example.enid.myapplication.dataS;

import java.util.Stack;

/**
 * Created by big_love on 2016/11/30.
 * 栈 LIFO 后进先出 数据结构
 */

public class StackTest {
    Stack<String> mStack = new Stack<>();

    private void add(String s) {
        mStack.add(s);
    }

    private void push(String s) {
        mStack.push(s);
    }

    /**
     * 移除栈顶元素
     */
    private void pop() {
        mStack.pop();
    }

    /**
     * 查看栈顶元素
     */
    private void peek() {
        mStack.peek();
    }

    /**
     * 判断栈是否为空
     */
    private void empty() {
        mStack.empty();
    }
}
