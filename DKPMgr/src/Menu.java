import java.io.PrintStream;
import java.util.Scanner;

public class Menu
{
  public int Welcome()
  {
    Scanner s = new Scanner(System.in);
    System.out.println("Welcome to Yiyunge DKP Manager Created By TANG DIAN");
    System.out.println("----------------------------------------------------");
    System.out.println("1.Show the DKP of all members.");
    System.out.println("2.Add a Member.");
    System.out.println("3.Delete a Member.");
    System.out.println("4.Edit DKP Individually");
    System.out.println("5.Edit DKP Collectively");
    System.out.println("6.Save");
    System.out.println("7.Exit");
    System.out.println("----------------------------------------------------");
    System.out.println("Please Input the Selection");
    return s.nextInt();
  }
}
