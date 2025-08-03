
/**
 * Write a description of class Heels here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Heels extends Shoes{
    public Heels(String id,String code,double size, String type, int qty, Customer c, Purchase p){
        super(id, code,size, type, qty, c, p);
    }
    
    public double price(){
        return 400;
    }
}
