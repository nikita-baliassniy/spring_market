BEGIN;
SET search_path TO hiber,public;
DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products (
    id                  bigserial PRIMARY KEY,
    title               VARCHAR(255),
    price                numeric(6, 2),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

INSERT INTO products (title, price)
VALUES
('milk', 79.90),
('bread', 24.90),
('butter', 220.00),
('cheese', 350.55),
('pepsi', 99.99),
('apple', 9.90),
('paper', 24.90),
('pineapple', 221.00),
('borshch', 350.55),
('garlic', 69.95),
('potato', 19.90),
('glass', 44.90),
('vodka', 234.50),
('soup', 350.55),
('mayonnaise', 69.95),
('tomato', 9.99),
('yalta onion', 24.90),
('box', 220.00),
('bag', 310.45),
('pen', 65.05);

COMMIT;