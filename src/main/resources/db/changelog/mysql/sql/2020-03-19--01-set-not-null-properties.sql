update t_food_product set proteins = 0 where proteins is null;
alter table t_food_product modify proteins decimal(19,2) not null;
update t_food_product set fat = 0 where fat is null;
alter table t_food_product modify fat decimal(19,2) not null;
update t_food_product set carbohydrates = 0 where carbohydrates is null;
alter table t_food_product modify carbohydrates decimal(19,2) not null;
update t_food_product set kilocalories = 0 where kilocalories is null;
alter table t_food_product modify kilocalories decimal(19,2) not null;

update t_recipe set min_age_month = 36 where min_age_month is null;
alter table t_recipe modify min_age_month integer not null;