package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.entity.*;
import com.bezjen.whattoeat.model.localized.RecipeCategoryModel;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.repository.localized.RecipeCategoryRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeCategoryService extends LocalizedEntityService<RecipeCategory, RecipeCategoryModel> {
	public RecipeCategoryService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			RecipeCategoryRepository entityRepository) {
		super(
				RecipeCategory::new,
				RecipeCategoryModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.RECIPE_CATEGORY
		);
	}
	
	@Override
	protected void fillLocalizedEntity(RecipeCategory entity, RecipeCategoryModel model) {
		if (model.getParentCategory() != null) {
			entity.setParent(entityRepository.findById(model.getParentCategory()).get());
		}
	}
	
	@Override
	public RecipeCategoryModel getLocalizedEntityModel(RecipeCategory entity) {
		RecipeCategoryModel model = super.getLocalizedEntityModel(entity);
		model.setParentCategory(entity.getParent() == null ? null : entity.getParent().getId());
		return model;
	}
	
	public Map<RecipeCategory, List<RecipeCategory>> findAllGroupedByCategory(Locale locale) {
		Iterable<RecipeCategory> recipeCategories = this.findAll();

		Map<RecipeCategory, List<RecipeCategory>> categories =
				StreamSupport.stream(recipeCategories.spliterator(), false).filter(c -> c.getParent() != null)
							.sorted(
								Comparator.comparing(f -> ((RecipeCategory)f).getParent().localization(locale))
										.thenComparing(f -> ((RecipeCategory)f).localization(locale))
							).collect(Collectors.groupingBy(RecipeCategory::getParent));
		categories.put(null,
				StreamSupport.stream(recipeCategories.spliterator(), false)
						.filter(recipeCategory -> recipeCategory.getParent() == null).collect(Collectors.toList()));
		return categories;
	}
	
	@Override
	protected boolean isLocalizedEntityExists(RecipeCategory entity) {
		RecipeCategoryRepository recipeCategoryRepository = (RecipeCategoryRepository) entityRepository;
		List<RecipeCategory> recipeCategories = new ArrayList<>();
		if (recipeCategoryRepository.existsByEnLocale(entity.getEnLocale())) {
			recipeCategories.addAll(recipeCategoryRepository.findByEnLocale(entity.getEnLocale()));
		}
		if (recipeCategoryRepository.existsByRuLocale(entity.getRuLocale())) {
			recipeCategories.addAll(recipeCategoryRepository.findByRuLocale(entity.getRuLocale()));
		}
		for (RecipeCategory recipeCategory : recipeCategories) {
			if (entity.getParent() == null && recipeCategory.getParent() == null
					|| entity.getParent() != null && recipeCategory.getParent() != null
					&& recipeCategory.getParent().getId().equals(recipeCategory.getParent().getId())) {
				return true;
			}
		}
		return false;
	}
}
