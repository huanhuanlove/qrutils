package com.qrcode.kangutil.qrutils;

import com.google.zxing.LuminanceSource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * <pre>
 * Created by kang on 2020-12-19-9:31.
 * @author <a href="https://huanhuanlove.github.io">kang</a>
 * @company: 康康小课堂
 * @description: 加载图片资源
 * </pre>
 */
public class ImageLoadSource extends LuminanceSource {

    private final BufferedImage image;
    private final int left;
    private final int top;

    public ImageLoadSource(BufferedImage image) {
        this(image, 0, 0, image.getWidth(), image.getHeight());
    }

    public ImageLoadSource(BufferedImage image, int left, int top, int width, int height) {
        super(width, height);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if (left + width > imageWidth || top + height > imageHeight) {
            throw new IllegalArgumentException("请选择合适的长宽进行剪裁");
        }

        for (int i = top; i < top + height; i++) {
            for (int x = left; x < left + width; x++) {
                if ((image.getRGB(x, i) & 0xFF000000) == 0) {
                    image.setRGB(x, i, 0xFFFFFFFF);
                }
            }
        }

        this.image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_BYTE_GRAY);
        this.image.getGraphics().drawImage(image, 0, 0, null);
        this.left = left;
        this.top = top;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException(
                    "设置的列超出范围： " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        this.image.getRaster().getDataElements(this.left, this.top + y, width, 1, row);
        return row;
    }

    @Override
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];
        this.image.getRaster().getDataElements(this.left, this.top, width,
                height, matrix);
        return matrix;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

    @Override
    public LuminanceSource crop(int left, int top, int width, int height) {
        return new ImageLoadSource(this.image, this.left + left,
                this.top + top, width, height);
    }

    @Override
    public boolean isRotateSupported() {
        return true;
    }

    @Override
    public LuminanceSource rotateCounterClockwise() {
        int imageWidth = this.image.getWidth();
        int imageHeight = this.image.getHeight();

        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0,
                0.0, imageWidth);
        BufferedImage rotatedImage = new BufferedImage(imageHeight,
                imageWidth, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(this.image, transform, null);
        g.dispose();

        int width = getWidth();
        return new ImageLoadSource(rotatedImage, this.top,
                imageWidth - (this.left + width), getHeight(), width);
    }
}
