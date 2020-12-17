alter table t_recipe add column is_breakfast_or_brunch bit not null;
update t_recipe set is_breakfast_or_brunch = 0;