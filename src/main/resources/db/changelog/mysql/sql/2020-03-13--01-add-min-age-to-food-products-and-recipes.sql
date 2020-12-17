alter table t_food_product drop column min_age;
alter table t_food_product add column min_age_month integer not null;
update t_food_product set min_age_month = 36;
alter table t_recipe add column min_age_month integer;