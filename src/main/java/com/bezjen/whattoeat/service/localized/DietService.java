package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.entity.*;
import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.item.ImageItemType;
import com.bezjen.whattoeat.model.localized.DietModel;
import com.bezjen.whattoeat.repository.localized.DietDescriptionRepository;
import com.bezjen.whattoeat.repository.localized.DietRepository;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.service.image.ImageService;
import com.bezjen.whattoeat.service.LocalizationService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class DietService extends LocalizedEntityService<Diet, DietModel> {
	private ImageService imageService;
	private DietDescriptionRepository dietDescriptionRepository;

	public DietService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			DietRepository entityRepository,
			DietDescriptionRepository dietDescriptionRepository,
			ImageService imageService
	) {
		super(
				Diet::new,
				DietModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.DIET
		);
		this.imageService = imageService;
		this.dietDescriptionRepository = dietDescriptionRepository;
	}

	@Override
	@Transactional
	public void addEntity(DietModel entity) throws LocalizedException {
		super.addEntity(entity);
		saveImage(entity);
	}

	@Override
	@Transactional
	public void editEntity(Long id, DietModel entity) {
		super.editEntity(id, entity);
		//TODO: delete old image
		saveImage(entity);
	}

	@Override
	protected void fillLocalizedEntity(Diet entity, DietModel model) {
		if (model.getDietImage() != null) {
			entity.setImageUrl(
					imageService.getImagesDirectory() + ImageItemType.DIET.getImagesDirectory()
							+ model.getEnLocale().toLowerCase().replaceAll("\\s", "_") + "."
							+ FilenameUtils.getExtension(model.getDietImage().getOriginalFilename())
			);
		}
		if (entity.getDescription() == null) {
			entity.setDescription(new DietDescription());
			entity.getDescription().setDiet(entity);
		}
		entity.getDescription().setEnLocale(model.getEnDescription());
		entity.getDescription().setRuLocale(model.getRuDescription());
	}

	@Override
	public DietModel getLocalizedEntityModel(Diet entity) {
		DietModel model = super.getLocalizedEntityModel(entity);
		model.setDietImageUrl(entity.getImageUrl());
		if (entity.getDescription() != null) {
			model.setRuDescription(entity.getDescription().getRuLocale());
			model.setEnDescription(entity.getDescription().getEnLocale());
		}
		return model;
	}

	private void saveImage(DietModel entity) {
		try {
			imageService.saveImage(
					entity.getDietImage(),
					entity.getEnLocale().toLowerCase().replaceAll("\\s", "_"),
					FilenameUtils.getExtension(entity.getDietImage().getOriginalFilename()),
					ImageItemType.DIET
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
