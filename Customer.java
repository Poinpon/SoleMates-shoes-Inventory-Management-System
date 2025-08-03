
/**
 * Write a description of class Customer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Customer
{
    private String name, custNum;
    
    public Customer(String name, String custNum){
        this.name=name;
        this.custNum=custNum;
    }
    
    public void setCustNum(String custNum){this.custNum=custNum;}
    public void setName(String name){this.name=name;}
    
    public String getCustNum(){return custNum;}
    public String getName(){return name;}
    
    public String custInfo(){
        return("\n\t═══════════════CUSTOMER INFO══════════════════"+
                "\n\tCustomer Name              : "+getName()+
                "\n\tPhone Number               : "+getCustNum());
    }
}
