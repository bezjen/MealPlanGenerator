package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.repository.localized.MeasureRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.entity.Measure;
import com.bezjen.whattoeat.entity.MeasureType;
import com.bezjen.whattoeat.model.localized.MeasureModel;

@Service
public class MeasureService extends LocalizedEntityService<Measure, MeasureModel> {	
	public MeasureService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			MeasureRepository entityRepository
	) {
		super(
				Measure::new,
				MeasureModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.MEASURE
		);
	}
	
	@Override
	protected void fillLocalizedEntity(Measure entity, MeasureModel model) {
		entity.setInCanonicalUnits(model.getInCanonicalUnits());
		entity.setMeasureType(MeasureType.fromCode(model.getMeasureType()));
	}
	
	@Override
	public MeasureModel getLocalizedEntityModel(Measure entity) {
		MeasureModel model = super.getLocalizedEntityModel(entity);
		model.setInCanonicalUnits(entity.getInCanonicalUnits());
		model.setMeasureType(entity.getMeasureType().getCode());
		return model;
	}
}
