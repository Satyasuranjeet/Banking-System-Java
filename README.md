
# Customer Management System

## Description
The Customer Management System is a Java-based application that provides an interface for users to manage customer records in an Oracle SQL database. The program supports operations such as displaying, adding, deleting, and updating customer records, as well as managing account and loan details. All interactions are handled through a menu-driven interface, with outputs displayed in a user-friendly format.

## Features
1. **Show Customer Records**: Display all customer details in a formatted manner.
2. **Add Customer Record**: Add a new customer by providing `cust_no`, `name`, `phoneno`, and `city`. Display updated customer records.
3. **Delete Customer Record**: Delete a customer by providing `cust_no`. Display updated customer records.
4. **Update Customer Information**: Update customer information by providing `cust_no` and choosing from:
   - Update name
   - Update phone number
   - Update city
   Display updated customer records.
5. **Show Account Details of a Customer**: Display account details by providing `cust_no`.
6. **Show Loan Details of a Customer**: Display loan details by providing `cust_no`.
7. **Deposit Money to an Account**: Deposit money by providing the account number and amount. Display updated account details.
8. **Withdraw Money from an Account**: Withdraw money by providing the account number and amount. Display updated account details.
9. **Exit the Program**: Exit the application.

## Prerequisites
- Java Development Kit (JDK)
- Oracle SQL Database
- JDBC Driver for Oracle

## Installation and Setup
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/CustomerManagementSystem.git
   cd CustomerManagementSystem
   ```
2. **Set up Oracle SQL Database**: Create the necessary tables for customers, accounts, and loans.
3. **Configure Database Connection**: Update the database connection details in the Java code (e.g., URL, username, password).
4. **Compile and Run the Program**:
   ```bash
   javac CustomerManagementSystem.java
   java CustomerManagementSystem
   ```

## Usage
Run the program to display the menu with various operations. Choose an option and follow the prompts to provide the necessary information. The results will be displayed on the terminal screen.

## Exception Handling
The program includes exception handling to manage errors during database operations and user input.

## Example Output
![Example Output](screenshots/example_output.png)
```plaintext
1. Show Customer Records
2. Add Customer Record
3. Delete Customer Record
4. Update Customer Information
5. Show Account Details of a Customer
6. Show Loan Details of a Customer
7. Deposit Money to an Account
8. Withdraw Money from an Account
9. Exit the Program

Enter your choice: 1
---------------------------------------
Customer Records:
Cust No | Name        | Phone No  | City
---------------------------------------
1       | John Doe    | 1234567890| New York
2       | Jane Smith  | 0987654321| Los Angeles
---------------------------------------
```

## Screenshots
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/1.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/2.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/3.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/4.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/5.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/6.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/7.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/8.png)]
[![Main Menu](https://github.com/Satyasuranjeet/Banking-System-Java/blob/master/UI_Screenshots/9.png)]

## Author
[Satya Suranjeet Jena](https://github.com/Satyasuranjeet)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
