insert into menu_entity(menu_name, menu_price, menu_category, menu_status)
values ('아메리카노', 1800, 'HANDMADE', 'SELLING'),
       ('헤이즐넛', 2000, 'HANDMADE', 'SELLING'),
       ('아이스티', 1500, 'HANDMADE', 'SELLING');

insert into order_entity(order_id, order_price, order_quantity, order_date)
values (1, 4500, 3, '2015-04-13'),
       (2, 8000, 4, '2015-04-13'),
       (3, 1800, 1, '2015-04-13');

insert into order_detail_entity(ORDER_DETAIL_ID, ORDER_ID, MENU_ID, ORDER_DETAIL_QUANTITY)
values (1, 1, 3, 3),
       (2, 2, 2, 4),
       (3, 3, 1, 1);