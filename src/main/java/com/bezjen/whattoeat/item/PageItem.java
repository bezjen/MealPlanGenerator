package com.bezjen.whattoeat.item;

public class PageItem {
	private String titleKey;
	private String path;
	private String link;		//without "/" at start
	
	public PageItem(String link) {
		this(null, null, link);
	}
	public PageItem(String titleKey, String path, String link) {
		this.titleKey = titleKey;
		this.path = path;
		this.link = link;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTitleKey() {
		return titleKey;
	}
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
}
