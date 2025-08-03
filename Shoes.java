
/**
 * Write a description of class Shoes here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Shoes 
{
    private double size, disc;
    private String type, id, prodCode;
    private int qty;
    private Customer c;
    private Purchase p;
    
    public Shoes(String id, String code, double size, String type, int qty,  Customer c, Purchase p){
        this.size=size;
        this.type=type;
        this.id=id;
        this.prodCode=code;
        this.qty = qty;
        this.c = c;
        this.p= p;
    }
    
    public void setQty(int qty){this.qty=qty;}
    public void setSize(double size){this.size=size;}
    public void setType(String type){this.type=type;}
    public void setID(String id){this.id=id;}
    public void setCode(String code){this.prodCode=code;}
    public void setPurchase(Purchase p){this.p=p;}
    
    public int getQty(){return qty;}
    public double getSize(){return size;}
    public String getType(){return type;}
    public String getID(){return id;}
    public String getCode(){return prodCode;}
    
    public Customer getCustomer(){return c;}
    public Purchase getPurchase(){return p;}
    
    public abstract double price();
    
    public double sumPrice(){
        return price()* getQty();
    }
    
    public double afterDiscount(){
        return sumPrice()-(sumPrice()*discount());
    }
    
    public double discount(){
        if(sumPrice()>600){
            disc=0.3;
        }else{
            disc=0;
        }
        return disc;
    }
    
    public String receipt(){
        return("\n\t═════════════════ RECEIPT ════════════════════"+
                "\n\tPrice per item             : RM "+price()+
                "\n\tDiscount                   : " + (int)(discount() * 100) + "%"+
                "\n\tTotal Price                : RM "+sumPrice()+
                "\n\tTotal Price after discount : RM "+afterDiscount()+
                "\n\tPayment Method             : "+p.getPayMethod());
    }
    public String toString(){
        return("\n\t═════════════════"+getID()+" Order════════════════════"+
                "\n\tProduct Code               : "+getCode()+
                "\n\tProduct Size               : "+getSize()+
                "\n\tProduct type               : "+getType()+
                "\n\tQuantity                   : "+getQty());
    }
    
    
}
