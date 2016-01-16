
package com.dean;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;

public class ChessFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static double width = Toolkit.getDefaultToolkit().getScreenSize().width;
	static double height = Toolkit.getDefaultToolkit().getScreenSize().height ;
	static double ratioW = width/1680;
	static double ratioH= height/1050;
	
	Image Board;
	Image Blue1;
	Image Blue2;
	Image Red2;
	Image Red1;
	Image Buffer;
	Graphics g2;

	boolean isRed = true;
	boolean isLocal = false;
	RedChess RC1;
	RedChess RC2;
	BlueChess BC1;
	BlueChess BC2;
	int scoreB;
	int scoreR;
	boolean linked;
	JLabel scoreBoard;
	boolean isServer = false;
	
	public void init() throws IOException, Exception{
	try {
		
		JOptionPane.showMessageDialog(this, "This is a two player flying chess game. \nBy pressing button, you can choose which plane to drive. \nThe first player who exactly rolls to the terminal wins! \nP.S. You can distroy opponents plane!");

		
		BufferedImage BoardB = ImageIO.read(new FileInputStream("Images/Board.png"));
		BufferedImage BlueB1=ImageIO.read(new FileInputStream("Images/Blue1.png"));
		BufferedImage RedB1=ImageIO.read(new FileInputStream("Images/Red1.png"));
		BufferedImage BlueB2=ImageIO.read(new FileInputStream("Images/Blue2.png"));
		BufferedImage RedB2=ImageIO.read(new FileInputStream("Images/Red2.png"));
		Board = BoardB.getScaledInstance((int)(ratioW*1024),(int)(ratioH*1024), Image.SCALE_SMOOTH);
		Blue1 = BlueB1.getScaledInstance((int)(ratioW*80),(int)(ratioH*90),Image.SCALE_SMOOTH);
		Red1 = RedB1.getScaledInstance((int)(ratioW*80),(int)(ratioH*90),Image.SCALE_SMOOTH);
		Blue2 = BlueB2.getScaledInstance((int)(ratioW*80),(int)(ratioH*90),Image.SCALE_SMOOTH);
		Red2 = RedB2.getScaledInstance((int)(ratioW*80),(int)(ratioH*90),Image.SCALE_SMOOTH);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	Font font = new Font("Monospaced",Font.BOLD,(int)(ratioW*32));
	Font font2 = new Font("Monospaced",Font.BOLD,(int)(ratioW*20));
	this.setSize((int)(ratioW*1224 ),(int)(ratioH*1024));
	this.setLocation(0,0);	
	this.setResizable(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	this.setTitle("FlyChess TCP+LOCAL");
	
	JLabel status = new JLabel("HELLO");
	scoreBoard = new JLabel("R Score" + scoreR +"|" +"B Score" + scoreB);
	status.setFont(font);
	JButton roll = new JButton("1");
	JButton roll2 =new JButton("2");
	roll.setEnabled(false);
	roll2.setEnabled(false);
	linked = false;
	
	
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	p2.setLayout(new GridLayout(2,1));
	p2.add(roll);
    p2.add(roll2);
	
	
	p1.setLayout(new BorderLayout());
	p1.add(status,BorderLayout.CENTER);
	p1.add(scoreBoard, BorderLayout.NORTH);
	scoreBoard.setFont(font2);
	p1.add(p2,BorderLayout.EAST);


	p1.setLocation(1100,400);
	this.add(p1, BorderLayout.EAST);
	this.setVisible(true);
	
	
	RC1 = new RedChess();
	RC2 = new RedChess();
	BC2 = new BlueChess();
	BC1 = new BlueChess();

	
	String[] options = new String[3];
	options[0] = "Server";
	options[1] = "Client";
	options[2] = "local";
	int selection = JOptionPane.showOptionDialog(this,"What is you?","Selection",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
	if(selection==0)
	{isServer = true;
	this.repaint();
	}
	else if (selection ==2) {
		isLocal = true;
	}
	
	if(isServer) {
		ServerSocket ss = new ServerSocket(8888);
		Socket s = ss.accept();
		System.out.println(s);
		roll.setEnabled(true);
		roll2.setEnabled(true);
		linked = true;
		this.repaint();
		
	
		ObjectOutputStream oos = new ObjectOutputStream (s.getOutputStream());
		
	

	roll.addActionListener( new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
	
			int step = (int) (Math.random() * 5 + 1);
			status.setText("R1:"+step);
				RC1.setSum(RC1.getSum() + step);
			
				ChessFrame.this.repaint();
		
		
			
				
				roll.setEnabled(false);
				roll2.setEnabled(false);
				
					try {
						System.out.println("I am ready to write RC1" + RC1.getSum());
						oos.writeInt(1);
						oos.flush();
					
						oos.writeObject(RC1);
						System.out.println("I have wrote RC1");
						oos.flush();
						oos.reset();
						
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
					ChessFrame.this.checkEat();
					ChessFrame.this.checkWin();
                    isRed = false;
	
			}
			
		
	});
	
	roll2.addActionListener( new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int step = (int) (Math.random() * 5 + 1);
			
			status.setText("R2:"+step);
			
	
				RC2.setSum(RC2.getSum() + step);
			
				ChessFrame.this.repaint();
			
				
				
				
				roll.setEnabled(false);
				roll2.setEnabled(false);
				
				try {
					System.out.println("I am ready to write RC2" + RC2.getSum());
					oos.writeInt(2);
					oos.flush();
				
					oos.writeObject(RC2);
					System.out.println("I have wrote RC2");
					oos.flush();
					oos.reset();
				
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				ChessFrame.this.checkEat();
				ChessFrame.this.checkWin();
				isRed = false;
			}
			
		    
			
		
		
	});
	
	ObjectInputStream ois = new ObjectInputStream (s.getInputStream());
	int t;
	while (true) {
		System.out.println("Reday to read t!");
		t = ois.readInt();
		System.out.println("t is read!");
		System.out.println(t);
		if (t == 1 ) {
			BC1 = (BlueChess)ois.readObject();
			System.out.println(BC1);
			System.out.println("BC1 is read");
			
		}
		else {
			BC2 = (BlueChess)ois.readObject();
			System.out.println(BC2);
			System.out.println("BC2 is read");
		}

		
		
		
	
		this.repaint();
		this.checkEat();
		roll.setEnabled(true);
		roll2.setEnabled(true);
		isRed = true;
		
		
	}
	}
	else if(isLocal) {
		
		roll.setEnabled(true);
		roll2.setEnabled(true);
		
		roll.addActionListener( new ActionListener() {
		

			@Override
			public void actionPerformed(ActionEvent e) {
				int step = (int) (Math.random() * 5 + 1);
				
				if (isRed) status.setText("R1:"+step);
				else status.setText("B1:"+step);
				
				if (isRed) {
					RC1.setSum(RC1.getSum() + step);
				
					ChessFrame.this.repaint();
				
					if (RC1.getSum() == 20) {
					
						ChessFrame.this.repaint();
						JOptionPane.showMessageDialog(ChessFrame.this, "Red win");
						RC1.setSum(0);
						RC2.setSum(0);
						BC1.setSum(0);
						BC2.setSum(0);
						scoreR += 1;
						scoreBoard.setText("R Score" + scoreR +"|"+ "B Score" + scoreB);
						ChessFrame.this.repaint();
				}
					ChessFrame.this.checkEat();
					isRed = false;
				}
				
				else {
					BC1.setSum(BC1.getSum() + step);
					
					ChessFrame.this.repaint();
					if (BC1.getSum() == 20) {
					
						ChessFrame.this.repaint();
						JOptionPane.showMessageDialog(ChessFrame.this, "Blue win");
						RC1.setSum(0);	RC2.setSum(0);
						BC1.setSum(0); BC2.setSum(0);
						scoreB += 1;
						scoreBoard.setText("R Score" + scoreR +"|" +"B Score" + scoreB);
						ChessFrame.this.repaint();
				}
					ChessFrame.this.checkEat();
					isRed = true;
				}
			    
			
			
				}
				
			
		});
		roll2.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int step = (int) (Math.random() * 5 + 1);
				
				if (isRed) status.setText("R2:"+step);
				else status.setText("B2:"+step);
				
				if (isRed) {
					RC2.setSum(RC2.getSum() + step);
				
					ChessFrame.this.repaint();
				
					if (RC2.getSum() == 20) {
					
						ChessFrame.this.repaint();
						JOptionPane.showMessageDialog(ChessFrame.this, "Red win");
						RC1.setSum(0);
						RC2.setSum(0);
						BC1.setSum(0);
						BC2.setSum(0);
						scoreR += 1;
						scoreBoard.setText("R Score" + scoreR +"|"+ "B Score" + scoreB);
						ChessFrame.this.repaint();
				}
					ChessFrame.this.checkEat();
					isRed = false;
				}
				
				else {
					BC2.setSum(BC2.getSum() + step);
					
					ChessFrame.this.repaint();
					if (BC2.getSum() == 20) {
					
						ChessFrame.this.repaint();
						JOptionPane.showMessageDialog(ChessFrame.this, "Blue win");
						RC1.setSum(0);	RC2.setSum(0);
						BC1.setSum(0); BC2.setSum(0);
						scoreB += 1;
						scoreBoard.setText("R Score" + scoreR +"|" +"B Score" + scoreB);
						ChessFrame.this.repaint();
				}
					ChessFrame.this.checkEat();
					isRed = true;
				}
			    
			
			
				}
				
			
		});
		

	}
	else {
		String ip = JOptionPane.showInputDialog("Please input Server's IP\n Please ensure that the server is waiting.");
		Socket s = new Socket(ip,8888);
		ObjectOutputStream oos;
		ObjectInputStream ois;
		
		ois = new ObjectInputStream (s.getInputStream());
		oos = new ObjectOutputStream (s.getOutputStream());
		
		
		
		roll.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int step = (int) (Math.random() * 5 + 1);
				
		
					BC1.setSum(BC1.getSum() + step);
					status.setText("B1:"+step);
					ChessFrame.this.repaint();
				
					
					roll.setEnabled(false);
					roll2.setEnabled(false);
				
					
					try {
						System.out.println("I am ready to write BC1" + BC1.getSum());
						oos.writeInt(1);
						oos.flush();
						
						oos.writeObject(BC1);
						System.out.println("I have wrote BC1");
					
						oos.flush();
						oos.reset();
						
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ChessFrame.this.repaint();
					ChessFrame.this.checkEat();
					ChessFrame.this.checkWin();
						isRed = true;
				}
			    
			
			
				
				
			
		});
		roll2.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int step = (int) (Math.random() * 5 + 1);
				
					BC2.setSum(BC2.getSum() + step);
					status.setText("B2:"+step);
					ChessFrame.this.repaint();
				
					
					
					roll.setEnabled(false);
					roll2.setEnabled(false);
					
					ChessFrame.this.repaint();
					try {
						System.out.println("I am ready to write BC2" + BC2.getSum());
						oos.writeInt(2);
						oos.flush();
					
						
						oos.writeObject(BC2);
						System.out.println("I have wrote BC2");
						oos.flush();
						oos.reset();
						
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
					
					ChessFrame.this.checkEat();
					ChessFrame.this.checkWin();
				isRed = true;
					
				}
			    
			
			
				
				
			
		});
		int t;
		while (true) {
			    //RC1 = null;
			System.out.println("Ready to read t!");
			t = ois.readInt();
			System.out.println("t is read!");
			System.out.println(t);
			if ( t == 1) {
				RC1 = (RedChess)ois.readObject();
				System.out.println(RC1);

				System.out.println("RC1 is read");
			}
			else {
				RC2 = (RedChess)ois.readObject();
				System.out.println(RC2);
				System.out.println("RC2 is read");
			}
		
			

			this.repaint();
			this.checkEat();
			this.checkWin();
			roll.setEnabled(true);
			roll2.setEnabled(true);
			isRed = false;
			
			
		}
		
			
		
			
			
			
		
	}
	}

	
	public void paint(Graphics g) {
		
		Buffer = this.createImage((int)(1224 * ratioW) ,(int)(1024 * ratioH ));
		
		g2 = Buffer.getGraphics();
		
		super.paint(g2);
		
		g2.drawImage (Board,0,0,this);

	

		if (RC1.getSum() == 0)  g2.drawImage (Red1, (int)(250*ratioW),(int)(850*ratioH),this);
		
		else g2.drawImage(Red1, (int)(((RC1.getCol() - 1) * 200 + 50)*ratioW),(int)(((RC1.getLineR() - 1) * 200 + 50)*ratioH), this);
		
        if (RC2.getSum() == 0)  g2.drawImage (Red2, (int)(50*ratioW),(int)(850*ratioH),this);
		
		else g2.drawImage(Red2, (int)(((RC2.getCol() - 1) * 200 + 50)*ratioW),(int)(((RC2.getLineR() - 1) * 200 + 50)*ratioH), this);
		
		if (BC1 != null && BC1.getSum() == 0)  g2.drawImage (Blue1, (int)(650*ratioW),(int)(850*ratioH),this);
		
		else g2.drawImage(Blue1, (int)(((BC1.getCol() - 1) * 200 + 50)*ratioW),(int)(((BC1.getLineR() - 1) * 200 + 50)*ratioH), this);

          if (BC2.getSum() == 0)  g2.drawImage (Blue2, (int)(850*ratioW),(int)(850*ratioH),this);
		
		else g2.drawImage(Blue2, (int)(((BC2.getCol() - 1) * 200 + 50)*ratioW),(int)(((BC2.getLineR() - 1) * 200 + 50)*ratioH), this);
		
          Font f = new Font("Monospaced",Font.BOLD,32);
          g2.setFont(f);
         if (!linked && isServer){
        	 g2.setColor(Color.GREEN);
        	 g2.drawString("WAITING", (int)(430*ratioW), (int)(200*ratioH));
        	 
         }
        else {
        	 if (isRed) {
            	 g2.setColor(Color.RED);
            	 g2.drawString("RED TURN", (int)(430*ratioW), (int)(200*ratioH));
             }
             else  {
            	 g2.setColor(Color.BLUE);
            	 g2.drawString("BLUE TURN", (int)(430*ratioW), (int)(200*ratioH));
             }
        	 
         }
         
		g.drawImage(Buffer,0,0,this); 
		
		
		
	
	}
	
	
	
	public void checkEat() {
		if (BC1.getSum() == RC1.getSum() ) {
			if (isRed) BC1.setSum(0);
			else RC1.setSum(0);
			ChessFrame.this.repaint();
		}
		if (BC2.getSum() == RC1.getSum() ) {
			if (isRed) BC2.setSum(0);
			else RC1.setSum(0);
			ChessFrame.this.repaint();
		}
		if (BC1.getSum() == RC2.getSum() ) {
			if (isRed) BC1.setSum(0);
			else RC2.setSum(0);
			ChessFrame.this.repaint();
		}
		if (BC2.getSum() == RC2.getSum() ) {
			if (isRed) BC2.setSum(0);
			else RC2.setSum(0);
			ChessFrame.this.repaint();
		}
		}
	
	
	public void checkWin() {
		if (BC2.getSum() == 20 || BC1.getSum() == 20) {
			
			ChessFrame.this.repaint();
			JOptionPane.showMessageDialog(ChessFrame.this, "Blue win");
			RC1.setSum(0);	RC2.setSum(0);
			BC1.setSum(0); BC2.setSum(0);
			scoreB += 1;
			scoreBoard.setText("R Score" + scoreR +"|" +"B Score" + scoreB);
			ChessFrame.this.repaint();
	}
	if (RC2.getSum() == 20 || RC1.getSum() == 20) {
			
			ChessFrame.this.repaint();
			JOptionPane.showMessageDialog(ChessFrame.this, "Red win");
			RC1.setSum(0);	RC2.setSum(0);
			BC1.setSum(0); BC2.setSum(0);
			scoreR += 1;
			scoreBoard.setText("R Score" + scoreR +"|" +"B Score" + scoreB);
			ChessFrame.this.repaint();
	}
	
	}
}
	

