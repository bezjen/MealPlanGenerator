package com.bezjen.whattoeat.service.localized;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bezjen.whattoeat.entity.FoodProductCategoryGroup;
import com.bezjen.whattoeat.item.ImageItemType;
import com.bezjen.whattoeat.repository.localized.FoodProductRepository;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.entity.FoodProduct;
import com.bezjen.whattoeat.entity.FoodProductCategory;
import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.model.localized.FoodProductModel;
import com.bezjen.whattoeat.repository.localized.FoodProductCategoryRepository;
import com.bezjen.whattoeat.service.image.ImageService;

@Service
public class FoodProductService extends LocalizedEntityService<FoodProduct, FoodProductModel> {
	private ImageService imageService;
	private FoodProductCategoryRepository foodProductCategoryRepository;
	
	public FoodProductService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			FoodProductRepository entityRepository,
			ImageService imageService,
			FoodProductCategoryRepository foodProductCategoryRepository
	) {
		super(
				FoodProduct::new,
				FoodProductModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.FOOD_PRODUCT
		);
		this.imageService = imageService;
		this.foodProductCategoryRepository = foodProductCategoryRepository;
	}
	
	@Override
	@Transactional
	public void addEntity(FoodProductModel entity) throws LocalizedException {
		super.addEntity(entity);
		try {
			imageService.saveImage(
					entity.getFoodProductImage(),
					entity.getEnLocale().toLowerCase().replaceAll("\\s", "_"),
					FilenameUtils.getExtension(entity.getFoodProductImage().getOriginalFilename()),
					ImageItemType.FOOD_PRODUCT
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void fillLocalizedEntity(FoodProduct entity, FoodProductModel model) {
		if (model.getFoodProductImage() != null) {
			entity.setImageUrl(
					imageService.getImagesDirectory() + ImageItemType.FOOD_PRODUCT.getImagesDirectory()
							+ model.getEnLocale().toLowerCase().replaceAll("\\s", "_") + "."
							+ FilenameUtils.getExtension(model.getFoodProductImage().getOriginalFilename())
			);
		}
		entity.setMinAgeMonth(12 * model.getMinAgeYear() + model.getMinAgeMonth());
		entity.setKilocalories(model.getKilocalories());
		entity.setProteins(model.getProteins());
		entity.setFat(model.getFat());
		entity.setCarbohydrates(model.getCarbohydrates());
		entity.setCommon(model.isCommon());
		entity.setCategory(foodProductCategoryRepository.findById(model.getCategory()).get());
	}
	
	@Override
	public FoodProductModel getLocalizedEntityModel(FoodProduct entity) {
		FoodProductModel model = super.getLocalizedEntityModel(entity);
		model.setFoodProductImageUrl(entity.getImageUrl());
		model.setMinAgeYear(entity.getMinAgeMonth() / 12);
		model.setMinAgeMonth(entity.getMinAgeMonth() % 12);
		model.setKilocalories(entity.getKilocalories());
		model.setProteins(entity.getProteins());
		model.setFat(entity.getFat());
		model.setCarbohydrates(entity.getCarbohydrates());
		model.setCommon(entity.isCommon());
		model.setCategory(entity.getCategory().getId());
		return model;
	}
	
	public Map<FoodProductCategory, List<FoodProduct>> findAllGroupedByCategory(Locale locale) {
		Comparator<FoodProductCategory> foodProductCategoryComparator =
				Comparator.comparing(foodProductCategory -> foodProductCategory.localization(locale));
		return StreamSupport.stream(this.findAll().spliterator(), false)
				.sorted(Comparator.comparing(foodProductCategory -> foodProductCategory.localization(locale)))
				.collect(
						Collectors.groupingBy(
								FoodProduct::getCategory, () -> new TreeMap<>(foodProductCategoryComparator),
								Collectors.toList()
						)
				);
	}

	public Map<FoodProductCategoryGroup, Map<FoodProductCategory, List<FoodProduct>>>
		findAllGroupedByCategoryThenGroupedByCategoryGroup(Locale locale
	) {
		Comparator<FoodProductCategory> foodProductCategoryComparator =
				Comparator.comparing(category -> category.localization(locale));
		Comparator<FoodProductCategoryGroup> foodProductCategoryGroupComparator =
				Comparator.comparing(group -> group.localization(locale));
		Map<FoodProductCategoryGroup, Map<FoodProductCategory, List<FoodProduct>>> result =
				new TreeMap<>(foodProductCategoryGroupComparator);
		Iterable<FoodProduct> foodProducts = this.findAllSorted(locale);
		for (FoodProduct foodProduct : foodProducts) {
			FoodProductCategory category = foodProduct.getCategory();
			FoodProductCategoryGroup categoryGroup = category.getCategoryGroup();
			if (!result.containsKey(categoryGroup)) {
				result.put(categoryGroup, new TreeMap<>(foodProductCategoryComparator));
			}
			Map<FoodProductCategory, List<FoodProduct>> categoryGroupMap = result.get(categoryGroup);
			if (!categoryGroupMap.containsKey(category)) {
				categoryGroupMap.put(category, new ArrayList<>());
			}
			categoryGroupMap.get(category).add(foodProduct);
		}
		return result;
	}
}
