use BillManagement;

INSERT INTO customers (name, email, phone, address, status, dob, loyalty_points) VALUES
('John Doe', 'john.doe@example.com', '555-1234', '123 Elm St', 'active', '1990-01-15', 120),
('Jane Smith', 'jane.smith@example.com', '555-5678', '456 Oak St', 'active', '1985-03-22', 80),
('Emily Johnson', 'emily.j@example.com', '555-2345', '789 Pine St', 'inactive', '1992-07-12', 0),
('Michael Brown', 'michael.b@example.com', '555-3456', '101 Maple St', 'active', '1988-11-04', 220),
('Sarah Williams', 'sarah.w@example.com', '555-4567', '202 Cedar St', 'active', '1995-09-09', 300),
('Chris Davis', 'chris.d@example.com', '555-6789', '303 Birch St', 'inactive', '1991-04-18', 50),
('Laura Miller', 'laura.m@example.com', '555-7890', '404 Spruce St', 'active', '1993-08-25', 100),
('David Garcia', 'david.g@example.com', '555-8901', '505 Fir St', 'active', '1996-02-14', 150),
('Sophia Wilson', 'sophia.w@example.com', '555-9012', '606 Redwood St', 'active', '1987-12-30', 200),
('Oliver Martinez', 'oliver.m@example.com', '555-3457', '707 Ash St', 'inactive', '1989-06-21', 70),
('Isabella Anderson', 'isabella.a@example.com', '555-5679', '808 Poplar St', 'active', '1997-10-15', 90),
('James Thomas', 'james.t@example.com', '555-2346', '909 Walnut St', 'active', '1990-01-11', 250),
('Mia Lee', 'mia.l@example.com', '555-7891', '1010 Willow St', 'inactive', '1994-05-19', 0),
('Ethan Jackson', 'ethan.j@example.com', '555-8902', '1111 Cypress St', 'active', '1986-11-25', 320),
('Ava Harris', 'ava.h@example.com', '555-9013', '1212 Dogwood St', 'active', '1992-03-05', 180),
('William Martin', 'william.m@example.com', '555-6780', '1313 Sequoia St', 'inactive', '1991-08-13', 60),
('Emily Clark', 'emily.c@example.com', '555-7892', '1414 Hickory St', 'active', '1998-02-17', 130),
('Liam Lewis', 'liam.l@example.com', '555-8903', '1515 Chestnut St', 'active', '1987-09-10', 170),
('Olivia Walker', 'olivia.w@example.com', '555-9014', '1616 Magnolia St', 'active', '1993-07-29', 140),
('Lucas Hall', 'lucas.h@example.com', '555-3458', '1717 Hawthorn St', 'inactive', '1990-06-07', 0);

INSERT INTO products (product_name, product_description, price, SKU, status, GST_rate, stock_quantity) VALUES
('Laptop', 'High-performance laptop', 1000.00, 'LAP123', 'active', 18.00, 50),
('Smartphone', 'Latest smartphone model', 700.00, 'SMP456', 'active', 18.00, 120),
('Tablet', 'Portable tablet device', 400.00, 'TAB789', 'active', 18.00, 80),
('Headphones', 'Noise-cancelling headphones', 150.00, 'HDN101', 'active', 18.00, 200),
('Smartwatch', 'Fitness tracking smartwatch', 200.00, 'SMW112', 'active', 18.00, 150),
('Monitor', '4K Ultra HD monitor', 300.00, 'MNT113', 'inactive', 18.00, 100),
('Keyboard', 'Mechanical keyboard', 80.00, 'KBD114', 'active', 18.00, 300),
('Mouse', 'Wireless ergonomic mouse', 40.00, 'MSE115', 'active', 18.00, 350),
('Printer', 'All-in-one printer', 250.00, 'PRN116', 'active', 18.00, 70),
('Router', 'Wi-Fi 6 router', 120.00, 'RTR117', 'active', 18.00, 90),
('External HDD', '1TB external hard drive', 80.00, 'HDD118', 'discontinued', 18.00, 60),
('Graphics Card', 'High-end graphics card', 600.00, 'GFX119', 'active', 18.00, 40),
('Desk Lamp', 'LED desk lamp', 30.00, 'LMP120', 'active', 12.00, 400),
('USB Cable', 'USB-C charging cable', 10.00, 'USB121', 'active', 5.00, 500),
('Power Bank', '10,000mAh power bank', 50.00, 'PWB122', 'active', 18.00, 250),
('Speaker', 'Bluetooth speaker', 80.00, 'SPK123', 'active', 18.00, 150),
('Webcam', '1080p HD webcam', 70.00, 'WBC124', 'active', 18.00, 100),
('Charger', 'Fast-charging USB-C charger', 25.00, 'CHG125', 'active', 18.00, 200),
('Gaming Chair', 'Ergonomic gaming chair', 300.00, 'CHR126', 'inactive', 18.00, 50),
('Microphone', 'Podcast-quality microphone', 100.00, 'MIC127', 'active', 18.00, 120);

