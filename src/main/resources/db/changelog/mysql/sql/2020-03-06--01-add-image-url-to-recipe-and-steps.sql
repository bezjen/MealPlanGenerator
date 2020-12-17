alter table t_recipe add column image_url varchar(255) default '/img/recipeThumbnail.png' not null;
alter table t_recipe_step add column image_url varchar(255);