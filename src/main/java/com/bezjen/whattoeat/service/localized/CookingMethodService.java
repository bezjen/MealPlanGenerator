package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.repository.localized.CookingMethodRepository;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.CookingMethod;
import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;

@Service
public class CookingMethodService extends LocalizedEntityService<CookingMethod, LocalizedEntityModel> {
	public CookingMethodService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			CookingMethodRepository entityRepository
	) {
		super(
				CookingMethod::new,
				LocalizedEntityModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.COOKING_METHOD
		);
	}
}
