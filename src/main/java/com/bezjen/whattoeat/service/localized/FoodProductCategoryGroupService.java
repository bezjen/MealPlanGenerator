package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.repository.localized.FoodProductCategoryGroupRepository;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.entity.FoodProductCategoryGroup;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;

@Service
public class FoodProductCategoryGroupService
		extends LocalizedEntityService<FoodProductCategoryGroup, LocalizedEntityModel> {
	public FoodProductCategoryGroupService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			FoodProductCategoryGroupRepository entityRepository
	) {
		super(
				FoodProductCategoryGroup::new,
				LocalizedEntityModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.FOOD_PRODUCT_CATEGORY_GROUP
		);
	}
}
