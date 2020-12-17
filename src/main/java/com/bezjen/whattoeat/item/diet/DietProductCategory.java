package com.bezjen.whattoeat.item.diet;

public class DietProductCategory {
    private String name;
    private String colorClass;
    private String note;

    public DietProductCategory(String name, String colorClass, String note) {
        this.name = name;
        this.colorClass = colorClass;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorClass() {
        return colorClass;
    }

    public void setColorClass(String colorClass) {
        this.colorClass = colorClass;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
