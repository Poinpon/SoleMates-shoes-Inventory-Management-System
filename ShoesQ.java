
/**
 * Write a description of class ShoesQ here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
public class ShoesQ
{
    private static Scanner in = new Scanner (System.in);
    private static Scanner inLine = new Scanner (System.in);
    public static void main (String args[]) throws IOException{
        try{
            Queue shoesQ = new Queue();
            
            FileReader fr = new FileReader ("shoesOrders.txt");
            BufferedReader br = new BufferedReader (fr);
            
            StringTokenizer st = null;
            String dataRow = br.readLine();
            
            while(dataRow != null){
                st = new StringTokenizer (dataRow, ";");
                
                String id = st.nextToken();
                String prodCode = st.nextToken();
                double size = Double.parseDouble(st.nextToken());
                String type = st.nextToken();
                int qty = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                String custNum = st.nextToken();
                String payMethod = st.nextToken();
                
                Customer c = new Customer(name, custNum);
                Purchase p = new Purchase(payMethod, c, null);
                
                Shoes s;
                if (prodCode.substring(0, 3).equalsIgnoreCase("SMH")) {
                    s = new Heels(id, prodCode, size, type, qty, c, p);
                } else if (prodCode.substring(0, 3).equalsIgnoreCase("SML")) {
                    s = new Loafers(id, prodCode, size, type, qty, c, p);
                } else if (prodCode.substring(0, 3).equalsIgnoreCase("SMB")) {
                    s = new Boots(id, prodCode, size, type, qty, c, p);
                } else {
                    System.err.println("Unknown product code: " + prodCode);
                    dataRow = br.readLine();
                    continue;
                }
                
                shoesQ.enqueue(s);
                dataRow = br.readLine();
            }
            
            br.close();
            fr.close();
            
            while(true){
                displayMenu();
                   
                System.out.print("\n\t\tEnter your choice (1/2/3/4/5/6/7): ");
                int choice = in.nextInt();
                in.nextLine();
                System.out.println("\n\t═════════════════════════════════════════════════════");
                   
                if(choice == 1){
                    searchOrder(shoesQ);
                }
                else if (choice == 2){
                    displayAllOrder(shoesQ);
                }
                else if (choice == 3){
                    deleteOrderByOrderID(shoesQ);
                }
                else if (choice == 4){
                    updateQuantityByOrderID(shoesQ);
                }
                else if (choice == 5){
                    addOrder(shoesQ);
                }
                else if (choice == 6){
                    calcTotalPrice(shoesQ);
                }
                else if(choice == 7){
                    System.out.println("Exiting the system... Goodbye!");
                    break;
                }
                else{
                    System.out.println("Invalid code!");
                    continue;
                }
            }
        } 
        catch (FileNotFoundException e){
            System.out.println("\nProblem: "+e.getMessage());
        }
        catch (EOFException eof){
            System.out.println("\nProblem: "+eof.getMessage());
        }
        catch (IOException ioe){
            System.out.println("\nProblem: "+ioe.getMessage());
        }
        finally{
            System.out.println("\nEnd of the program");
        }
    }
        
    public static void displayMenu(){
        System.out.println("\n\t\t   WELCOME TO SOLEMATE'S INVENTORY SYSTEM :>");
        System.out.println("\n\t╔════════════════════════════════════════════════════╗");
        System.out.println("\t\t\t\t    MENU");
        System.out.println("\n\t╚════════════════════════════════════════════════════╝");
        System.out.println("\t\t[1] Search order(s)");
        System.out.println("\t\t[2] Display all orders");
        System.out.println("\t\t[3] Delete order");
        System.out.println("\t\t[4] Update quantity");
        System.out.println("\t\t[5] Add order");
        System.out.println("\t\t[6] Calculate total price");
        System.out.println("\t\t[7] Exit");
        System.out.println("\n\t═════════════════════════════════════════════════════");
    }
        
    public static void searchOrder(Queue shoesQ){
        boolean search = true;
        while (search){
            System.out.println();
            System.out.println("\n\t═════════════════════════════════════════════════════");
            System.out.println("\t\t1. Search by order ID");
            System.out.println("\t\t2. Search by product code");
            System.out.println("\t\t3. Search by customer name");
            System.out.println("\t\t4. Return to Menu");
                
            System.out.print("\n\t\tEnter your choice (1/2/3/4): ");
            int code = in.nextInt();
            in.nextLine();
            
            boolean orderFound = false;
            Queue tempQ = new Queue();
            if(code == 1){
                System.out.print("\n\t\tEnter order ID: ");
                String id = in.nextLine().trim();
                
                while(!shoesQ.isEmpty()){
                    Shoes s = (Shoes) shoesQ.dequeue();
                    if(s.getID().equalsIgnoreCase(id)){
                        System.out.println("\n\n\n\tOrder found: " + s.toString());
                        System.out.println(s.getCustomer().custInfo());
                        orderFound = true;
                    }
                    tempQ.enqueue(s);
                }
            }
            else if (code == 2){
                System.out.print("\n\t\tEnter product code: ");
                String prodCode = in.nextLine().trim();
                    
                while(!shoesQ.isEmpty()){
                    Shoes s = (Shoes) shoesQ.dequeue();
                    if(s.getCode().equalsIgnoreCase(prodCode)){
                        System.out.println("\n\n\n\tOrder found: "+ s.toString());
                        System.out.println(s.getCustomer().custInfo());
                        orderFound = true;
                    }
                    tempQ.enqueue(s);
                }
            }
            else if (code == 3){
                System.out.print("\n\t\tEnter customer name: ");
                String name = in.nextLine().trim();
                    
                while(!shoesQ.isEmpty()){
                    Shoes s = (Shoes) shoesQ.dequeue();
                    if(s.getCustomer().getName().equalsIgnoreCase(name)){
                        System.out.println("\n\n\n\tOrder found: " + s);
                        System.out.println(s.getCustomer().custInfo());
                        orderFound = true;
                    }
                    tempQ.enqueue(s);
                }
            }
            else if(code==4){
                System.out.println("\nReturning to menu...");
                return;
            }
            else{
                System.out.println("\nInvalid code! Please try again.");
                continue;
            }
            
            while(!tempQ.isEmpty()){
                shoesQ.enqueue(tempQ.dequeue());
            }
                
            if(!orderFound){
                System.out.print("\n\t\tNo matching orders found. Would you like to try again? (Yes/No): ");
                String retry = in.nextLine();
                if(!retry.equalsIgnoreCase("Yes")){
                    search = false;
                }
            }
        }
    }
        
    public static void displayAllOrder(Queue shoesQ){
        System.out.println("\n\tList of all orders:");
        Queue tempQ = new Queue();
            
        while(!shoesQ.isEmpty()){
            Shoes s = (Shoes) shoesQ.dequeue();
            System.out.println(s.toString());
            tempQ.enqueue(s);
        }
        
        while(!tempQ.isEmpty()){
            shoesQ.enqueue(tempQ.dequeue());
        }
    }
        
    public static void deleteOrderByOrderID(Queue shoesQ){
        System.out.print("\n\t\tEnter order ID: ");
        String id = in.nextLine();
        boolean orderFound = false;
        
        Queue tempQ = new Queue();
        while(!shoesQ.isEmpty()){
            Shoes s = (Shoes) shoesQ.dequeue();
            if(s.getID().equalsIgnoreCase(id)){
                orderFound = true;
                System.out.print("\t\tOrder "+id+ " has been deleted!\n");
                System.out.print("\n\t\t" +s.toString());
                break;
            }
            else{
                tempQ.enqueue(s);
            }
        }
        
        while(!tempQ.isEmpty()){
            shoesQ.enqueue(tempQ.dequeue());
        }
        
        if(!orderFound){
            System.out.println("\t\tOrder "+id+ " is not found");
        }
    }
        
    public static void updateQuantityByOrderID(Queue shoesQ){
        System.out.print("\t\tEnter order ID: ");
        String id = in.nextLine();
            
        boolean orderFound = false;
        Queue tempQ = new Queue();  
        
        while(!shoesQ.isEmpty()){
            Shoes s = (Shoes) shoesQ.dequeue();
            if(s.getID().equalsIgnoreCase(id)){
                System.out.print("\t\tEnter new Quantity: ");
                int newQty = in.nextInt();
                in.nextLine();
                
                s.setQty(newQty);
                System.out.println("\n\tUpdated order details: "+s.toString());
                System.out.println("\t\tOrder " +id+ " has been updated!");
                orderFound = true;
            }
            tempQ.enqueue(s);
        }
        
        while(!tempQ.isEmpty()){
            shoesQ.enqueue(tempQ.dequeue());
        }
        
        if(!orderFound){
            System.out.println("\t\tOrder ID" +id+ " not found!");
        }
    }
        
    public static void addOrder(Queue shoesQ){
        System.out.print("\n\tEnter order ID                   :");
        String id = in.nextLine();
            
        System.out.print("\n\tEnter product code               :");
        String prodCode = in.nextLine();
            
        System.out.print("\n\tEnter shoes size                 :");
        double size = in.nextDouble();
        in.nextLine();
            
        System.out.print("\n\tEnter shoe type                  :");
        String type = in.nextLine();
            
        System.out.print("\n\tEnter quantity                   :");
        int qty = in.nextInt();
        in.nextLine();
            
        System.out.print("\n\tEnter customer name              :");
        String name = in.nextLine();
        if (!name.isEmpty()) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
            
        System.out.print("\n\tEnter customer phone number      :");
        String custNum = in.nextLine();
    
        System.out.print("\n\tEnter payment method (CASH/E-WALLET/QR PAY)     :");
        String payMethod = in.nextLine().toUpperCase();
        
        Customer c = new Customer(name, custNum);
        Purchase p = new Purchase(payMethod, c, null);
        
        Queue tempQ = new Queue();
        Shoes s; 
        if(prodCode.substring(0,3).equalsIgnoreCase("SMH")){
            s = new Heels(id, prodCode, size, type, qty, c, p);
        } else if (prodCode.substring(0,3).equalsIgnoreCase("SML")){
            s = new Loafers (id, prodCode, size, type, qty, c, p);
        } else if(prodCode.substring(0,3).equalsIgnoreCase("SMB")){
            s = new Boots (id, prodCode, size, type, qty, c, p);
        }else{
            System.out.println("Invalid product code. Order not added. ");
            return;
        }
            
        shoesQ.enqueue(s);
        System.out.println("\n\t\tOrder added successfully!");
    }
        
    public static void calcTotalPrice(Queue shoesQ){
        boolean search = true;
        while (search){
            System.out.println();
            System.out.println("\n\t═════════════════════════════════════════════════════");
            System.out.println("\t\t1. Search by order ID");
            System.out.println("\t\t2. Search by product code");
            System.out.println("\t\t3. Search by customer name");
            System.out.println("\t\t4. Return to Menu");
                
            System.out.print("\n\t\tEnter your choice (1/2/3/4): ");
            int code = in.nextInt();
            in.nextLine();
                
            boolean orderFound = false;
            Queue tempQ = new Queue();
            if(code == 1){
                System.out.print("\n\t\tEnter order ID: ");
                String id = in.nextLine();
                        
                while(!shoesQ.isEmpty()){
                    Shoes s = (Shoes) shoesQ.dequeue();
                    if(s.getID().equalsIgnoreCase(id)){
                        orderFound = true;
                        Purchase p = s.getPurchase();
                        Customer c = s.getCustomer();
                        System.out.println(c.custInfo());
                        System.out.println(s.toString());
                        System.out.println(s.receipt());
                    }
                    tempQ.enqueue(s);
                }
            }
            else if (code == 2){
                System.out.print("\n\t\tEnter product code: ");
                String prodCode = in.nextLine();
                
                while(!shoesQ.isEmpty()){
                    Shoes s = (Shoes) shoesQ.dequeue();
                    if(s.getCode().equalsIgnoreCase(prodCode)){
                        orderFound = true;
                        Purchase p = s.getPurchase();
                        Customer c = s.getCustomer();
                        System.out.println(c.custInfo());
                        System.out.println(s.toString());
                        System.out.println(s.receipt());
                    }
                    tempQ.enqueue(s);
                }
            }
            else if (code == 3){
                System.out.print("\n\t\tEnter customer name: ");
                String name = in.nextLine();
        
                while(!shoesQ.isEmpty()){
                    Shoes s = (Shoes) shoesQ.dequeue();
                    Customer c = s.getCustomer();
                    if(c.getName().equalsIgnoreCase(name)){
                        orderFound = true;
                        System.out.println(c.custInfo());
                        System.out.println(s.toString());
                        System.out.println(s.receipt());
                    }
                    tempQ.enqueue(s);
                }
            }
            else if(code == 4){
                System.out.println("\nReturning to menu...");
                return;
            }
            else{
                System.out.println("\nInvalid code! Please try again.");
                continue;
            }
            
            while(!tempQ.isEmpty()){
                shoesQ.enqueue(tempQ.dequeue());
            }
            
            if(!orderFound){
                System.out.print("\n\t\tNo matching orders found. Would you like to try again? (Yes/No): ");
                String retry = in.nextLine();
                if(!retry.equalsIgnoreCase("Yes")){
                    search = false;
                }else{
                    continue;
                }
            }
        }
    }
}
