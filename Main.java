package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String conurl = "jdbc:oracle:thin:@10.190.37.249:1521/XE";
            con = DriverManager.getConnection(conurl, "SYSTEM", "Satya@12345");

            int choice;
            do {
                System.out.println("\n\n***** Banking Management System*****");
                System.out.println("1. Show Customer Records");
                System.out.println("2. Add Customer Record");
                System.out.println("3. Delete Customer Record");
                System.out.println("4. Update Customer Record for any attribute except Customer Number");
                System.out.println("5. Show Account Details of a Customer");
                System.out.println("6. Show Loan Details of a Customer");
                System.out.println("7. Deposit Money to an Account");
                System.out.println("8. Withdraw Money from an Account");
                System.out.println("9. Exit the Program");
                System.out.println("Enter your choice(1-9):");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        showCustomerRecords(con);
                        break;
                    case 2:
                        addCustomerRecord(con, scanner);
                        break;
                    case 3:
                        deleteCustomerRecord(con, scanner);
                        break;
                    case 4:
                        updateCustomerRecord(con, scanner);
                        break;
                    case 5:
                        showAccountDetails(con, scanner);
                        break;
                    case 6:
                        showLoanDetails(con, scanner);
                        break;
                    case 7:
                        depositMoney(con, scanner);
                        break;
                    case 8:
                        withdrawMoney(con, scanner);
                        break;
                    case 9:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                        break;
                }
            } while (choice != 9);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
                if (stmt != null)
                    stmt.close();
                if (scanner != null)
                    scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showCustomerRecords(Connection con) {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
            System.out.println("Customer Records:");
            while (rs.next()) {
                System.out.println("Customer Number: " + rs.getString("cust_no") +
                        ", Name: " + rs.getString("name") +
                        ", Phone Number: " + rs.getString("phone_no") +
                        ", City: " + rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCustomerRecord(Connection con, Scanner scanner) {
        try {
            String insertQuery = "INSERT INTO Customers VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertQuery);

            System.out.println("Enter Customer Number:");
            stmt.setString(1, scanner.next());
            System.out.println("Enter Name:");
            stmt.setString(2, scanner.next());
            System.out.println("Enter Phone Number:");
            stmt.setString(3, scanner.next());
            System.out.println("Enter City:");
            stmt.setString(4, scanner.next());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer added successfully.");
            } else {
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCustomerRecord(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number to delete:");
            String custNo = scanner.next();
            String deleteQuery = "DELETE FROM Customers WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(deleteQuery);
            stmt.setString(1, custNo);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("No customer found with the given Customer Number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateCustomerRecord(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number to update:");
            String custNo = scanner.next();
            System.out.println("Enter new name:");
            String newName = scanner.next();
            System.out.println("Enter new phone number:");
            String newPhoneNo = scanner.next();
            System.out.println("Enter new city:");
            String newCity = scanner.next();

            String updateQuery = "UPDATE Customers SET name = ?, phone_no = ?, city = ? WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, newName);
            stmt.setString(2, newPhoneNo);
            stmt.setString(3, newCity);
            stmt.setString(4, custNo);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer information updated successfully!");
            } else {
                System.out.println("No customer record found for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAccountDetails(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number:");
            String custNo = scanner.next();

            String query = "SELECT * FROM Accounts WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, custNo);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Account Details for Customer Number: " + custNo);
            while (rs.next()) {
                System.out.println("Account Number: " + rs.getString("acc_no") +
                        ", Balance: " + rs.getDouble("balance") +
                        ", Type: " + rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showLoanDetails(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number:");
            String custNo = scanner.next();

            String query = "SELECT * FROM Loans WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, custNo);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Loan Details for Customer Number: " + custNo);
            while (rs.next()) {
                System.out.println("Loan Number: " + rs.getString("loan_no") +
                        ", Amount: " + rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void depositMoney(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Account Number:");
            String accNo = scanner.next();
            System.out.println("Enter Amount to Deposit:");
            double amount = scanner.nextDouble();

            String updateQuery = "UPDATE Accounts SET balance = balance + ? WHERE acc_no = ?";
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setDouble(1, amount);
            stmt.setString(2, accNo);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Amount deposited successfully!");
            } else {
                System.out.println("Failed to deposit amount.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void withdrawMoney(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Account Number:");
            String accNo = scanner.next();
            System.out.println("Enter Amount to Withdraw:");
            double amount = scanner.nextDouble();

            String checkBalanceQuery = "SELECT balance FROM Accounts WHERE acc_no = ?";
            PreparedStatement balanceStmt = con.prepareStatement(checkBalanceQuery);
            balanceStmt.setString(1, accNo);
            ResultSet rs = balanceStmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updateQuery = "UPDATE Accounts SET balance = balance - ? WHERE acc_no = ?";
                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                    updateStmt.setDouble(1, amount);
                    updateStmt.setString(2, accNo);

                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Amount withdrawn successfully!");
                    } else {
                        System.out.println("Failed to withdraw amount.");
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String conurl = "jdbc:oracle:thin:@10.190.37.249:1521/XE";
            con = DriverManager.getConnection(conurl, "SYSTEM", "Satya@12345");

            int choice;
            do {
                System.out.println("\n\n***** Banking Management System*****");
                System.out.println("1. Show Customer Records");
                System.out.println("2. Add Customer Record");
                System.out.println("3. Delete Customer Record");
                System.out.println("4. Update Customer Record for any attribute except Customer Number");
                System.out.println("5. Show Account Details of a Customer");
                System.out.println("6. Show Loan Details of a Customer");
                System.out.println("7. Deposit Money to an Account");
                System.out.println("8. Withdraw Money from an Account");
                System.out.println("9. Exit the Program");
                System.out.println("Enter your choice(1-9):");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        showCustomerRecords(con);
                        break;
                    case 2:
                        addCustomerRecord(con, scanner);
                        break;
                    case 3:
                        deleteCustomerRecord(con, scanner);
                        break;
                    case 4:
                        updateCustomerRecord(con, scanner);
                        break;
                    case 5:
                        showAccountDetails(con, scanner);
                        break;
                    case 6:
                        showLoanDetails(con, scanner);
                        break;
                    case 7:
                        depositMoney(con, scanner);
                        break;
                    case 8:
                        withdrawMoney(con, scanner);
                        break;
                    case 9:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                        break;
                }
            } while (choice != 9);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
                if (stmt != null)
                    stmt.close();
                if (scanner != null)
                    scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showCustomerRecords(Connection con) {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
            System.out.println("Customer Records:");
            while (rs.next()) {
                System.out.println("Customer Number: " + rs.getString("cust_no") +
                        ", Name: " + rs.getString("name") +
                        ", Phone Number: " + rs.getString("phone_no") +
                        ", City: " + rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCustomerRecord(Connection con, Scanner scanner) {
        try {
            String insertQuery = "INSERT INTO Customers VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertQuery);

            System.out.println("Enter Customer Number:");
            stmt.setString(1, scanner.next());
            System.out.println("Enter Name:");
            stmt.setString(2, scanner.next());
            System.out.println("Enter Phone Number:");
            stmt.setString(3, scanner.next());
            System.out.println("Enter City:");
            stmt.setString(4, scanner.next());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer added successfully.");
            } else {
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCustomerRecord(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number to delete:");
            String custNo = scanner.next();
            String deleteQuery = "DELETE FROM Customers WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(deleteQuery);
            stmt.setString(1, custNo);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("No customer found with the given Customer Number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateCustomerRecord(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number to update:");
            String custNo = scanner.next();
            System.out.println("Enter new name:");
            String newName = scanner.next();
            System.out.println("Enter new phone number:");
            String newPhoneNo = scanner.next();
            System.out.println("Enter new city:");
            String newCity = scanner.next();

            String updateQuery = "UPDATE Customers SET name = ?, phone_no = ?, city = ? WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, newName);
            stmt.setString(2, newPhoneNo);
            stmt.setString(3, newCity);
            stmt.setString(4, custNo);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer information updated successfully!");
            } else {
                System.out.println("No customer record found for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAccountDetails(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number:");
            String custNo = scanner.next();

            String query = "SELECT * FROM Accounts WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, custNo);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Account Details for Customer Number: " + custNo);
            while (rs.next()) {
                System.out.println("Account Number: " + rs.getString("acc_no") +
                        ", Balance: " + rs.getDouble("balance") +
                        ", Type: " + rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showLoanDetails(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Customer Number:");
            String custNo = scanner.next();

            String query = "SELECT * FROM Loans WHERE cust_no = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, custNo);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Loan Details for Customer Number: " + custNo);
            while (rs.next()) {
                System.out.println("Loan Number: " + rs.getString("loan_no") +
                        ", Amount: " + rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void depositMoney(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Account Number:");
            String accNo = scanner.next();
            System.out.println("Enter Amount to Deposit:");
            double amount = scanner.nextDouble();

            String updateQuery = "UPDATE Accounts SET balance = balance + ? WHERE acc_no = ?";
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setDouble(1, amount);
            stmt.setString(2, accNo);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Amount deposited successfully!");
            } else {
                System.out.println("Failed to deposit amount.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void withdrawMoney(Connection con, Scanner scanner) {
        try {
            System.out.println("Enter Account Number:");
            String accNo = scanner.next();
            System.out.println("Enter Amount to Withdraw:");
            double amount = scanner.nextDouble();

            String checkBalanceQuery = "SELECT balance FROM Accounts WHERE acc_no = ?";
            PreparedStatement balanceStmt = con.prepareStatement(checkBalanceQuery);
            balanceStmt.setString(1, accNo);
            ResultSet rs = balanceStmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updateQuery = "UPDATE Accounts SET balance = balance - ? WHERE acc_no = ?";
                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                    updateStmt.setDouble(1, amount);
                    updateStmt.setString(2, accNo);

                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Amount withdrawn successfully!");
                    } else {
                        System.out.println("Failed to withdraw amount.");
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
