CREATE OR REPLACE FUNCTION handle_stock_price_update()
RETURNS TRIGGER AS $$
DECLARE
    briefcase_record RECORD;
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM briefcase_stocks bs
        INNER JOIN stocks s
        ON bs.stock_id = s.id
        WHERE s.name = NEW.name
    ) THEN
        RETURN NEW;
    END IF;

    FOR briefcase_record IN
        SELECT bs.*
        FROM briefcase_stocks bs
        INNER JOIN stocks s
        ON bs.stock_id = s.id
        WHERE s.name = NEW.name
    LOOP
        IF NEW.price < briefcase_record.stop_loss_price THEN
            CALL delete_bs_stock_and_update(briefcase_record.briefcase_id, briefcase_record.id,
            briefcase_record.stocks_amount*briefcase_record.stop_loss_price,
            briefcase_record.stocks_amount*(briefcase_record.stop_loss_price - briefcase_record.stock_actual_price));

        ELSIF NEW.price > briefcase_record.take_profit_price AND briefcase_record.take_profit_price <> 0 THEN
            CALL delete_bs_stock_and_update(briefcase_record.briefcase_id, briefcase_record.id,
            briefcase_record.stocks_amount*briefcase_record.take_profit_price,
            briefcase_record.stocks_amount*(briefcase_record.take_profit_price - briefcase_record.stock_actual_price));

        ELSE
            CALL update_briefcase_stocks(briefcase_record.briefcase_id, briefcase_record.id, briefcase_record.stock_id, NEW.price, NEW.stocks_per_lot);
        END IF;
    END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;






CREATE OR REPLACE FUNCTION set_getted_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.getted_at := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION set_created_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.created_at := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION get_actual_stock(
    p_name VARCHAR
)
RETURNS stocks AS $$
DECLARE
    result stocks%ROWTYPE;
BEGIN
    SELECT * INTO result
    FROM stocks
    WHERE name = p_name
    ORDER BY getted_at DESC
    LIMIT 1;

    RETURN result;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION validate_operation()
RETURNS TRIGGER AS $$
DECLARE
    briefcase_balance NUMERIC(15,5);
    stock_amount INT;
    p_stocks_per_lot INT;
    stock_name VARCHAR;
BEGIN

    SELECT free_balance INTO briefcase_balance
    FROM briefcases
    WHERE id = NEW.briefcase_id;

    SELECT stocks_per_lot INTO p_stocks_per_lot
    FROM stocks
    WHERE id = NEW.stock_id;

    IF NEW.operation_name_id = 1 THEN
        IF briefcase_balance < NEW.stocks_amount * NEW.stock_price * p_stocks_per_lot THEN
            NEW.status_id := 4;
        END IF;
    ELSIF NEW.operation_name_id = 2 THEN

        SELECT name INTO stock_name
        FROM stocks WHERE id = NEW.stock_id;

        SELECT SUM(bs.stocks_amount) INTO stock_amount
        FROM briefcase_stocks bs
        INNER JOIN stocks s
        ON bs.stock_id = s.id
        WHERE bs.briefcase_id = NEW.briefcase_id AND s.name = stock_name;

        IF stock_amount IS NULL OR stock_amount < NEW.stocks_amount THEN
            NEW.status_id := 4;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION handle_stock_operation()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status_id = 3 AND OLD.status_id <> 3 THEN
        IF NEW.operation_name_id = 1 THEN
            PERFORM handle_buy_operation(NEW);
        ELSIF NEW.operation_name_id = 2 THEN
            PERFORM handle_sell_operation(NEW);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION get_briefcase_balance(p_briefcase_id BIGINT)
RETURNS NUMERIC(15, 5) AS $$
DECLARE
    briefcase_balance NUMERIC(15, 5);
BEGIN
    SELECT free_balance INTO briefcase_balance
    FROM briefcases
    WHERE id = p_briefcase_id;
    RETURN briefcase_balance;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION get_stocks_per_lot(p_stock_id BIGINT)
RETURNS INT AS $$
DECLARE
    p_stocks_per_lot INT;
BEGIN
    SELECT stocks_per_lot INTO p_stocks_per_lot
    FROM stocks
    WHERE id = p_stock_id;
    RETURN p_stocks_per_lot;
