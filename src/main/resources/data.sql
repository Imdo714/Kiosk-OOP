insert into menu_entity(menu_name, menu_price, menu_category, menu_status)
values ('아메리카노', 1800, 'HANDMADE', 'SELLING'),
       ('헤이즐넛', 2000, 'HANDMADE', 'SELLING'),
       ('아이스티', 1500, 'HANDMADE', 'SELLING');

insert into order_entity(order_price, order_quantity, order_date)
values (4500, 3, '2015-04-13'),
       (8000, 4, '2015-04-13'),
       (1800, 1, '2015-04-13');

insert into order_detail_entity(ORDER_ID, MENU_ID, ORDER_DETAIL_QUANTITY)
values (1, 3, 3),
       (2, 2, 4),
       (3, 1, 1);

INSERT INTO discount_entity (discount_code, discount_type, discount_value, discount_start, discount_end, menu_id)
VALUES (
           'SUMMER2024',  -- 할인 코드
           'PERCENT',  -- 할인 유형
           20,             -- 할인 값 (예: 20% 할인)
           '2025-04-01 00:00:00', -- 할인 시작 날짜 및 시간
           '2025-04-28 23:59:59',  -- 할인 종료 날짜 및 시간
            1
       );