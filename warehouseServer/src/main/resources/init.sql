create table if not exists  role
(
    id   bigint auto_increment
        primary key,
    name varchar(255) not null,
    constraint role_name_uindex
        unique (name)
);

create table if not exists warehouse
(
    id                  bigint auto_increment
        primary key,
    name                varchar(255)     not null,
    squar               double           not null,
    number_of_employees bigint default 0 null
);

create table if not exists user
(
    id           bigint auto_increment
        primary key,
    username     varchar(255) not null,
    password     varchar(255) not null,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    role_id      bigint       null,
    warehouse_id bigint       null,
    constraint user_username_uindex
        unique (username),
    constraint user_role_id_fk
        foreign key (role_id) references role (id),
    constraint user_warehouse_id_fk
        foreign key (warehouse_id) references warehouse (id)
);

create table if not exists category
(
    id   bigint auto_increment
        primary key,
    name varchar(255) not null,
    constraint category_name_uindex
        unique (name)
);

create table if not exists item
(
    id          bigint auto_increment
        primary key,
    name        varchar(255) null,
    price       double       not null,
    category_id bigint       null,
    constraint item_category_id_fk
        foreign key (category_id) references category (id)
);

create table if not exists warehouse_category
(
    warehouse_id bigint not null,
    category_id  bigint not null,
    constraint warehouse_category_category_id_fk
        foreign key (category_id) references category (id),
    constraint warehouse_category_warehouse_id_fk
        foreign key (warehouse_id) references warehouse (id)
);

create table if not exists warehouse_item
(
    warehouse_id bigint not null,
    item_id      bigint not null,
    constraint warehouse_item_item_id_fk
        foreign key (item_id) references item (id),
    constraint warehouse_item_warehouse_id_fk
        foreign key (warehouse_id) references warehouse (id)
);

create table if not exists category_item
(
    category_id bigint not null,
    item_id     bigint not null,
    constraint category_item_category_id_fk
        foreign key (category_id) references category (id),
    constraint category_item_item_id_fk
        foreign key (item_id) references item (id)
);

INSERT INTO warehouse.role (name)
VALUES ('ADMIN');

INSERT INTO warehouse.role (name)
VALUES ('MANAGER');

INSERT INTO warehouse.warehouse (name, squar, number_of_employees)
VALUES ('Склад1', 12.2, 2);

INSERT INTO warehouse.warehouse (name, squar, number_of_employees)
VALUES ('Склад2', 34.2, 2);

INSERT INTO warehouse.warehouse (name, squar, number_of_employees)
VALUES ('Склад3', 21.2, 2);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id, warehouse_id)
VALUES ('user', '123', 'Alex', 'L', 2, 1);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id, warehouse_id)
VALUES ('user1', '123', 'Kolya', 'L', 2, 1);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id, warehouse_id)
VALUES ('user2', '123', 'Tolya', 'L', 2, 2);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id, warehouse_id)
VALUES ('user3', '123', 'Maks', 'L', 2, 2);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id, warehouse_id)
VALUES ('user4', '123', 'Lena', 'L', 2, 3);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id, warehouse_id)
VALUES ('user5', '123', 'John', 'L', 2, 3);

INSERT INTO warehouse.user (username, password, first_name, last_name, role_id)
VALUES ('root', '123', 'Denis', 'L', 1);

INSERT INTO warehouse.category (name)
VALUES ('Бытовая техника');

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (1, 1);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (2, 1);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (3, 1);

INSERT INTO warehouse.category (name)
VALUES ('Электроника');

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (1, 2);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (2, 2);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (3, 2);

INSERT INTO warehouse.category (name)
VALUES ('Игрушки');

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (1, 3);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (2, 3);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (3, 3);

INSERT INTO warehouse.category (name)
VALUES ('Одежда');

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (1, 4);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (2, 4);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (3, 4);

INSERT INTO warehouse.category (name)
VALUES ('Мебель');

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (1, 5);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (2, 5);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (3, 5);

INSERT INTO warehouse.category (name)
VALUES ('Строительные материалы');

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (1, 6);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (2, 6);

INSERT INTO warehouse.warehouse_category (warehouse_id, category_id)
VALUES (3, 6);


INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Стиральная машина', 15000, 1);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (1, 1);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 1);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 1);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 1);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Холодильник', 15000, 1);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (1, 2);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 2);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 2);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 2);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Пылесос', 5000, 1);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (1, 3);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 3);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 3);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 3);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Телефон', 50000, 2);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (2, 4);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 4);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 4);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 4);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Планшет', 30000, 2);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (2, 5);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 5);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 5);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 5);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Копилка', 1000, 3);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (3, 6);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 6);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 6);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 6);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Робот', 3000, 3);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (3, 7);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 7);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 7);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 7);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Машинка', 3000, 3);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (3, 8);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 8);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 8);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 8);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Кукла', 2000, 3);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (3, 9);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 9);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 9);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 9);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Пуховик', 7000, 4);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (4, 10);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 10);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 10);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 10);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Рубашка', 1000, 4);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (4, 11);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 11);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 11);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 11);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Стул', 2000, 5);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (5, 12);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 12);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 12);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 12);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Стол', 5000, 5);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (5, 13);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 13);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 13);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 13);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Шкаф', 10000, 5);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (5, 14);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 14);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 14);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 14);

INSERT INTO warehouse.item (name, price, category_id)
VALUES ('Доска', 1000, 6);

INSERT INTO warehouse.category_item (category_id, item_id)
VALUES (6, 15);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (1, 15);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (2, 15);

INSERT INTO warehouse.warehouse_item (warehouse_id, item_id)
VALUES (3, 15);























