package com.qrcode.kangutil.qrutils;

import java.awt.*;

/**
 * <pre>
 * Created by kang on 2020-12-19-10:14.
 * @author <a href="https://huanhuanlove.github.io">kang</a>
 * @company: 康康小课堂
 * @description: 图片资源配置
 * </pre>
 */
public class MatrixToLogoImageConfig {
    /**
     * logo 默认边框颜色
     */
    public static final Color DEFAULT_BORDERCOLOR = Color.RED;

    /**
     * logo默认边框宽度
     */
    public static final int DEFAULT_BORDER = 2;

    /**
     * logo大小默认为照片的1/5
     */
    public static final int DEFAULT_LOGOPART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public MatrixToLogoImageConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public MatrixToLogoImageConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public int getBorder() {
        return this.border;
    }

    public int getLogoPart() {
        return this.logoPart;
    }
}
