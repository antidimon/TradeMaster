CREATE TRIGGER after_stock_insert
AFTER INSERT ON stocks
FOR EACH ROW
EXECUTE FUNCTION handle_stock_price_update();

CREATE TRIGGER before_insert_stock
BEFORE INSERT ON stocks
FOR EACH ROW
EXECUTE FUNCTION set_getted_at();

CREATE TRIGGER set_created_at_trigger
BEFORE INSERT ON operations
FOR EACH ROW
EXECUTE FUNCTION set_created_at();

CREATE TRIGGER stock_operation_trigger
AFTER UPDATE ON operations
FOR EACH ROW
EXECUTE FUNCTION handle_stock_operation();

CREATE TRIGGER validate_operation_trigger
BEFORE INSERT ON operations
FOR EACH ROW
EXECUTE FUNCTION validate_operation();
