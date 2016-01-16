import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

public class Driver
{
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
  
  public static void BlankL()
  {
    for (int i = 0; i < 30; i++) {
      System.out.println("\n");
    }
  }
  
  public static void main(String[] args)
    throws IOException, ClassNotFoundException
  {
    DKPFrame DF = new DKPFrame();
    DF.init();
    BlankL();
    Menu M = new Menu();
    MemberList Ml;
//    MemberList Ml;
    if (checkExist()) {
      Ml = load();
    } else {
      Ml = new MemberList();
    }
    for (;;)
    {
      boolean flag = true;
      int i = 0;
      try
      {
        i = M.Welcome();
      }
      catch (InputMismatchException ime)
      {
        flag = false;BlankL();System.out.println("Error input!You must Enter a number ABOVE! Please Try Again!");Ml.stop();BlankL();
      }
      if (flag) {
        switch (i)
        {
        case 1: 
          BlankL();Ml.ShowMemberList();Ml.stop();BlankL(); break;
        case 7: 
          System.out.println("byebye!");System.exit(0);
        case 6: 
          save(Ml);BlankL(); break;
        case 2: 
        case 3: 
        case 4: 
        case 5: 
        default: 
          BlankL();System.out.println("Error input!You must choose the number ABOVE!Please Try Again!");Ml.stop();BlankL();
        }
      }
    }
  }
}
