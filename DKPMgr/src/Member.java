import java.io.Serializable;

public class Member
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int DKP;
  private String Name;
  
  Member(String Name, int DKP)
  {
    setName(Name);
    this.DKP = DKP;
  }
  
  public int getDKP()
  {
    return this.DKP;
  }
  
  public void addDKP(int addition)
  {
    this.DKP += addition;
  }
  
  public void redDKP(int reduction)
  {
    this.DKP -= reduction;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String Name)
  {
    this.Name = Name;
  }
}
