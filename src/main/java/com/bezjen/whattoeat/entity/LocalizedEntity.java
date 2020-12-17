package com.bezjen.whattoeat.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class LocalizedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Lob
	@Column(name = "ru_locale")
	protected String ruLocale;
	@Lob
	@Column(name = "en_locale")
	protected String enLocale;

	public String localization(Locale locale) {
		if (locale == null || Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			return StringUtils.defaultString(enLocale);
		}
		return StringUtils.defaultString(ruLocale);
	}

	public void setLocalization(Locale locale, String localization) {
		setLocalization(locale.getLanguage(), localization);
	}

	public void setLocalization(String langCode, String localization) {
		if (Locale.ENGLISH.getLanguage().equals(langCode)) {
			this.enLocale = localization;
		} else if ("ru".equals(langCode)) {
			this.ruLocale = localization;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "LocalizedEntity{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
