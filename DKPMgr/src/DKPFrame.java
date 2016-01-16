import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class DKPFrame
  extends JFrame
{
  public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
  public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
  MemberList ml;
  Image GuildF;
  ImageIcon icon;
  
  public static boolean checkExist()
  {
    File f = new File("save/save.ser");
    if (f.exists()) {
      return true;
    }
    return false;
  }
  
  public static void save(MemberList Ml)
    throws IOException
  {
    FileOutputStream fs = new FileOutputStream("save/save.ser");
    ObjectOutputStream os = new ObjectOutputStream(fs);
    os.writeObject(Ml);
    os.close();
  }
  
  public static MemberList load()
    throws IOException, ClassNotFoundException
  {
    FileInputStream fi = new FileInputStream("save/save.ser");
    
    ObjectInputStream oi = new ObjectInputStream(fi);
    return (MemberList)oi.readObject();
  }
  
  public void init()
    throws ClassNotFoundException, IOException
  {
	  
	  this.setTitle("DKP Manager");
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] f = ge.getAvailableFontFamilyNames();
    String[] arrayOfString1;
    int j = (arrayOfString1 = f).length;
    for (int i = 0; i < j; i++)
    {
      String fe = arrayOfString1[i];
      System.out.println(fe);
    }
    BufferedImage Guild = ImageIO.read(new FileInputStream("Image/Guild.png"));
    this.GuildF = Guild.getScaledInstance(150, 150, 4);
    this.icon = new ImageIcon(this.GuildF);
    JLabel im = new JLabel();
    im.setIcon(this.icon);
    if (checkExist()) {
      this.ml = load();
    } else {
      this.ml = new MemberList();
    }
    JPanel buttons = new JPanel();
    Dimension d = new Dimension(30, 50);
    
    JButton b2 = new JButton("Add Member");
    JButton b3 = new JButton("Edit DKP Individually");
    b3.setSize(30, 60);
    JButton b4 = new JButton("Edit DKP Colectively");
    b4.setSize(30, 60);
    
    JButton b6 = new JButton("Delete Member");
    
    b6.setSize(d);
    
    b2.setSize(d);
    
    buttons.setLayout(new BoxLayout(buttons, 2));
    
    buttons.add(b2);
    
    buttons.add(b6);
    
    buttons.add(b3);
    
    buttons.add(b4);
    
    buttons.add(Box.createHorizontalGlue());
    
    add(buttons, "Last");
    
    setVisible(true);
    
    setLocation(WIDTH / 2, HEIGHT / 2);
    
    setSize(600, 480);
    
    setDefaultCloseOperation(3);
    
    final JEditorPane ep = new JEditorPane();
    
    ep.setFont(new Font("Courier New", Font.PLAIN, 14));
    
    ep.setSize(300, 400);
    
    ep.setEditable(false);
    
    ep.setText(this.ml.ShowMemberList());
    
    add(ep, "Center");
    
    add(im, "East");
    
    b2.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent me)
      {
        String name = JOptionPane.showInputDialog("Please Input the Name of the Member!");
        
        DKPFrame.this.ml.addMember(name);
        ep.setText(DKPFrame.this.ml.ShowMemberList());
        try
        {
          DKPFrame.save(DKPFrame.this.ml);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    b3.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent me)
      {
        String[] options = { "Plus", "Minus" };
        Object[] Members = DKPFrame.this.ml.getNameList();
        int PmIndex = JOptionPane.showOptionDialog(null, "Plus or Minus DKP?", "Please Choose", -1, 3, null, options, null);
        int MemberIndex = JOptionPane.showOptionDialog(null, "Which Member?", "Please Choose", -1, 3, null, Members, null);
        int parameter;
//        int parameter;
        if (PmIndex == 0) {
          parameter = Integer.parseInt(JOptionPane.showInputDialog("Enter the number to add"));
        } else {
          parameter = Integer.parseInt(JOptionPane.showInputDialog("Enter the number to reduce"));
        }
        DKPFrame.this.ml.editDKP(PmIndex, MemberIndex, parameter);
        ep.setText(DKPFrame.this.ml.ShowMemberList());
        try
        {
          DKPFrame.save(DKPFrame.this.ml);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    b4.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent me)
      {
        String[] options = { "Plus", "Minus" };
        
        int PmIndex = JOptionPane.showOptionDialog(null, "Plus or Minus DKP?", "Please Choose", -1, 3, null, options, null);
        int parameter;
//        int parameter;
        if (PmIndex == 0) {
          parameter = Integer.parseInt(JOptionPane.showInputDialog("Enter the number to add"));
        } else {
          parameter = Integer.parseInt(JOptionPane.showInputDialog("Enter the number to reduce"));
        }
        DKPFrame.this.ml.editDKPC(PmIndex, parameter);
        ep.setText(DKPFrame.this.ml.ShowMemberList());
        try
        {
          DKPFrame.save(DKPFrame.this.ml);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    b6.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent me)
      {
        Object[] Members = DKPFrame.this.ml.getNameList();
        int MemberIndex = JOptionPane.showOptionDialog(null, "Which Member?", "Please Choose", -1, 3, null, Members, null);
        DKPFrame.this.ml.delMember(MemberIndex);
        ep.setText(DKPFrame.this.ml.ShowMemberList());
        try
        {
          DKPFrame.save(DKPFrame.this.ml);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    Enumeration<Object> keys = UIManager.getDefaults().keys();
    Object key = null;
    Object value = null;
    while (keys.hasMoreElements())
    {
      key = keys.nextElement();
      value = UIManager.get(key);
      if ((value instanceof Font)) {
        UIManager.put(key, new Font("Courier New", 1, 20));
      }
    }
  }
}
