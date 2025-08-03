
/**
 * Write a description of class Purchase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Purchase
{
    private Customer c;
    private Shoes s;
    private int qty;
    private String payMethod;
    public double disc=0;
    
    public Purchase(String pm, Customer c, Shoes s){
        this.payMethod=pm;
        this.c=c;
        this.s=s;
    }
    
    public void setPayMethod(String pm){this.payMethod=pm;}
    public void setSize(double size){s.setSize(size);}
    public void setType(String type){s.setType(type);}
    public void setID(String id){s.setID(id);}
    public void setCode(String code){s.setCode(code);}
    public void setCustNum(String p){c.setCustNum(p);}
    public void setCustName(String cName){c.setName(cName);}
    
    public String getPayMethod(){return payMethod;}
    public double getSize(){return s.getSize();}
    public String getType(){return s.getType();}
    public String getID(){return s.getID();}
    public String getCode(){return s.getCode();}
    public String getPhoneNum(){return c.getCustNum();}
    public String getCustName(){return c.getName();}
    
}
