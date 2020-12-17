create table t_diet (id bigint not null, primary key (id)) engine=InnoDB;
create table t_food_product_diet (food_product_id bigint not null, diet_id bigint not null, primary key (food_product_id, diet_id)) engine=InnoDB;
alter table t_food_product_diet add constraint fk_food_product_diet_fp_id foreign key (food_product_id) references t_food_product (id);
alter table t_food_product_diet add constraint fk_food_product_diet_diet_id foreign key (diet_id) references t_diet (id);