alter table t_recipe add column is_snack bit not null;
update t_recipe set is_snack = 0;