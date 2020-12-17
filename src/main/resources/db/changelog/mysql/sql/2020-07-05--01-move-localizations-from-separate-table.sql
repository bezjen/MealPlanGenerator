alter table t_cooking_method add column en_locale longtext;
update t_cooking_method set en_locale = (select value from t_localization l where entity_id = t_cooking_method.id and locale = 'en');
alter table t_cooking_method add column ru_locale longtext;
update t_cooking_method set ru_locale = (select value from t_localization l where entity_id = t_cooking_method.id and locale = 'ru');
alter table t_diet add column en_locale longtext;
update t_diet set en_locale = (select value from t_localization l where entity_id = t_diet.id and locale = 'en');
alter table t_diet add column ru_locale longtext;
update t_diet set ru_locale = (select value from t_localization l where entity_id = t_diet.id and locale = 'ru');
alter table t_food_product add column en_locale longtext;
update t_food_product set en_locale = (select value from t_localization l where entity_id = t_food_product.id and locale = 'en');
alter table t_food_product add column ru_locale longtext;
update t_food_product set ru_locale = (select value from t_localization l where entity_id = t_food_product.id and locale = 'ru');
alter table t_food_product_category add column en_locale longtext;
update t_food_product_category set en_locale = (select value from t_localization l where entity_id = t_food_product_category.id and locale = 'en');
alter table t_food_product_category add column ru_locale longtext;
update t_food_product_category set ru_locale = (select value from t_localization l where entity_id = t_food_product_category.id and locale = 'ru');
alter table t_food_product_category_group add column en_locale longtext;
update t_food_product_category_group set en_locale = (select value from t_localization l where entity_id = t_food_product_category_group.id and locale = 'en');
alter table t_food_product_category_group add column ru_locale longtext;
update t_food_product_category_group set ru_locale = (select value from t_localization l where entity_id = t_food_product_category_group.id and locale = 'ru');
alter table t_measure add column en_locale longtext;
update t_measure set en_locale = (select value from t_localization l where entity_id = t_measure.id and locale = 'en');
alter table t_measure add column ru_locale longtext;
update t_measure set ru_locale = (select value from t_localization l where entity_id = t_measure.id and locale = 'ru');
alter table t_recipe add column en_locale longtext;
update t_recipe set en_locale = (select value from t_localization l where entity_id = t_recipe.id and locale = 'en');
alter table t_recipe add column ru_locale longtext;
update t_recipe set ru_locale = (select value from t_localization l where entity_id = t_recipe.id and locale = 'ru');
alter table t_recipe_category add column en_locale longtext;
update t_recipe_category set en_locale = (select value from t_localization l where entity_id = t_recipe_category.id and locale = 'en');
alter table t_recipe_category add column ru_locale longtext;
update t_recipe_category set ru_locale = (select value from t_localization l where entity_id = t_recipe_category.id and locale = 'ru');
alter table t_recipe_description add column en_locale longtext;
update t_recipe_description set en_locale = (select value from t_localization l where entity_id = t_recipe_description.id and locale = 'en');
alter table t_recipe_description add column ru_locale longtext;
update t_recipe_description set ru_locale = (select value from t_localization l where entity_id = t_recipe_description.id and locale = 'ru');
alter table t_recipe_step add column en_locale longtext;
update t_recipe_step set en_locale = (select value from t_localization l where entity_id = t_recipe_step.id and locale = 'en');
alter table t_recipe_step add column ru_locale longtext;
update t_recipe_step set ru_locale = (select value from t_localization l where entity_id = t_recipe_step.id and locale = 'ru');