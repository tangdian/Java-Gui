package dean;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;


@SuppressWarnings("serial")
public class FiveChessFrame extends JFrame {
	/*
	 * @Dean Tang
	 * @2014.7
	 * All rights reserved. 
	 */

	public static final int XSHIFT = 51;
	public static final int YSHIFT = 71;
	public static final int SEP = 51;

	
	static double width = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	static double height = Toolkit.getDefaultToolkit().getScreenSize().height ;
	
	static double ratioW = width/1680;
	static double ratioH = height/1050;
	
	Image bgImage ;
	Image WhiteChess;
	Image BlackChess; 
	int [][] Chess = new int [19][19];
	boolean isBlack = true;
	String[] options = {"Black","White"};
	int i,j; 
	private Image buffer;
	private Graphics g2;
	private JButton b1;
	private JLabel p1;
	private boolean CanReg = true;

	

	public void init () {
		this.setLayout(null);
	if(JOptionPane.showOptionDialog(this,"Select the first color","Selection",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0])==0)
			{isBlack = true;}
		else {isBlack = false;}
		System.out.println(isBlack);
		System.out.println(width);
		System.out.println(height);
		try {
		
			BufferedImage bgImageO = ImageIO.read(new File("Images/Board.jpg"));
			BufferedImage WhiteChessO=ImageIO.read(new File("Images/WhiteChess.png"));
			BufferedImage BlackChessO=ImageIO.read(new File("Images/BlackChess.png"));
			bgImage = bgImageO.getScaledInstance((int)(ratioW*1024),(int)(ratioH*1024),Image.SCALE_SMOOTH);
			WhiteChess= WhiteChessO.getScaledInstance((int)(ratioW*40),(int)(ratioH*38),Image.SCALE_SMOOTH);
			BlackChess= BlackChessO.getScaledInstance((int)(ratioW*40),(int)(ratioH*38),Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		this.setTitle("GoBang");
		b1 = new JButton("REGRET");
		p1 = new JLabel();
		b1.setBounds ((int)(ratioW*1025),(int)(ratioH*300),(int)(ratioW*80),(int)(ratioH*50));
		p1.setBounds((int)(ratioW*1100),(int)(ratioH*200),(int)(ratioW*200),(int)(ratioH*30));
		this.add(p1);
		this.add(b1);
		this.setSize((int)(ratioW*1224 ),(int)(ratioH*1024));
		this.setLocation(0,0);	
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent me)  {
				System.out.println ("X is:"+me.getX());
				System.out.println("Y is :"+me.getY());
				double x = me.getX();
				double y = me.getY();
				double a = (x-ratioW*XSHIFT)/(ratioW*SEP);
				double b = (y-ratioH*YSHIFT)/(ratioH*SEP);
				System.out.println(a);
				System.out.println(b);
				i = (int) Math.round(a);
				j = (int) Math.round(b);
				if(i>19||j>19) return;
				if (Chess[i][j]==0){
				if (isBlack==true) {
					Chess[i][j] = 1;
					isBlack = false;
					CanReg = true;
					p1.setText("REGRET AVAILABLE");
				}
				else if (isBlack==false){
					Chess[i][j]=2;
					isBlack = true;
					CanReg = true;
					p1.setText("REGRET AVAILABLE");
				}
				
				
				}
				FiveChessFrame.this.repaint();
				boolean WinFlag = FiveChessFrame.this.checkWin();
				if (WinFlag) {
					if (!isBlack)
					JOptionPane.showMessageDialog(FiveChessFrame.this, "Black is the winner!");
					if (isBlack) JOptionPane.showMessageDialog(FiveChessFrame.this, "White is winner!");
					for (int i = 0 ;  i < Chess.length ; i++ ){
						for (int j =0; j<Chess.length; j++){
							Chess[i][j] =0;
							FiveChessFrame.this.repaint();
						}
				
			}
		}}}); 
		b1.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				if (CanReg) {if (isBlack){
				Chess[i][j] =0;
				isBlack = false;
				FiveChessFrame.this.repaint();
				CanReg = false;
				}
				else if(!isBlack){
					Chess[i][j] =0;
					isBlack = true;
					FiveChessFrame.this.repaint();
				CanReg = false;
				
				}}
				else JOptionPane.showMessageDialog (FiveChessFrame.this,"YOU HAVE REGRETTED!! YOU CAN REGRET ONLY ONCE!");
				p1.setText("REGRET UNAVAILABLE");
		}});
	}
	public boolean checkWin() {
		int color = Chess[i][j];
		int countX = 1;
		int countY = 1;
		int countR = 1;
		int countL = 1;
		int Rc = 1;
		int Rd = 1;
		int Lc =1;
		int Ld = 1;
		int Yc = 1;
		int Yd = 1;
		int Xc = 1;
		int Xd = 1;
		
		if (i+Rc <Chess.length && j+Rc <Chess.length) {
			while (Chess[i+Rc][j+Rc] == color){
			countR++;
			Rc++;
		}
			}
		if (i-Rd >0 && j-Rd>0){while (Chess[i-Rd][j-Rd] == color){
			countR++;
			Rd++;
		}}
		if (i-Lc >0 && j+Lc <Chess.length) {while (Chess[i-Lc][j+Lc] == color){
			countL++;
			Lc++;
		}}
		if (i+Ld <Chess.length && j-Ld>0){while (Chess[i+Ld][j-Ld] == color){
			countL++;
			Ld++;
		}}
		
		
		if (i+Xc <Chess.length){while (Chess[i+Xc][j]==color ){
			countX++;
			Xc++;
		}}
		if(i-Xd >0 ) {while (Chess[i-Xd][j] == color ){
			countX++;
			Xd++;
		}}
		if(j+Yc <Chess.length) {while (Chess[i][j+Yc]==color ){
			countY++;
			Yc++;
		}}
		if (j-Yd >0) {while (Chess[i][j-Yd] == color ){
			countY++;
			Yd++;
		}}
		if (countX == 5||countY==5||countR==5||countL==5) return true;
		else return false;
	
	}
	
	
	public void paint(Graphics g) {
		buffer = this.createImage((int)(1024*ratioW),(int)(1024*ratioH));
		
		g2 = buffer.getGraphics();
		g2.setFont(new Font("091-CAI978",Font.BOLD,22));
	
		
		g2.drawImage (bgImage,0,(int)(ratioH*20),this);
		

		for (int i = 0 ;  i < Chess.length ; i++ ){
			for (int j =0; j<Chess.length; j++){
				if (Chess[i][j]==1) {
		
				
					g2.drawImage(BlackChess,(int)((XSHIFT+SEP*i-20)*ratioW), (int)((YSHIFT+SEP*j-20)*ratioH),this);
				}
				if (Chess[i][j]==2) {

					g2.drawImage(WhiteChess,(int)((XSHIFT+SEP*i-20)*ratioW), (int)((YSHIFT+SEP*j-20)*ratioH),this);
					
				}
			}
		}
		if (isBlack) g2.drawString("BLACK TURN", (int)(400*ratioW), (int)(70*ratioH));
		else g2.drawString("WHITE TURN", (int)(400*ratioW), (int)(70*ratioH));
	    g2.drawString("Created by Dian Tang", (int)(50*ratioW), (int)(70*ratioH));
		g.drawImage(buffer,0,0,this);
		
	}
}
