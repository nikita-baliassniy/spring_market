BEGIN;
SET search_path TO hiber,public;
DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products (
    id                  bigserial PRIMARY KEY,
    title               VARCHAR(255),
    cost                numeric(6, 2),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

INSERT INTO products (title, cost)
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

CREATE TABLE users (
    id                      bigserial primary key,
    username                varchar(30) not null unique,
    password                varchar(80) not null,
    email                   varchar(50) unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

CREATE TABLE addresses (
    id                      bigserial primary key,
    address                 varchar(255) not null
);

CREATE TABLE orders (
    id                      bigserial PRIMARY KEY,
    owner_id                bigint references users (id),
    total_cost              numeric(6, 2),
    address_id              bigint references addresses (id),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

CREATE TABLE roles (
    id                      bigserial primary key,
    name                    varchar(50) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id                 bigint not null references users (id),
    role_id                 bigint not null references roles (id),
    primary key (user_id, role_id)
);

CREATE TABLE order_items (
    id                      bigserial PRIMARY KEY,
    order_id                bigint references orders (id),
    product_id              bigint references products (id),
    title                   varchar(255),
    quantity                int,
    cost_per_product        numeric(6, 2),
    cost                    numeric(6, 2),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users (username, password, email)
values
('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

COMMIT;