/**
 * Write a description of class SoleMateGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import java.util.LinkedList;
import java.util.StringTokenizer;

public class ShoesInventoryGUI {

    private static LinkedList<Shoes> shoesLL = new LinkedList<>();
    private static JTextArea outputArea;

    public static void main(String[] args) throws IOException {
        loadShoesData();
        createGUI();
    }
    
    private static String formatOrderDetails(Shoes shoes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(shoes.getID()).append("\n");
        sb.append("Product Code: ").append(shoes.getCode()).append("\n");
        sb.append("Size: ").append(shoes.getSize()).append("\n");
        sb.append("Type: ").append(shoes.getType()).append("\n");
        sb.append("Quantity: ").append(shoes.getQty()).append("\n");
        sb.append("Customer Name: ").append(shoes.getCustomer().getName()).append("\n");
        sb.append("Customer Number: ").append(shoes.getCustomer().getCustNum()).append("\n");
        sb.append("Payment Method: ").append(shoes.getPurchase().getPayMethod()).append("\n");
        sb.append("---------------------------------\n");
        return sb.toString();
    }
    // Load data from file
    private static void loadShoesData() throws IOException {
        try {
            FileReader fr = new FileReader("shoesOrders.txt");
            BufferedReader br = new BufferedReader(fr);
            String dataRow = br.readLine();

            while (dataRow != null) {
                StringTokenizer st = new StringTokenizer(dataRow, ";");
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

                shoesLL.addFirst(s);
                dataRow = br.readLine();
            }

            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Shoes Inventory System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
    
        // Title
        JLabel lblTitle = new JLabel("Shoes Inventory System", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(lblTitle, BorderLayout.NORTH);
    
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4, 10, 10));
    
        JButton btnSearchOrder = new JButton("Search Order");
        JButton btnDisplayAll = new JButton("Display All Orders");
        JButton btnAddOrder = new JButton("Add Order");
        JButton btnDeleteOrder = new JButton("Delete Order");
        JButton btnUpdateQty = new JButton("Update Quantity");
        JButton btnCalcPrice = new JButton("Calculate Price");
        JButton btnExit = new JButton("Exit");
    
        buttonPanel.add(btnSearchOrder);
        buttonPanel.add(btnDisplayAll);
        buttonPanel.add(btnAddOrder);
        buttonPanel.add(btnDeleteOrder);
        buttonPanel.add(btnUpdateQty);
        buttonPanel.add(btnCalcPrice);
        buttonPanel.add(btnExit);
    
        frame.add(buttonPanel, BorderLayout.SOUTH);
    
        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        frame.add(scrollPane, BorderLayout.CENTER);
    
        // Button Actions
        btnSearchOrder.addActionListener(e -> searchOrderGUI(shoesLL));
        btnDisplayAll.addActionListener(e -> displayAllOrders(shoesLL));
        btnAddOrder.addActionListener(e -> addOrderGUI(shoesLL));
        btnDeleteOrder.addActionListener(e -> deleteOrderGUI(shoesLL));
        btnUpdateQty.addActionListener(e -> updateQuantityGUI(shoesLL));
        btnCalcPrice.addActionListener(e -> calcTotalPriceGUI(shoesLL));
        btnExit.addActionListener(e -> System.exit(0));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void searchOrderGUI(LinkedList shoesLL) {
        StringBuilder sb = new StringBuilder("List of matching orders:\n");
        String[] searchOptions = {"Order ID", "Product Code", "Customer Name"};
    
        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Select search criteria:",
                "Search Order",
                JOptionPane.QUESTION_MESSAGE,
                null,
                searchOptions,
                searchOptions[0]);
    
        String input = JOptionPane.showInputDialog("Enter " + choice + ":");
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input provided. Returning to the menu.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        boolean orderFound = false;
        Shoes s = (Shoes) shoesLL.getFirst(); // Assuming getFirst() returns the first element.
    
        // Loop through the linked list to find matching orders
        while (s != null) {
            // Check based on the selected search criteria
            if ((choice.equals("Order ID") && s.getID().equalsIgnoreCase(input)) ||
                (choice.equals("Product Code") && s.getCode().equalsIgnoreCase(input)) ||
                (choice.equals("Customer Name") && s.getCustomer().getName().equalsIgnoreCase(input))) {
                orderFound = true;
                Purchase p = s.getPurchase();
                Customer c = s.getCustomer();
                sb.append("Order found:\n");
                sb.append(s.toString()).append("\n");
                sb.append(c.custInfo()).append("\n\n");
            }
            s = (Shoes) shoesLL.getNext(); // Move to the next shoe order
        }
    
        // Show results in output area
        if (orderFound) {
            outputArea.setText(sb.toString());
        } else {
            outputArea.setText("No matching orders found.");
        }
    }

    private static void displayAllOrders(LinkedList shoesLL) {
        StringBuilder sb = new StringBuilder("All Orders:\n");
        Shoes s = (Shoes) shoesLL.getFirst();
        
        // Check if the list is empty
        if (s == null) {
            sb.append("No orders found.\n");
        } else {
            // Loop through the linked list and append order details to StringBuilder
            while (s != null) {
                sb.append(s.toString()).append("\n"); // Append order details
                s = (Shoes) shoesLL.getNext(); // Move to the next order
            }
        }
    
        // Update the outputArea with the collected order details
        outputArea.setText(sb.toString());
    }

    private static void addOrderGUI(LinkedList<Shoes> shoesLL) {
        JFrame frame = new JFrame("Add Order");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 2, 5, 5)); // 2 columns with some padding

        // Create labels and text fields for input
        JLabel lblOrderID = new JLabel("Enter Order ID:");
        JTextField txtOrderID = new JTextField();

        JLabel lblProductCode = new JLabel("Enter Product Code:");
        JTextField txtProductCode = new JTextField();

        JLabel lblSize = new JLabel("Enter Shoe Size:");
        JTextField txtSize = new JTextField();

        JLabel lblType = new JLabel("Enter Shoe Type:");
        JTextField txtType = new JTextField();

        JLabel lblQuantity = new JLabel("Enter Quantity:");
        JTextField txtQuantity = new JTextField();

        JLabel lblCustomerName = new JLabel("Enter Customer Name:");
        JTextField txtCustomerName = new JTextField();

        JLabel lblCustNum = new JLabel("Enter Customer Phone Number:");
        JTextField txtCustNum = new JTextField();

        JLabel lblPayMethod = new JLabel("Enter Payment Method (CASH/E-WALLET/QR PAY):");
        JTextField txtPayMethod = new JTextField();

        JButton btnSubmit = new JButton("Add Order");

        // Add components to the frame
        frame.add(lblOrderID);
        frame.add(txtOrderID);
        frame.add(lblProductCode);
        frame.add(txtProductCode);
        frame.add(lblSize);
        frame.add(txtSize);
        frame.add(lblType);
        frame.add(txtType);
        frame.add(lblQuantity);
        frame.add(txtQuantity);
        frame.add(lblCustomerName);
        frame.add(txtCustomerName);
        frame.add(lblCustNum);
        frame.add(txtCustNum);
        frame.add(lblPayMethod);
        frame.add(txtPayMethod);
        frame.add(btnSubmit);

        // Button action listener
        btnSubmit.addActionListener(e -> {
        // Gather input values
        String id = txtOrderID.getText().trim();
        String prodCode = txtProductCode.getText().trim().toUpperCase();
        double size;
        int qty;

        try {
            size = Double.parseDouble(txtSize.getText().trim());
            qty = Integer.parseInt(txtQuantity.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid size or quantity input.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String type = txtType.getText().trim();
        if (!type.isEmpty()) {
            type = Character.toUpperCase(type.charAt(0)) + type.substring(1).toLowerCase();
        }

        String name = txtCustomerName.getText().trim();
        if (!name.isEmpty()) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        }

        String custNum = txtCustNum.getText().trim();
        String payMethod = txtPayMethod.getText().trim().toUpperCase();

        // Validate product code format
        if (!prodCode.startsWith("SMH") && !prodCode.startsWith("SML") && !prodCode.startsWith("SMB")) {
            JOptionPane.showMessageDialog(frame, "Invalid product code. Order not added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create Customer and Purchase objects
        Customer c = new Customer(name, custNum);
        Purchase p = new Purchase(payMethod, c, null);

        // Create Shoes object based on product code
        Shoes s;
        if (prodCode.startsWith("SMH")) {
            s = new Heels(id, prodCode, size, type, qty, c, p);
        } else if (prodCode.startsWith("SML")) {
            s = new Loafers(id, prodCode, size, type, qty, c, p);
        } else { // SMB
            s = new Boots(id, prodCode, size, type, qty, c, p);
        }

        // Add the shoes object to the linked list
        shoesLL.addFirst(s);

        // Show success message and close the frame
        JOptionPane.showMessageDialog(frame, "Order added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
        });

        // Set frame visibility
        frame.setVisible(true);
    }

    private static void deleteOrderGUI(LinkedList shoesLL) {
        // Create the frame for the delete order dialog
        JFrame frame = new JFrame("Delete Order");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
    
        // Create label and text field for order ID input
        JLabel lblOrderID = new JLabel("Enter Order ID:");
        JTextField txtOrderID = new JTextField(15); // Text field with 15 columns
    
        // Create the delete button
        JButton btnDelete = new JButton("Delete Order");
        
        // Add components to the frame
        frame.add(lblOrderID);
        frame.add(txtOrderID);
        frame.add(btnDelete);
    
        // Action listener for the delete button
        btnDelete.addActionListener(e -> {
            String id = txtOrderID.getText().trim();
            boolean orderFound = false;
            
            Shoes current = (Shoes) shoesLL.getFirst();
            Shoes previous = null;
            
            while (current != null) {
                if (current.getID().equalsIgnoreCase(id)) {
                    if (previous == null) {
                        shoesLL.removeFirst();
                    } else {
                        shoesLL.removeAfter(previous);
                    }
                    
                    // Show success message
                    JOptionPane.showMessageDialog(frame, "Order " + id + " has been deleted!\n" + current.toString(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    orderFound = true;
                    break;
                }
                previous = current;
                current = (Shoes) shoesLL.getNext();
            }
            
            if (!orderFound) {
                JOptionPane.showMessageDialog(frame, "Order " + id + " is not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }

    private static void updateQuantityGUI(LinkedList shoesLL) {
        JFrame frame = new JFrame("Update Quantity");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
    
        JLabel lblOrderID = new JLabel("Enter Order ID:");
        JTextField txtOrderID = new JTextField(15);
        JLabel lblNewQty = new JLabel("Enter New Quantity:");
        JTextField txtNewQty = new JTextField(5);
        JButton btnUpdate = new JButton("Update Quantity");
    
        frame.add(lblOrderID);
        frame.add(txtOrderID);
        frame.add(lblNewQty);
        frame.add(txtNewQty);
        frame.add(btnUpdate);
    
        btnUpdate.addActionListener(e -> {
            String id = txtOrderID.getText().trim();
            String qtyText = txtNewQty.getText().trim();
    
            if (qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "New quantity cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            int newQty;
            try {
                newQty = Integer.parseInt(qtyText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity entered.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            boolean orderFound = false;
            Shoes s = (Shoes) shoesLL.getFirst();
    
            while (s != null) {
                if (s.getID().equalsIgnoreCase(id)) {
                    s.setQty(newQty);
                    JOptionPane.showMessageDialog(frame, "Order " + id + " has been updated!\n" + s.toString(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    orderFound = true;
                    break;
                }
                s = (Shoes) shoesLL.getNext();
            }
    
            if (!orderFound) {
                JOptionPane.showMessageDialog(frame, "Order ID " + id + " not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }

    private static void calcTotalPriceGUI(LinkedList shoesLL) {
        JFrame frame = new JFrame("Calculate Total Price");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        String[] searchOptions = {"Search by Order ID", "Search by Product Code", "Search by Customer Name", "Return to Menu"};
        JComboBox<String> comboSearchOptions = new JComboBox<>(searchOptions);
        JTextField txtSearchInput = new JTextField(15);
        JButton btnSearch = new JButton("Search");

        frame.add(new JLabel("Select Search Method:"));
        frame.add(comboSearchOptions);
        frame.add(new JLabel("Enter Search Term:"));
        frame.add(txtSearchInput);
        frame.add(btnSearch);

        btnSearch.addActionListener(e -> {
            String selectedOption = (String) comboSearchOptions.getSelectedItem();
            String input = txtSearchInput.getText().trim();

            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean orderFound = false;
            Shoes s = (Shoes) shoesLL.getFirst();
            StringBuilder result = new StringBuilder();

            switch (selectedOption) {
                case "Search by Order ID":
                    while (s != null) {
                        if (s.getID().equalsIgnoreCase(input)) {
                            orderFound = true;
                            result.append("Order found:\n")
                                  .append(s.toString()).append("\n")
                                  .append(s.getCustomer().custInfo()).append("\n")
                                  .append(s.receipt()).append("\n\n");
                            break;
                        }
                        s = (Shoes) shoesLL.getNext();
                    }
                    break;

                case "Search by Product Code":
                    while (s != null) {
                        if (s.getCode().equalsIgnoreCase(input)) {
                            orderFound = true;
                            result.append("Order found:\n")
                                  .append(s.toString()).append("\n")
                                  .append(s.getCustomer().custInfo()).append("\n")
                                  .append(s.receipt()).append("\n\n");
                        }
                        s = (Shoes) shoesLL.getNext();
                    }
                    break;

                case "Search by Customer Name":
                    while (s != null) {
                        if (s.getCustomer().getName().equalsIgnoreCase(input)) {
                            orderFound = true;
                            result.append("Order found:\n")
                                  .append(s.toString()).append("\n")
                                  .append(s.getCustomer().custInfo()).append("\n")
                                  .append(s.receipt()).append("\n\n");
                            break;
                        }
                        s = (Shoes) shoesLL.getNext();
                    }
                    break;

                case "Return to Menu":
                    frame.dispose();
                    return;
            }

            if (orderFound) {
                // Display results in the main output area
                outputArea.setText(result.toString());
                frame.dispose();
            } else {
                int retry = JOptionPane.showConfirmDialog(
                    frame, 
                    "No matching orders found. Would you like to try again?", 
                    "No Results", 
                    JOptionPane.YES_NO_OPTION
                );

                if (retry == JOptionPane.YES_OPTION) {
                    // Clear the search input for a new attempt
                    txtSearchInput.setText("");
                } else {
                    // Close the frame and return to the main menu
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }   
}
