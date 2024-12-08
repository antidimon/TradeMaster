CREATE VIEW modifiable_users_limited AS
SELECT id, fio, age FROM users;



CREATE VIEW operation_details AS
SELECT
    o.id AS operation_id,
    u.fio AS fio,
    u.passport_details AS passport_details,
    u.phone_number AS phone_number,
    b.briefcase_name AS briefcase_name,
    os.status AS operation_status,
    oname.name AS operation_name,
    s.name AS stock_name,
    o.stocks_amount AS stocks_amount,
    o.stock_price AS stock_price,
    o.stocks_per_lot AS stocks_per_lot,
    o.created_at AS operation_time,
    (o.stocks_amount * o.stock_price) AS total_amount
FROM
    operations o
JOIN
    users u ON o.user_id = u.id
JOIN
    briefcases b ON o.briefcase_id = b.id
JOIN
    operation_statuses os ON o.status_id = os.id
JOIN
    operation_names oname ON o.operation_name_id = oname.id
JOIN
    stocks s ON o.stock_id = s.id;



CREATE VIEW v_user_briefcase_summary AS
SELECT
    u.id AS user_id,
    u.fio,
    COUNT(b.id) AS briefcase_count,
    SUM(b.balance_now) AS total_balance_now,
    SUM(b.deposited_balance) AS total_deposited
FROM
    users u
LEFT JOIN
    briefcases b ON u.id = b.user_id
GROUP BY
    u.id, u.fio
ORDER BY
    u.id;



CREATE VIEW v_stock_operations_summary AS
SELECT
    s.id AS stock_id,
    s.name AS stock_name,
    COUNT(o.id) AS total_operations,
    SUM(o.stocks_amount) AS total_stocks_traded,
    SUM(o.stock_price * o.stocks_amount) AS total_value_traded
FROM
    stocks s
LEFT JOIN
    operations o ON s.id = o.stock_id
GROUP BY
    s.id, s.name;



CREATE VIEW non_modifiable_briefcase_stock_value AS
SELECT
    b.briefcase_name,
    SUM(bs.stocks_amount * bs.stock_actual_price) AS total_stock_value
FROM
    briefcases b
JOIN
    briefcase_stocks bs ON b.id = bs.briefcase_id
GROUP BY
    b.id
ORDER BY
    b.id;



