package com.bezjen.whattoeat.item;

public enum ImageItemType {
    FOOD_PRODUCT("/foodProducts/", 150, false, false, false),
    DIET("/diets/", 500, false, false, false),
    RECIPE("/recipes/", 500, true, true, true),
    AVATAR("/avatars/", 150, false, false, false);

    ImageItemType(
            String imagesDirectory,
            int baseWidth,
            boolean isSaveOriginal,
            boolean isWithWatermark,
            boolean isGalleryUsed
    ) {
        this.imagesDirectory = imagesDirectory;
        this.baseWidth = baseWidth;
        this.isSaveOriginal = isSaveOriginal;
        this.isWithWatermark = isWithWatermark;
        this.isGalleryUsed = isGalleryUsed;
    }

    private String imagesDirectory;
    private int baseWidth;
    private boolean isSaveOriginal;
    private boolean isWithWatermark;
    private boolean isGalleryUsed;

    public String getImagesDirectory() {
        return imagesDirectory;
    }

    public int getBaseWidth() {
        return baseWidth;
    }

    public boolean isSaveOriginal() {
        return isSaveOriginal;
    }

    public boolean isWithWatermark() {
        return isWithWatermark;
    }

    public boolean isGalleryUsed() {
        return isGalleryUsed;
    }
}
