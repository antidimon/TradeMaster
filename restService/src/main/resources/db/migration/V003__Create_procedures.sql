CREATE OR REPLACE PROCEDURE update_briefcase_stocks(
    p_briefcase_id BIGINT,
    p_briefcase_stock_id BIGINT,
    p_old_stock_id BIGINT,
    new_price NUMERIC,
    new_stocks_per_lot INT
)
LANGUAGE plpgsql AS $$
DECLARE
    old_revenue NUMERIC;
    new_revenue NUMERIC;
BEGIN

    SELECT revenue INTO old_revenue
    FROM briefcase_stocks
    WHERE id = p_briefcase_stock_id;

    UPDATE briefcase_stocks
    SET
        stock_actual_price = new_price,
        revenue = (stocks_amount * (new_price * new_stocks_per_lot - (SELECT (price * stocks_per_lot) FROM stocks WHERE id = p_old_stock_id)))
    WHERE
        id = p_briefcase_stock_id;

    SELECT revenue INTO new_revenue
    FROM briefcase_stocks
    WHERE id = p_briefcase_stock_id;

    CALL update_briefcase_balance_with_value(p_briefcase_id, new_revenue-old_revenue);
    CALL update_briefcase_total_revenue_with_value(p_briefcase_id, new_revenue-old_revenue);

END;
$$;



CREATE OR REPLACE PROCEDURE update_briefcase_balance_with_value(
    p_briefcase_id BIGINT,
    p_value NUMERIC
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE briefcases
    SET balance_now = balance_now + p_value
    WHERE id = p_briefcase_id;
END;
$$;



CREATE OR REPLACE PROCEDURE update_briefcase_total_revenue_with_value(
    p_briefcase_id BIGINT,
    p_value NUMERIC
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE briefcases
    SET total_revenue = total_revenue + p_value
    WHERE id = p_briefcase_id;
END;
$$;




CREATE OR REPLACE PROCEDURE delete_bs_stock_and_update(
    p_briefcase_id BIGINT,
    p_briefcase_stock_id BIGINT,
    p_value NUMERIC,
    p_revenue NUMERIC
)
LANGUAGE plpgsql AS $$
BEGIN
    CALL update_briefcase_balance_with_value(p_briefcase_id, p_revenue);
    CALL update_briefcase_free_balance_with_value(p_briefcase_id, p_value);
    CALL update_briefcase_total_revenue_with_value(p_briefcase_id, p_revenue);
    DELETE FROM briefcase_stocks
    WHERE id = p_briefcase_stock_id;
END;
$$;



CREATE OR REPLACE PROCEDURE update_briefcase_free_balance_with_value(
    p_briefcase_id BIGINT,
    p_value NUMERIC
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE briefcases
    SET free_balance = free_balance + p_value
    WHERE id = p_briefcase_id;
END;
$$;