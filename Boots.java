
/**
 * Write a description of class Boots here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Boots extends Shoes {
    public Boots(String id,String code,double size, String type, int qty, Customer c, Purchase p) {
        super(id, code, size, type, qty, c, p);
    }
    
    public double price(){
        return 450;
    }
}