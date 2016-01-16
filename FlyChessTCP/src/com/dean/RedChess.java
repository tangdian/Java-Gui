package com.dean;

import java.io.Serializable;

public class RedChess implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4042723969407051014L;
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
		//System.out.println("the sum of red is " + this.sum);
		//System.out.println("the col of red is :" + this.col);
		//System.out.println("The lineR of red is :" + this.lineR);
		
	}
	
	public String toString() {
		return "The sum of red chess is"+ sum;
		
	}
	

	
}
