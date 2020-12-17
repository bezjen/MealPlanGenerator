alter table t_recipe add column servings_number integer not null;
update t_recipe set servings_number = 1;