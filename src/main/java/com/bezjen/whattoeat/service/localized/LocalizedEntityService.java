package com.bezjen.whattoeat.service.localized;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bezjen.whattoeat.repository.localized.*;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.entity.LocalizedEntity;
import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;

@Service
public abstract class LocalizedEntityService<E extends LocalizedEntity, M extends LocalizedEntityModel> {
	protected final LocalizationService localizationService;
	protected final LocalizedEntityRepository localizedEntityRepository;
	protected final CrudRepository<E, Long> entityRepository;
	protected final EntityType entityType;
	private final Supplier<E> entitySupplier;
	private final Supplier<M> modelSupplier;
	
	public LocalizedEntityService(
			Supplier<E> entitySupplier,
			Supplier<M> modelSupplier,
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			CrudRepository<E, Long> entityRepository,
			EntityType entityType
	) {
		this.entitySupplier = entitySupplier;
		this.modelSupplier = modelSupplier;
		this.localizationService = localizationService;
		this.localizedEntityRepository = localizedEntityRepository;
		this.entityRepository = entityRepository;
		this.entityType = entityType;
	}

	public void addEntity(M model) throws LocalizedException {
		E entity = getLocalizedEntity(model);
		entity.setEnLocale(model.getEnLocale());
		entity.setRuLocale(model.getRuLocale());
		if (isLocalizedEntityExists(entity)) {
			throw new LocalizedException("Entity exists", "messages.error.object.exists");
		}
		localizedEntityRepository.save(entity);
	}
	
	public void editEntity(Long id, M model) {
		E entity = getLocalizedEntity(id, model);
		entity.setRuLocale(model.getRuLocale());
		entity.setEnLocale(model.getEnLocale());
		localizedEntityRepository.save(entity);
	}

	public Optional<E> findById(Long id) {
		return entityRepository.findById(id);
	}

	public Iterable<E> findAll() {
		return entityRepository.findAll();
	}

	public List<E> findAllSorted(Locale locale) {	//TODO: move sorting to repo?
		Iterable<E> entities = entityRepository.findAll();
		List<E> result = StreamSupport.stream(entities.spliterator(), false)
				.sorted(Comparator.comparing(entity -> entity.localization(locale))).collect(Collectors.toList());
		return result;
	}

	public void delete(Long id) {	//TODO: delete images
		entityRepository.deleteById(id);
	}

	//TODO: think about this: synonyms in one language don't have multiple translation in another
	protected boolean isLocalizedEntityExists(E entity) {
		return localizationService.isLocalizedEntityExists(entity, entityType);
	}

	public M getLocalizedEntityModel() {
		return modelSupplier.get();
	}
	
	public M getLocalizedEntityModel(Long id) {
		E entity = findById(id).get();
		return getLocalizedEntityModel(entity);
	}
	
	protected M getLocalizedEntityModel(E entity) {
		M model = modelSupplier.get();
		model.setEnLocale(entity.localization(new Locale("en")));
		model.setRuLocale(entity.localization(new Locale("ru")));
		return model;
	}
	
	protected void fillLocalizedEntity(E entity, M model) {
	}

	private E getLocalizedEntity(M model) {
		E entity = entitySupplier.get();
		fillLocalizedEntity(entity, model);
		return entity;
	}
	
	private E getLocalizedEntity(Long id, M model) {
		E entity = findById(id).get();
		fillLocalizedEntity(entity, model);
		return entity;
	}
}
