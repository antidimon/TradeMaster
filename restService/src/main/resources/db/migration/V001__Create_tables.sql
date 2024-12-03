CREATE TABLE operation_names (
    id smallint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(20) NOT NULL
);

CREATE TABLE operation_statuses (
    id smallint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    status varchar(20) NOT NULL
);


CREATE TABLE users (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    fio varchar NOT NULL,
    age smallint NOT NULL CHECK (age > 14 and age < 150),
    passport_details varchar NOT NULL UNIQUE,
    phone_number varchar(11) NOT NULL UNIQUE,
    email varchar NOT NULL UNIQUE,
    is_qualified boolean DEFAULT False
);

CREATE TABLE briefcases (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint,
    briefcase_name varchar DEFAULT 'Test',
    deposited_balance numeric(15,5) DEFAULT 0,
    balance_now numeric(15,5) DEFAULT 0,
    free_balance numeric(15, 5) DEFAULT 0,
    total_revenue numeric(15, 5) DEFAULT 0,


    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE stocks (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar NOT NULL,
    price numeric(15, 5) NOT NULL,
    getted_at TIMESTAMP,
    predicted_price numeric(15, 5),
    stocks_per_lot int NOT NULL
);

CREATE TABLE briefcase_stocks (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    briefcase_id bigint,
    stock_id bigint,
    stocks_amount int NOT NULL,
    stock_actual_price numeric(15, 5),
    revenue numeric(15, 5) DEFAULT 0,
    is_marginal boolean NOT NULL,
    stop_loss_price numeric(15, 5) DEFAULT 0,
    stop_loss_amount int DEFAULT 0,
    take_profit_price numeric(15, 5) DEFAULT 0,
    take_profit_amount int DEFAULT 0,

    CONSTRAINT fk_briefcase_id FOREIGN KEY (briefcase_id) REFERENCES briefcases(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_stock_id FOREIGN KEY (stock_id) REFERENCES stocks(id) ON DELETE SET NULL
);

CREATE TABLE operations (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint,
    briefcase_id bigint,
    status_id smallint,
    operation_name_id smallint,
    stock_id bigint,
    stocks_amount int NOT NULL,
    stock_price numeric(15, 5),
    created_at TIMESTAMP,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_briefcase_id FOREIGN KEY (briefcase_id) REFERENCES briefcases(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES operation_statuses(id) ON UPDATE CASCADE,
    CONSTRAINT fk_operation_name_id FOREIGN KEY (operation_name_id) REFERENCES operation_names(id) ON UPDATE CASCADE,
    CONSTRAINT fk_stock_id FOREIGN KEY (stock_id) REFERENCES stocks(id) ON UPDATE CASCADE
);