END;
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION handle_buy_operation(p_new RECORD)
RETURNS VOID AS $$
DECLARE
    briefcase_balance NUMERIC(15, 5);
    p_stocks_per_lot INT;
BEGIN
    briefcase_balance := get_briefcase_balance(p_new.briefcase_id);
    p_stocks_per_lot := get_stocks_per_lot(p_new.stock_id);

    IF briefcase_balance < p_new.stocks_amount * p_new.stock_price * p_stocks_per_lot THEN
        UPDATE operations
        SET status_id = 4
        WHERE id = p_new.id;
        RETURN;
    END IF;

    CALL update_briefcase_free_balance_with_value(p_new.briefcase_id, -1 * p_new.stocks_amount * p_new.stock_price * p_stocks_per_lot);

    IF EXISTS (SELECT 1
               FROM briefcase_stocks
               WHERE briefcase_id = p_new.briefcase_id AND stock_id = p_new.stock_id) THEN
        UPDATE briefcase_stocks
        SET stocks_amount = stocks_amount + p_new.stocks_amount, stock_actual_price = p_new.stock_price
        WHERE briefcase_id = p_new.briefcase_id AND stock_id = p_new.stock_id;
    ELSE
        INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, is_marginal)
        VALUES (p_new.briefcase_id, p_new.stock_id, p_new.stocks_amount, p_new.stock_price, false);
    END IF;
END;
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION handle_sell_operation(p_new RECORD)
RETURNS VOID AS $$
DECLARE
    current_stocks_amount INT;
    other_stocks_amount INT;
    ostatok INT;
    stock_name VARCHAR;
    record RECORD;
    p_stocks_per_lot INT;
BEGIN
    p_stocks_per_lot := get_stocks_per_lot(p_new.stock_id);

    SELECT name INTO stock_name
    FROM stocks WHERE id = p_new.stock_id;

    SELECT stocks_amount INTO current_stocks_amount
    FROM briefcase_stocks
    WHERE briefcase_id = p_new.briefcase_id AND stock_id = p_new.stock_id;

    SELECT SUM(bs.stocks_amount) INTO other_stocks_amount
    FROM briefcase_stocks bs
    INNER JOIN stocks s
    ON bs.stock_id = s.id
    WHERE bs.briefcase_id = p_new.briefcase_id AND s.name = stock_name AND bs.stock_id <> p_new.stock_id;

    IF (other_stocks_amount + current_stocks_amount) < p_new.stocks_amount THEN
        UPDATE operations
        SET status_id = 4
        WHERE id = p_new.id;
        RETURN;
    END IF;

    CALL update_briefcase_free_balance_with_value(p_new.briefcase_id, p_new.stocks_amount * p_new.stock_price * p_stocks_per_lot);

    IF current_stocks_amount < p_new.stocks_amount THEN
        DELETE FROM briefcase_stocks
        WHERE briefcase_id = p_new.briefcase_id AND stock_id = p_new.stock_id;

        ostatok := p_new.stocks_amount - current_stocks_amount;

        FOR record IN SELECT bs.*
        FROM briefcase_stocks bs
        INNER JOIN stocks s
        ON bs.stock_id = s.id
        WHERE bs.briefcase_id = p_new.briefcase_id AND bs.stock_id <> p_new.stock_id AND s.name = stock_name
        ORDER BY id LOOP
            IF ostatok <= 0 THEN
                EXIT;
            END IF;
            IF record.stocks_amount > ostatok THEN
                UPDATE briefcase_stocks
                SET stocks_amount = stocks_amount - ostatok
                WHERE id = record.id;
                ostatok := 0;
            ELSE
                ostatok := ostatok - record.stocks_amount;

                DELETE FROM briefcase_stocks
                WHERE id = record.id;
            END IF;
        END LOOP;

    ELSIF current_stocks_amount = p_new.stocks_amount THEN
        DELETE FROM briefcase_stocks
        WHERE briefcase_id = p_new.briefcase_id AND stock_id = p_new.stock_id;
    ELSE
        UPDATE briefcase_stocks
        SET stocks_amount = stocks_amount - p_new.stocks_amount
        WHERE briefcase_id = p_new.briefcase_id AND stock_id = p_new.stock_id;
    END IF;
END;
$$ LANGUAGE plpgsql;







