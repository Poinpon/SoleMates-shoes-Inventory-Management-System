
/**
 * Write a description of class ShoesLL here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
public class ShoesLL
{
    private static Scanner in = new Scanner (System.in);
    private static Scanner inLine = new Scanner (System.in);
    public static void main (String args[]) throws IOException{
        try{
            LinkedList shoesLL = new LinkedList();
            
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
                Purchase p = new Purchase(payMethod, c,  null);
                
                Shoes s;
                if (prodCode.substring(0, 3).equalsIgnoreCase("SMH")) {
                    s = new Heels(id, prodCode, size, type,qty, c, p);
                } else if (prodCode.substring(0, 3).equalsIgnoreCase("SML")) {
                    s = new Loafers(id, prodCode, size, type,qty, c, p);
                } else if (prodCode.substring(0, 3).equalsIgnoreCase("SMB")) {
                    s = new Boots(id, prodCode, size, type,qty, c, p);
                } else {
                    System.err.println("Unknown product code: " + prodCode);
                    dataRow = br.readLine();
                    continue;
                }
                
                shoesLL.addFirst(s);
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
                    searchOrder(shoesLL);
                }
                else if (choice == 2){
                    displayAllOrder(shoesLL);
                }
                else if (choice == 3){
                    deleteOrderByOrderID(shoesLL);
                }
                else if (choice == 4){
                    updateQuantityByOrderID(shoesLL);
                }
                else if (choice == 5){
                    addOrder(shoesLL);
                }
                else if (choice == 6){
                    calcTotalPrice(shoesLL);
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
        
    public static void searchOrder(LinkedList shoesLL){
        boolean search = true;
        while (search){
            System.out.println();
            System.out.println("\n\t═════════════════════════════════════════════════════");
            System.out.println("\t\t1. Search by order ID");
            System.out.println("\t\t2. Search by product code");
            System.out.println("\t\t3. Search by customer name");
            System.out.println("\t\t4. Return to Menu");
                
            System.out.print("\n\n\t\tEnter your choice (1/2/3/4): ");
            int code = in.nextInt();
            in.nextLine();
                
            boolean orderFound = false;
            
            Shoes s = (Shoes) shoesLL.getFirst();
            
            if(code == 1){
                System.out.print("\n\t\tEnter order ID: ");
                String id = in.nextLine().trim();
                    
                while(s != null){
                    if(s.getID().equalsIgnoreCase(id)){
                        Customer c = s.getCustomer();
                        System.out.println("\n\n\n\tOrder found: " + s.toString());
                        System.out.println(c.custInfo());
                        orderFound = true;
                    }
                    s = (Shoes) shoesLL.getNext();
                }
            }
            else if (code == 2){
                System.out.print("\n\t\tEnter product code: ");
                String prodCode = in.nextLine().trim();
                    
                while(s != null){
                    if(s.getCode().equalsIgnoreCase(prodCode)){
                        Customer c = s.getCustomer();
                        System.out.println("\n\n\n\tOrder found: "+ s.toString());
                        System.out.println(c.custInfo());
                        orderFound = true;
                    }
                    s = (Shoes) shoesLL.getNext();
                }
            }
            else if (code == 3){
                System.out.print("\n\t\tEnter customer name: ");
                String name = in.nextLine().trim();
                    
                while(s != null){
                    if(s.getCustomer().getName().equalsIgnoreCase(name)){
                        Customer c = s.getCustomer();
                        System.out.println("\n\n\n\tOrder found: " + s);
                        System.out.println(c.custInfo());
                        orderFound = true;
                    }
                    s = (Shoes) shoesLL.getNext();
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
        
    public static void displayAllOrder(LinkedList shoesLL){
        System.out.println("\n\tList of all orders:");
        Shoes s = (Shoes) shoesLL.getFirst();
            
        while(s != null){
            System.out.println(s.toString());
            s = (Shoes) shoesLL.getNext();
        }
    }
        
    public static void deleteOrderByOrderID(LinkedList shoesLL){
        System.out.print("\n\t\tEnter order ID: ");
        String id = in.nextLine();
        boolean orderFound = false;
        
        Shoes current = (Shoes) shoesLL.getFirst();
        Shoes previous = null;
        
        while(current != null){
            if(current.getID().equalsIgnoreCase(id)){
                if(previous == null){
                    shoesLL.removeFirst();
                }
                else{
                    shoesLL.removeAfter(previous);
                }
                System.out.println("\n\t\tOrder "+id+ " has been deleted!\n");
                System.out.println("\n\t\t" +current.toString());
                orderFound = true;
                break;
            }
            previous = current;
            current = (Shoes) shoesLL.getNext();
        }
        
        if(!orderFound){
            System.out.println("\n\t\tOrder "+id+ " is not found");
        }
    }
        
    public static void updateQuantityByOrderID(LinkedList shoesLL){
        System.out.print("\t\tEnter order ID: ");
        String id = in.nextLine();
            
        boolean orderFound = false;
            
        Shoes s = (Shoes) shoesLL.getFirst();
            
        while(s != null){
            if(s.getID().equalsIgnoreCase(id)){
                System.out.print("\n\t\tEnter new Quantity: ");
                int newQty = in.nextInt();
                Purchase p = s.getPurchase();
                s.setQty(newQty);
                System.out.println(s.toString());
                System.out.println("\n\t\tOrder " +id+ " has been updated!");
                orderFound = true;
                break;
            }
            s = (Shoes) shoesLL.getNext();
        }
            
        if(!orderFound){
            System.out.println("\t\tOrder ID" +id+ " not found!");
        }
    }
        
    public static void addOrder(LinkedList shoesLL){
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
        
        Shoes s;             
        if(prodCode.substring(0,3).equalsIgnoreCase("SMH")){
            s = new Heels(id, prodCode, size, type, qty, c, p);
        } else if (prodCode.substring(0,3).equalsIgnoreCase("SML")){
            s = new Loafers (id, prodCode, size, type,qty, c, p);
        } else if(prodCode.substring(0,3).equalsIgnoreCase("SMB")){
            s = new Boots (id, prodCode, size, type, qty, c, p);
        }else{
            System.out.println("Invalid product code. Order not added. ");
            return;
        }
            
        shoesLL.addFirst(s);
        System.out.println("\n\t\tOrder added successfully!");
    }
        
    public static void calcTotalPrice(LinkedList shoesLL){
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
                
            Shoes s = (Shoes) shoesLL.getFirst();
            if(code == 1){
                System.out.print("\n\t\tEnter order ID: ");
                String id = in.nextLine();
                            
                while(s!=null){
                    if(s.getID().equalsIgnoreCase(id)){
                        orderFound = true;
                        System.out.println("\n\n\tOrder found: ");
                        Purchase p = s.getPurchase();
                        Customer c = s.getCustomer();
                        System.out.println(c.custInfo());
                        System.out.println(s.toString());
                        System.out.println(s.receipt());
                    }
                    s = (Shoes) shoesLL.getNext();
                }
            }
            else if (code == 2){
                System.out.print("\n\t\tEnter product code: ");
                String prodCode = in.nextLine();
                while(s != null){
                    
                    if(s.getCode().equalsIgnoreCase(prodCode)){
                        orderFound = true;
                        System.out.println("\n\n\tOrder found: ");
                        Purchase p = s.getPurchase();
                        Customer c = s.getCustomer();
                        System.out.println(c.custInfo());
                        System.out.println(s.toString());
                        System.out.println(s.receipt());
                    }
                    s = (Shoes) shoesLL.getNext();
                }
            }
                else if (code == 3){
                System.out.print("\n\t\tEnter customer name: ");
                String name = in.nextLine();
                      
                while(s != null){
                    if(s.getCustomer().getName().equalsIgnoreCase(name)){
                        orderFound = true;
                        System.out.println("\n\n\tOrder found: ");
                        Customer c = s.getCustomer();
                        Purchase p = s.getPurchase();
                        System.out.println(c.custInfo());
                        System.out.println(s.toString());
                        System.out.println(s.receipt());
                    }
                    s = (Shoes) shoesLL.getNext();
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
            
            
            if(!orderFound){
                System.out.println("\n\t\tNo matching orders found. Would you like to try again? (Yes/No): ");
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