INSERT INTO bills (bill_date, customer_id, total_amount, gst_amount, discount_amount, status) VALUES
('2024-01-01', 1, 1200.00, 216.00, 100.00, 'paid'),
('2024-01-02', 2, 800.00, 144.00, 50.00, 'paid'),
('2024-01-03', 3, 400.00, 72.00, 0.00, 'pending'),
('2024-01-04', 4, 150.00, 27.00, 10.00, 'paid'),
('2024-01-05', 5, 200.00, 36.00, 20.00, 'paid'),
('2024-01-06', 6, 300.00, 54.00, 0.00, 'paid'),
('2024-01-07', 7, 80.00, 14.40, 0.00, 'pending'),
('2024-01-08', 8, 40.00, 7.20, 5.00, 'canceled'),
('2024-01-09', 9, 250.00, 45.00, 20.00, 'paid'),
('2024-01-10', 10, 120.00, 21.60, 0.00, 'pending'),
('2024-01-11', 11, 600.00, 108.00, 50.00, 'paid'),
('2024-01-12', 12, 300.00, 54.00, 10.00, 'pending'),
('2024-01-13', 13, 30.00, 5.40, 0.00, 'paid'),
('2024-01-14', 14, 10.00, 0.60, 0.00, 'paid'),
('2024-01-15', 15, 50.00, 9.00, 0.00, 'paid'),
('2024-01-16', 16, 80.00, 14.40, 0.00, 'pending'),
('2024-01-17', 17, 100.00, 18.00, 10.00, 'paid'),
('2024-01-18', 18, 300.00, 54.00, 20.00, 'pending'),
('2024-01-19', 19, 70.00, 12.60, 5.00, 'paid'),
('2024-01-20', 20, 120.00, 21.60, 10.00, 'pending');

INSERT INTO bill_items (bill_id, product_id, quantity, price, total_price, gst_amount) VALUES
(1, 1, 1, 1000.00, 1000.00, 180.00),
(1, 4, 2, 150.00, 300.00, 54.00),
(2, 2, 1, 700.00, 700.00, 126.00),
(2, 8, 1, 40.00, 40.00, 7.20),
(3, 3, 1, 400.00, 400.00, 72.00),
(4, 5, 1, 200.00, 200.00, 36.00),
(5, 7, 3, 80.00, 240.00, 43.20),
(6, 6, 2, 300.00, 600.00, 108.00),
(7, 10, 1, 120.00, 120.00, 21.60),
(8, 11, 2, 50.00, 100.00, 18.00),
(9, 12, 4, 30.00, 120.00, 21.60),
(10, 9, 1, 250.00, 250.00, 45.00),
(11, 14, 5, 10.00, 50.00, 9.00),
(12, 13, 1, 600.00, 600.00, 108.00),
(13, 15, 3, 50.00, 150.00, 27.00),
(14, 17, 1, 100.00, 100.00, 18.00),
(15, 16, 2, 300.00, 600.00, 108.00),
(16, 18, 3, 80.00, 240.00, 43.20),
(17, 19, 1, 70.00, 70.00, 12.60),
(18, 20, 1, 120.00, 120.00, 21.60);

-- Insert Admin Users
INSERT INTO users (username, password, email, role, first_name, last_name) 
VALUES 
('Adi', 'abcd', 'adi@example.com', 'admin', 'Adi', 'Lastname1'),
('Parth', 'abcd', 'parth@example.com', 'admin', 'Parth', 'Lastname2'),
('Sharvari', 'abcd', 'sharvari@example.com', 'admin', 'Sharvari', 'Lastname3');

-- Insert Regular Users
INSERT INTO users (username, password, email, role, first_name, last_name) 
VALUES 
('Lavesh', 'abcd', 'lavesh@example.com', 'user', 'Lavesh', 'Lastname4'),
('Varun', 'abcd', 'varun@example.com', 'user', 'Varun', 'Lastname5'),
('Shweta', 'abcd', 'shweta@example.com', 'user', 'Shweta', 'Lastname6');
