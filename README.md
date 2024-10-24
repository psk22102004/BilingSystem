# **Billing System**

## Dependencies: 

- **[JDK](https://www.oracle.com/pk/java/technologies/downloads/)**
- **MySQL**
- **JDBC** 
- **Netbeans IDE**

---

## How to run:

1. Download and Install the Dependencies
2. Create database localhost to your selected user (eg. root), and create database **BillManagement**.

   ```sql
   CREATE DATABASE BillManagement;
   ```
3. Insert tables to your database from the below database schemas.
4. Add the sample data provided in `sampleData.sql` file. Add prefered users to the users table.
5. Open Netbeans IDE and open this project.
6. Go to `src/main/java/com/mycompany/billingsysswing/daos` and in **each** file, change the below fields

   ```java
   private final String jdbcUsername = "root"; // your DB username
   private final String jdbcPassword = "thinkpad"; // your DB password
   ```
   to your credentials of your mysql server.
7. After completing the above steps, click run.

## Database Schemas:

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role ENUM('admin', 'user') DEFAULT 'user',
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    status ENUM('active', 'inactive', 'suspended') DEFAULT 'active'
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('active', 'inactive') DEFAULT 'active',
    dob DATE,
    loyalty_points INT DEFAULT 0
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    product_description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    SKU VARCHAR(50) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('active', 'inactive', 'discontinued') DEFAULT 'active',
    GST_rate DECIMAL(5, 2) DEFAULT 0,
    stock_quantity INT DEFAULT 0
);

CREATE TABLE bills (
    bill_id INT PRIMARY KEY AUTO_INCREMENT,
    bill_date DATE NOT NULL,
    customer_id INT,
    total_amount DECIMAL(10, 2) NOT NULL,
    gst_amount DECIMAL(10, 2) NOT NULL,
    discount_amount DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('paid', 'pending', 'canceled') DEFAULT 'pending',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE bill_items (
    bill_item_id INT PRIMARY KEY AUTO_INCREMENT,
    bill_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    gst_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```
### 1. Users Table

- **first_name**: `VARCHAR(50) NOT NULL` – To store the user's first name.
- **last_name**: `VARCHAR(50) NOT NULL` – To store the user's last name.
- **created_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` – To track when the user was created.
- **updated_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` – To track when the user information was last updated.
- **last_login**: `TIMESTAMP` – To store the last login time of the user.
- **status**: `ENUM('active', 'inactive', 'suspended') DEFAULT 'active'` – To manage the status of the user account.

### 2. Customers Table

- **created_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` – To track when the customer was added.
- **updated_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` – To track the last update of the customer's information.
- **status**: `ENUM('active', 'inactive') DEFAULT 'active'` – To manage the status of the customer account.
- **dob**: `DATE` – To store the date of birth of the customer, if relevant.
- **loyalty_points**: `INT DEFAULT 0` – To track loyalty points earned by the customer.

### 3. Products Table

- **SKU (Stock Keeping Unit)**: `VARCHAR(50) UNIQUE` – To uniquely identify the product.
- **created_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` – To track when the product was added.
- **updated_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` – To track the last update of the product information.
- **status**: `ENUM('active', 'inactive', 'discontinued') DEFAULT 'active'` – To manage the product's availability.
- **GST_rate**: `DECIMAL(5, 2) DEFAULT 0` – To store the GST rate applicable for the product.
- **stock_quantity**: `INT DEFAULT 0` – To track the available quantity of the product in stock.

### 4. Bills Table

- **gst_amount**: `DECIMAL(10, 2) NOT NULL` – To track the total GST amount applicable to the bill.
- **discount_amount**: `DECIMAL(10, 2) DEFAULT 0` – To apply any discounts to the bill.
- **created_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` – To track when the bill was generated.
- **updated_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` – To track the last update of the bill.
- **status**: `ENUM('paid', 'pending', 'canceled') DEFAULT 'pending'` – To manage the status of the bill.

### 5. Bill Items Table

- **total_price**: `DECIMAL(10, 2) NOT NULL` – To store the total price for each bill item (price * quantity).
- **gst_amount**: `DECIMAL(10, 2) NOT NULL` – To store the GST amount applicable for the individual bill item.
- **created_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` – To track when the bill item was added.
- **updated_at**: `TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` – To track the last update of the bill item

