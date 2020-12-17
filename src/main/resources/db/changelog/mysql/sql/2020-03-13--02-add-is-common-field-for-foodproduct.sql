alter table t_food_product add column is_common bit not null;
update t_food_product set is_common = 0;