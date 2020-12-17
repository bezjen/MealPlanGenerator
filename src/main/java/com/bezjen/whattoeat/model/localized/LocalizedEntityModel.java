package com.bezjen.whattoeat.model.localized;

import javax.validation.constraints.NotBlank;

public class LocalizedEntityModel {
	@NotBlank
	protected String ruLocale;
	@NotBlank
	protected String enLocale;
	public String getRuLocale() {
		return ruLocale;
	}
	public void setRuLocale(String ruLocale) {
		this.ruLocale = ruLocale;
	}
	public String getEnLocale() {
		return enLocale;
	}
	public void setEnLocale(String enLocale) {
		this.enLocale = enLocale;
	}
}
