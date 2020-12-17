package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.entity.*;
import com.bezjen.whattoeat.model.localized.FoodProductCategoryModel;
import com.bezjen.whattoeat.repository.localized.FoodProductCategoryGroupRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bezjen.whattoeat.repository.localized.FoodProductCategoryRepository;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.stereotype.Service;

@Service
public class FoodProductCategoryService extends LocalizedEntityService<FoodProductCategory, FoodProductCategoryModel> {
	private FoodProductCategoryGroupRepository foodProductCategoryGroupRepository;

	public FoodProductCategoryService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			FoodProductCategoryRepository entityRepository,
			FoodProductCategoryGroupRepository foodProductCategoryGroupRepository
	) {
		super(
				FoodProductCategory::new,
				FoodProductCategoryModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.FOOD_PRODUCT_CATEGORY
		);
		this.foodProductCategoryGroupRepository = foodProductCategoryGroupRepository;
	}
	
	@Override
	protected void fillLocalizedEntity(FoodProductCategory entity, FoodProductCategoryModel model) {
		entity.setCategoryGroup(foodProductCategoryGroupRepository.findById(model.getCategoryGroup()).get());
	}
	
	@Override
	public FoodProductCategoryModel getLocalizedEntityModel(FoodProductCategory entity) {
		FoodProductCategoryModel model = super.getLocalizedEntityModel(entity);
		model.setCategoryGroup(entity.getCategoryGroup().getId());
		return model;
	}
	
	public Map<FoodProductCategoryGroup, List<FoodProductCategory>> findAllGroupedByCategory(Locale locale) {
		Map<FoodProductCategoryGroup, List<FoodProductCategory>> categories = 
				StreamSupport.stream(this.findAll().spliterator(), false)
							.sorted(
								Comparator.comparing(
										foodProductCategory ->
												((FoodProductCategory)foodProductCategory)
														.getCategoryGroup()
														.localization(locale)
								).thenComparing(f -> ((FoodProductCategory)f).localization(locale))
							).collect(Collectors.groupingBy(FoodProductCategory::getCategoryGroup));
		return categories;
	}
	
	@Override
	protected boolean isLocalizedEntityExists(FoodProductCategory entity) {
		FoodProductCategoryRepository foodProductCategoryRepository = (FoodProductCategoryRepository) entityRepository;
		List<FoodProductCategory> foodProductCategories = new ArrayList<>();
		if (foodProductCategoryRepository.existsByEnLocale(entity.getEnLocale())) {
			foodProductCategories.addAll(foodProductCategoryRepository.findByEnLocale(entity.getEnLocale()));
		}
		if (foodProductCategoryRepository.existsByRuLocale(entity.getRuLocale())) {
			foodProductCategories.addAll(foodProductCategoryRepository.findByRuLocale(entity.getRuLocale()));
		}
		for (FoodProductCategory foodProductCategory : foodProductCategories) {
			if (entity.getCategoryGroup().getId().equals(foodProductCategory.getCategoryGroup().getId())) {
				return true;
			}
		}
		return false;
	}
}
