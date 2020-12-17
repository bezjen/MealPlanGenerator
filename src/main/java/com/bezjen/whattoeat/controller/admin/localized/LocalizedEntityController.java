package com.bezjen.whattoeat.controller.admin.localized;

import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bezjen.whattoeat.controller.BaseController;
import com.bezjen.whattoeat.entity.LocalizedEntity;
import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;
import com.bezjen.whattoeat.service.localized.LocalizedEntityService;

public abstract class LocalizedEntityController<E extends LocalizedEntity, M extends LocalizedEntityModel>
		extends BaseController {
	protected LocalizedEntityService<E, M> entityService;
	protected Map<PageType, PageItem> pages;

	public LocalizedEntityController(
			MessageSource messageSource,
			LocalizedEntityService<E, M> entityService
	) {
		super(messageSource);
		this.entityService = entityService;
	}

	@GetMapping(value = {"", "/"})
	public String show(Model model, Locale locale) {
		addMetadataToModel(model, getMessage(getGeneralPage().getTitleKey()), "", null);
		model.addAttribute("addPageLink", getAddPage().getLink());
		model.addAttribute("editPageLink", getEditPage().getLink());
		model.addAttribute("deletePageLink", getDeletePage().getLink());
		model.addAttribute("model", entityService.findAllSorted(locale));
		return getGeneralPage().getPath();
	}
	
	@GetMapping("/add")
	public String add(Model model, Locale locale) {
		try {
			addMetadataToModel(model, getMessage(getAddPage().getTitleKey()), "", null);
			model.addAttribute("model", entityService.getLocalizedEntityModel());
			fillSaveFormModelDefaultData(model, locale);
        } catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
        }
		return getAddPage().getPath();
	}
	
	@PostMapping("/add")
	public String add(@Valid @ModelAttribute("model") M addModel,
							BindingResult result, Model model, Locale locale) {
		try {
			addMetadataToModel(model, getMessage(getAddPage().getTitleKey()), "", null);
			if (result.hasErrors()) {
				model.addAttribute(result);
				return getAddPage().getPath();
			}
			entityService.addEntity(addModel);
		} catch (LocalizedException e) {
			logger.error("", e);
			addMessageToModel(model, MessageType.ERROR, e.getKey());
			fillSaveFormModelDefaultData(model, locale);
            return getAddPage().getPath();
        } catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
			fillSaveFormModelDefaultData(model, locale);
            return getAddPage().getPath();
        }
		return "redirect:" + getGeneralPage().getLink();
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, Locale locale) {
		addMetadataToModel(model, getMessage(getEditPage().getTitleKey()), "", null);
		model.addAttribute("model", entityService.getLocalizedEntityModel(id));
		model.addAttribute("id", id);
		fillSaveFormModelDefaultData(model, locale);
		return getEditPage().getPath();
	}
	
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, @Valid @ModelAttribute("model") M editModel,
			BindingResult result, Model model, Locale locale) {
		try {
			addMetadataToModel(model, getMessage(getAddPage().getTitleKey()), "", null);
			if (result.hasErrors()) {
				model.addAttribute(result);
				return getEditPage().getPath();
			}
			entityService.editEntity(id, editModel);
        } catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
			fillSaveFormModelDefaultData(model, locale);
            return getEditPage().getPath();
        }
		return "redirect:" + getGeneralPage().getLink();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model, Locale locale) {
		try {
			entityService.delete(id);
        } catch (DataIntegrityViolationException e) {
			logger.error("", e);
			addMessageToModel(model, MessageType.ERROR, "messages.error.objectInUse");
			return show(model, locale);
		} catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
            return show(model, locale);
        }
		return "redirect:" + getGeneralPage().getLink();
	}
	
	protected void fillSaveFormModelDefaultData(Model model, Locale locale) {}

	@PostConstruct
	protected abstract void initPages();
	
	protected PageItem getGeneralPage() {
		return pages.get(PageType.GENERAL);
	}
	
	private PageItem getAddPage() {
		return pages.get(PageType.ADD);
	}
	
	private PageItem getEditPage() {
		return pages.get(PageType.EDIT);
	}
	
	private PageItem getDeletePage() {
		return pages.get(PageType.DELETE);
	}
	
}