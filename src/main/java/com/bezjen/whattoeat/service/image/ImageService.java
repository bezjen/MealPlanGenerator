package com.bezjen.whattoeat.service.image;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

import com.bezjen.whattoeat.item.ImageItemType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ValidationException;

@Service
public class ImageService {
	private String home;
	private String imagesDirectory = "/images";
	private String watermarkText = "WhatToEat";

	public ImageService(@Value("${WHATTOEAT_HOME}") String home) {
		this.home = home + imagesDirectory;
	}

	public byte[] getImage(String filename, ImageItemType imageItemType) throws IOException {
		String path = home + imageItemType.getImagesDirectory() + filename;
		byte[] result = getImageAsByteArrayIfExists(path);
		if (result.length == 0 && filename.contains("-orig.")) {
			result = getImageAsByteArrayIfExists(path.replace("-orig", ""));
		}
		return result;
	}

	public String getMediaType(String filename) {
		switch (FilenameUtils.getExtension(filename).toLowerCase()) {
			case "jpg":
			case "jpeg":
			case "jfif":
			case "jpe":
			case "pjpeg":
				return MediaType.IMAGE_JPEG_VALUE;
			case "png":
				return MediaType.IMAGE_PNG_VALUE;
		}
		throw new ValidationException("illegal file extension");
	}

	public void checkFileExtension(String filename) {
		switch (FilenameUtils.getExtension(filename).toLowerCase()) {
			case "png":
			case "jpg":
			case "jpeg":
			case "jfif":
			case "jpe":
			case "pjpeg":
				break;
			default:
				throw new ValidationException("illegal file extension");
		}
	}

	public String getImagesDirectory() {
		return imagesDirectory;
	}

	public void  saveImage(
			MultipartFile image,
			String filename,
			String extension,
			ImageItemType imageItemType
	) throws IOException {
		saveImage(
				image,
				home + imageItemType.getImagesDirectory(),
				filename,
				extension,
				imageItemType.getBaseWidth(),
				imageItemType.isSaveOriginal(),
				imageItemType.isWithWatermark(),
				imageItemType.isGalleryUsed()
		);
	}

	private void saveImage(
			MultipartFile image,
			String path,
			String filename,
			String extension,
			int baseWidth,
			boolean isSaveOriginal,
			boolean isWithWatermark,
			boolean isMultisize
	) throws IOException {
		String tempFilePath = path + filename + "-temp." + extension;
		File tempFile = new File(tempFilePath);
		try {
			Files.createDirectories(tempFile.toPath().getParent());
			image.transferTo(tempFile);
			BufferedImage sourceImage = ImageIO.read(tempFile);

			if (isSaveOriginal) {
				saveImage(sourceImage, path, filename + "-original", extension, sourceImage.getWidth());
			}
			if (isWithWatermark) {
				addWatermark(sourceImage);
			}
			if (isMultisize) {
				saveImage(
						sourceImage,
						path,
						filename + "-orig",
						extension,
						Math.max(sourceImage.getWidth(), baseWidth)
				);
			}
			saveImage(sourceImage, path, filename, extension, baseWidth);
		} finally {
			tempFile.delete();
		}

	}

	private void saveImage(
			BufferedImage image,
			String path,
			String filename,
			String extension,
			int baseWidth
	) throws IOException {
		int scaledHeight = (int) ((float) baseWidth / image.getWidth() * image.getHeight());
		boolean preserveAlpha = image.getTransparency() == Transparency.OPAQUE;
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledImage = new BufferedImage(baseWidth, scaledHeight, imageType);
		Graphics2D g = scaledImage.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(image, 0, 0, baseWidth, scaledHeight, null);
		String formattedPath = path + filename + "." + extension;
		File formattedFile = new File(formattedPath);
		ImageIO.write(
			scaledImage,
			extension,
			formattedFile
		);
		g.dispose();
	}

	private byte[] getImageAsByteArrayIfExists(String path) throws IOException {
		File initialFile = new File(path);
		if (initialFile.exists()) {
			InputStream in = new FileInputStream(initialFile);
			return IOUtils.toByteArray(in);
		}
		return new byte[0];
	}

	private void addWatermark(BufferedImage image) {
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		// initializes necessary graphic properties
		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2d.setComposite(alphaChannel);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, image.getWidth() / 10));
		FontMetrics fontMetrics = g2d.getFontMetrics();
		Rectangle2D rect = fontMetrics.getStringBounds(watermarkText, g2d);
		// calculates the coordinate where the String is painted
		int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
		int centerY = image.getHeight() / 2;
		// paints the textual watermark
		g2d.drawString(watermarkText, centerX, centerY);
		g2d.dispose();
	}
}
