import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberList
  implements Serializable
{
  static final String SPACES = "                                                                                                                                                     ";
  private static final long serialVersionUID = 1L;
  private ArrayList<Member> MemberL = new ArrayList();
  
  public void addMember(String name)
  {
    this.MemberL.add(new Member(name, 0));
  }
  
  public void delMember(int index)
  {
    this.MemberL.remove(index);
  }
  
  public String ShowMemberList()
  {
    StringBuffer sb = new StringBuffer();
    String s = "  Name                          DKP";
    sb.append(s + "\n" + "\n");
    
    int l = s.length();
    for (Member m : this.MemberL)
    {
      Member temp = m;
      int index = this.MemberL.indexOf(temp) + 1;
      Integer Windex = Integer.valueOf(index);
      String name = temp.getName();
      int DKP = temp.getDKP();
      int nl = Windex.toString().length() + 1 + name.length();
      int space = l - nl;
      System.out.println("space is "+space);
      sb.append(index + ". " + name + "                                                                                                                                                                                        ".substring(1, space) + DKP + "\n");
    }
    String Final = sb.toString();
    return Final;
  }
  
  public void editDKP(int PmIndex, int MemberIndex, int parameter)
  {
    Member MemberTemp = (Member)this.MemberL.get(MemberIndex);
    if (PmIndex == 0) {
      MemberTemp.addDKP(parameter);
    } else {
      MemberTemp.redDKP(parameter);
    }
  }
  
  public void editDKPC(int PmIndex, int parameter)
  {
    if (PmIndex == 0) {
      for (Member m : this.MemberL) {
        m.addDKP(parameter);
      }
    } else {
      for (Member m : this.MemberL) {
        m.redDKP(parameter);
      }
    }
  }
  
  public void stop()
  {
    System.out.println("---------------------");
    System.out.println("Input anything to return");
    
    Scanner se = new Scanner(System.in);
    if (se.hasNext()) {
      se.next();
    }
  }
  
  public Object[] getNameList()
  {
    ArrayList<String> NameList = new ArrayList();
    for (Member m : this.MemberL) {
      NameList.add(m.getName());
    }
    Object[] NameLists = NameList.toArray();
    return NameLists;
  }
}
