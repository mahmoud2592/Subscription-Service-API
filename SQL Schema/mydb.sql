CREATE DATABASE mydb;

use mydb;

CREATE TABLE customers (
    id int NOT NULL AUTO_INCREMENT
    ,username varchar(255) NOT NULL UNIQUE
    ,first_name varchar(255) NOT NULL
    ,last_name varchar(255) NOT NULL
    ,email varchar(255) NOT NULL UNIQUE
    ,phone varchar(255) NOT NULL UNIQUE
    ,password varchar(255) NOT NULL
    ,created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,last_login timestamp
    ,balance DOUBLE(40,5) NOT NULL DEFAULT 0
    ,is_admin BOOLEAN NOT NULL DEFAULT false
    ,is_deleted BOOLEAN NOT NULL DEFAULT false
    ,PRIMARY KEY (id)
);

CREATE TABLE cards (
    id int NOT NULL AUTO_INCREMENT
    ,card_type varchar(255) NOT NULL
    ,issuer varchar(255) NOT NULL
    ,is_default BOOLEAN NOT NULL DEFAULT false
    ,card_number varchar(255) NOT NULL
    ,valid_until varchar(255) NOT NULL
    ,name_on_card varchar(255) NOT NULL
    ,cvv varchar(255) NOT NULL
    ,created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,is_deleted BOOLEAN NOT NULL DEFAULT false
    ,customer_id int NOT NULL 
    ,FOREIGN KEY (customer_id) REFERENCES customers(id)
    ,PRIMARY KEY (id)
);

CREATE TABLE products (
    id int NOT NULL AUTO_INCREMENT
    ,product_name varchar(255) NOT NULL
    ,created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,is_deleted BOOLEAN NOT NULL DEFAULT false
    ,customer_id int NOT NULL 
    ,FOREIGN KEY (customer_id) REFERENCES customers(id)
    ,PRIMARY KEY (id)
);

CREATE TABLE plans (
    id int NOT NULL AUTO_INCREMENT
    ,plan_name varchar(255) NOT NULL
    ,price_per_month DOUBLE(40,5) NOT NULL DEFAULT 0
    ,price_per_year DOUBLE(40,5) NOT NULL DEFAULT 0
    ,created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,is_deleted BOOLEAN NOT NULL DEFAULT false
    ,is_active BOOLEAN NOT NULL DEFAULT false
    ,product_id int NOT NULL 
    ,customer_id int NOT NULL 
    ,FOREIGN KEY (customer_id) REFERENCES customers(id)
    ,FOREIGN KEY (product_id) REFERENCES products(id)
    ,PRIMARY KEY (id)
);

CREATE TABLE subscriptions (
    id int NOT NULL AUTO_INCREMENT
    ,start_date timestamp NOT NULL
    ,end_date timestamp NOT NULL
    ,flag int NOT NULL DEFAULT 0
    ,created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,is_deleted BOOLEAN NOT NULL DEFAULT false
    ,customer_id int NOT NULL 
    ,plan_id int NOT NULL
    ,FOREIGN KEY (customer_id) REFERENCES customers(id)
    ,FOREIGN KEY (plan_id) REFERENCES plans(id)
    ,PRIMARY KEY (id)
);

CREATE TABLE invoices (
    id int NOT NULL AUTO_INCREMENT
    ,amount DOUBLE(40,5) NOT NULL
    ,created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,is_deleted BOOLEAN NOT NULL DEFAULT false
    ,customer_id int NOT NULL 
    ,FOREIGN KEY (customer_id) REFERENCES customers(id)
    ,PRIMARY KEY (id)
);

CREATE TABLE customers_subscriptions (
    customer_id int NOT NULL
    ,subscription_id int NOT NULL 
    , FOREIGN KEY (customer_id) REFERENCES customers(id)
    ,FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
);

CREATE TABLE subscriptions_cards (
    card_id int NOT NULL
    ,subscription_id int NOT NULL 
    ,FOREIGN KEY (card_id) REFERENCES cards(id)
    ,FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
);
