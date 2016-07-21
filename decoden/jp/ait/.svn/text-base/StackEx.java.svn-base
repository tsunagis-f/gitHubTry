package jp.ait;


import java.util.*;

/**
 * Stackの容量を３０までにした　拡張Stack
 * 
 * @author kagi
 *
 */
class StackEx extends Stack{

	final static int STACK_MAX = 15;

	/*
	 * スタックの最大容量を STACK_MAXまでとする
	 */
	public void push(Operation obj) {
		
		// 現在のスタックのサイズを得る
		int stack_size = size();
		
		// スタックの容量をオーバーしたときは古いオブジェクトを吐き出して、
		// 新しいオブジェクトを入れる
		if (stack_size >= STACK_MAX) {
			Object obj2 = elementAt(0);
			removeElementAt(0);
			((Operation)obj2).delete();
			obj2 = null;
		}
		super.push(obj);
	}
	
	public void clear() {
		int stack_size = size();
		for (int i = 0; i < stack_size; i++) {
			Object obj = pop();
			((Operation)obj).clear();
		}
		
		super.clear();
	}
}

