-- Add to your database 

use BillManagement;
DELIMITER //

CREATE TRIGGER update_stock_after_bill_insert
AFTER INSERT ON bill_items
FOR EACH ROW
BEGIN
    UPDATE products
    SET stock_quantity = stock_quantity - NEW.quantity
    WHERE product_id = NEW.product_id;
END //

DELIMITER ;
DELIMITER //

CREATE TRIGGER revert_stock_after_bill_cancel
AFTER UPDATE ON bills
FOR EACH ROW
BEGIN
    IF NEW.status = 'canceled' THEN
        UPDATE products p
        JOIN bill_items bi ON p.product_id = bi.product_id
        SET p.stock_quantity = p.stock_quantity + bi.quantity
        WHERE bi.bill_id = NEW.bill_id;
    END IF;
END //

DELIMITER ;
