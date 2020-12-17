create table t_recipe_diet (recipe_id bigint not null, diet_id bigint not null, primary key (recipe_id, diet_id)) engine=InnoDB;
alter table t_recipe_diet add constraint fk_recipe_diet_diet_id foreign key (diet_id) references t_diet (id);
alter table t_recipe_diet add constraint fk_recipe_diet_recipe_id foreign key (recipe_id) references t_recipe (id);