create table t_food_product_category_group (id bigint not null, primary key (id)) engine=InnoDB;
-- insert fake group
insert into t_food_product_category_group (id) values (-1);
insert into t_localization (id, entity_type, locale, value, entity_id) values (-2, 9, 'en', 'Fake category group', -1);
insert into t_localization (id, entity_type, locale, value, entity_id) values (-3, 9, 'ru', 'Fake category group', -1);
-- insert fake group ends
create table t_food_product_category (id bigint not null, group_id bigint not null, primary key (id)) engine=InnoDB;
alter table t_food_product_category add constraint fk_food_product_cat_group_id foreign key (group_id) references t_food_product_category_group (id);
-- insert fake category
insert into t_food_product_category (id, group_id) values (0, -1);
insert into t_localization (id, entity_type, locale, value, entity_id) values (-4, 8, 'en', 'Fake category', 0);
insert into t_localization (id, entity_type, locale, value, entity_id) values (-5, 8, 'ru', 'Fake category', 0);
-- insert fake category ends
alter table t_food_product add column category_id bigint not null;
alter table t_food_product add constraint fk_food_product_category_id foreign key (category_id) references t_food_product_category (id);
update t_food_product set category_id = 0;