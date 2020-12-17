alter table t_food_product add column min_age decimal(19,2) not null;
update t_food_product set min_age = 0;