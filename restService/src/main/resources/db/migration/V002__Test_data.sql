INSERT INTO users (fio, age, passport_details, phone_number, email, is_qualified) VALUES ('Иванов Иван Иванович', 24, '123123123 pass', '89999999999', 'ivan@mail.ru', False);
INSERT INTO users (fio, age, passport_details, phone_number, email, is_qualified) VALUES ('Петров Пётр Калыванович', 41, '456456456 pass', '89315342451', 'petya@mail.ru', True);
INSERT INTO users (fio, age, passport_details, phone_number, email, is_qualified) VALUES ('Анисин Владимир Владимирович', 20, '789789789 pass', '89531920111', 'vova@mail.ru', False);

INSERT INTO briefcases (user_id, briefcase_name, deposited_balance, balance_now, free_balance, total_revenue) VALUES (1, 'первый', 23560, 27997, 12883, 4437);
INSERT INTO briefcases (user_id, briefcase_name, deposited_balance, balance_now, free_balance, total_revenue) VALUES (1, 'второй', 74000, 74700, 4000, 700);
INSERT INTO briefcases (user_id, briefcase_name, deposited_balance, balance_now, free_balance, total_revenue) VALUES (2, 'main', 789431, 699432, 369917, -89999);
INSERT INTO briefcases (user_id, briefcase_name, deposited_balance, balance_now, free_balance, total_revenue) VALUES (3, 'вторичка', 241567, 374254.5, 230134.5, 132687.5);

INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 97, NOW() - INTERVAL '5 minute', 100, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 93, NOW() - INTERVAL '4 minute', 94, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 95, NOW() - INTERVAL '3 minute', 98, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 96, NOW() - INTERVAL '2 minute', 101, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 99, NOW() - INTERVAL '1 minute', 105, 1);

INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('B', 1721, NOW() - INTERVAL '3 minute', 1850, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('B', 1732, NOW() - INTERVAL '2 minute', 1870, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('B', 1757, NOW() - INTERVAL '1 minute', 1945, 1);

INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('C', 0.00131, NOW() - INTERVAL '3 minute', 0.00132, 1000000);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('C', 0.00128, NOW() - INTERVAL '2 minute', 0.00130, 1000000);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('C', 0.00129, NOW() - INTERVAL '1 minute', 0.00132, 1000000);

INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('D', 541.54, NOW() - INTERVAL '3 minute', 534.6, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('D', 536.87, NOW() - INTERVAL '2 minute', 521.8, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('D', 517.24, NOW() - INTERVAL '1 minute', 511.3, 1);

INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 100, NOW(), 110, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('B', 1763, NOW(), 1987, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('D', 503.24, NOW(), 500.91, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('A', 101, NOW() + INTERVAL '1 minute', 112, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('C', 0.00132, NOW(), 0.00134, 1000000);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AR', 10000, NOW(), 112, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AC', 1000, NOW(), 112, 1);

INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AR', 9820, NOW() - INTERVAL '5 minute', 9990, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AR', 9910, NOW() - INTERVAL '4 minute', 10000, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AR', 9960, NOW() - INTERVAL '3 minute', 10000, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AR', 10090, NOW() - INTERVAL '2 minute', 10100, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AR', 10030, NOW() - INTERVAL '1 minute', 10004, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AC', 1040, NOW() - INTERVAL '5 minute', 1040, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AC', 1050, NOW() - INTERVAL '4 minute', 1040, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AC', 1030, NOW() - INTERVAL '3 minute', 1030, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AC', 1015, NOW() - INTERVAL '2 minute', 1030, 1);
INSERT INTO stocks (name, price, getted_at, predicted_price, stocks_per_lot) VALUES ('AC', 1020, NOW() - INTERVAL '1 minute', 1020, 1);

INSERT INTO operation_names (name) VALUES ('PURCHASE');
INSERT INTO operation_names (name) VALUES ('SALE');

INSERT INTO operation_statuses (status) VALUES ('CREATED');
INSERT INTO operation_statuses (status) VALUES ('WAITING_FOR_CAPTURE');
INSERT INTO operation_statuses (status) VALUES ('SUCCEEDED');
INSERT INTO operation_statuses (status) VALUES ('CANCELED');

INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (1, 15, 10, 101, 10, False, 95, 10, 135, 10);
INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (1, 16, 8, 1763, 0, False, 1700, 8, 0, 0);
INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (2, 15, 700, 101, 700, False, 0, 0, 0, 0);
INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (3, 17, 350, 503.24, 0, False, 495, 250, 600, 350);
INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (3, 16, 87, 1763, 0, False, 0, 0, 0, 0);
INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (4, 18, 120, 101, 0, False, 0, 0, 130, 120);
INSERT INTO briefcase_stocks (briefcase_id, stock_id, stocks_amount, stock_actual_price, revenue, is_marginal, stop_loss_price, stop_loss_amount, take_profit_price, take_profit_amount)
    VALUES (4, 19, 100, 0.00132, 0, True, 0, 0, 0, 0);


INSERT INTO operations (user_id, briefcase_id, status_id, operation_name_id, stock_id, stocks_amount, stock_price, stocks_per_lot, created_at)
    VALUES (1, 1, 3, 2, 19, 100, 0.00134, 1000000, NOW());
INSERT INTO operations (user_id, briefcase_id, status_id, operation_name_id, stock_id, stocks_amount, stock_price, stocks_per_lot, created_at)
    VALUES (1, 2, 3, 1, 15, 700, 100, 1, NOW());
INSERT INTO operations (user_id, briefcase_id, status_id, operation_name_id, stock_id, stocks_amount, stock_price, stocks_per_lot, created_at)
    VALUES (2, 3, 4, 1, 16, 1000, 1763, 1, NOW());
INSERT INTO operations (user_id, briefcase_id, status_id, operation_name_id, stock_id, stocks_amount, stock_price, stocks_per_lot, created_at)
    VALUES (2, 3, 1, 1, 18, 35, 101, 1, NOW());
INSERT INTO operations (user_id, briefcase_id, status_id, operation_name_id, stock_id, stocks_amount, stock_price, stocks_per_lot, created_at)
    VALUES (3, 4, 2, 2, 19, 100, 0.00134, 1, NOW());
INSERT INTO operations (user_id, briefcase_id, status_id, operation_name_id, stock_id, stocks_amount, stock_price, stocks_per_lot, created_at)
    VALUES (3, 4, 1, 1, 17, 100, 503.24, 1, NOW());