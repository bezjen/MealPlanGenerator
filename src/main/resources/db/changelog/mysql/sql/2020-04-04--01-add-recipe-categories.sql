create table t_recipe_category (id bigint not null, parent_id bigint, primary key (id)) engine=InnoDB;
alter table t_recipe_category add constraint fk_recipe_cat_parent_id foreign key (parent_id) references t_recipe_category (id);
-- insert fake category
insert into t_recipe_category (id, parent_id) values (-6, null);
insert into t_localization (id, entity_type, locale, value, entity_id) values (-7, 10, 'en', 'Fake recipe category', -6);
insert into t_localization (id, entity_type, locale, value, entity_id) values (-8, 10, 'ru', 'Fake recipe category', -6);

alter table t_recipe add column category_id bigint not null;
update t_recipe set category_id = -6;
alter table t_recipe add constraint fk_recipe_category_id foreign key (category_id) references t_recipe_category (id);