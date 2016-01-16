package com.dean;

import java.io.Serializable;

public class BlueChess implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9117324177866750507L;
	private int sum;
	private int col;
	private int line;
	private int lineR;
	
	public int getLineR() {
		return lineR;
	}

	public int getLine() {
		return line;
	}

	public int getCol() {
		return col;
	}

	public int getSum() {
		return sum;
	}
	
	public void setSum(int sum) {
		this.sum = sum;
		
		
		
		if (this.sum > 20) {
			int exceed = this.sum - 20;
			this.sum = 20 - exceed;
		}
		
		if (this.sum % 5 == 0) line = this.sum / 5;
		else line = (int) (this.sum / 5) + 1;
	
		if (line % 2 != 0 ) col = this.sum - 5 * (line - 1);
		else col = 6 - (this.sum - 5 * (line - 1));
		
		lineR = 5 - line;
		
		//System.out.println("the sum of Blue is " + this.sum);
		//System.out.println("the col of blue is :" + this.col);
		//System.out.println("The lineR of blue is :" + this.lineR);
		
	}
	
	public String toString() {
		return "The sum of blue chess is"+ sum;
		
	}

}
