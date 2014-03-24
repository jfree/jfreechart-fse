/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * ------------------------
 * ImageEncoderFactory.java
 * ------------------------
 * (C) Copyright 2004-2012, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 01-Aug-2004 : Initial version (RA);
 * 01-Nov-2005 : Now using ImageIO for JPEG encoding, so we no longer have a
 *               dependency on com.sun.* which isn't available on all
 *               implementations (DG);
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 *
 */

package org.jfree.chart.encoders;

import java.util.HashMap;

/**
 * Factory class for returning {@link ImageEncoder}s for different
 * {@link ImageFormat}s.
 */
public class ImageEncoderFactory {

    /** Storage for the encoders. */
    private static HashMap<String, String> encoders = null;

    static {
        init();
    }

    /**
     * Sets up default encoders (uses Sun PNG Encoder if JDK 1.4+ and the
     * SunPNGEncoderAdapter class is available).
     */
    private static void init() {
        encoders = new HashMap<String, String>();
        encoders.put("jpeg", "org.jfree.chart.encoders.SunJPEGEncoderAdapter");
        encoders.put("png", "org.jfree.chart.encoders.SunPNGEncoderAdapter");
    }

    /**
     * Used to set additional encoders or replace default ones.
     *
     * @param format  The image format name.
     * @param imageEncoderClassName  The name of the ImageEncoder class.
     */
    public static void setImageEncoder(String format,
                                       String imageEncoderClassName) {
        encoders.put(format, imageEncoderClassName);
    }

    /**
     * Used to retrieve an ImageEncoder for a specific image format.
     *
     * @param format  The image format required.
     *
     * @return The ImageEncoder or <code>null</code> if none available.
     */
    public static ImageEncoder newInstance(String format) {
        ImageEncoder imageEncoder = null;
        String className = encoders.get(format);
        if (className == null) {
            throw new IllegalArgumentException("Unsupported image format - "
                    + format);
        }
        try {
            Class imageEncoderClass = Class.forName(className);
            imageEncoder = (ImageEncoder) imageEncoderClass.newInstance();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.toString());
        }
        return imageEncoder;
    }

    /**
     * Used to retrieve an ImageEncoder for a specific image format.
     *
     * @param format  The image format required.
     * @param quality  The quality to be set before returning.
     *
     * @return The ImageEncoder or <code>null</code> if none available.
     */
    public static ImageEncoder newInstance(String format, float quality) {
        ImageEncoder imageEncoder = newInstance(format);
        imageEncoder.setQuality(quality);
        return imageEncoder;
    }

    /**
     * Used to retrieve an ImageEncoder for a specific image format.
     *
     * @param format  The image format required.
     * @param encodingAlpha  Sets whether alpha transparency should be encoded.
     *
     * @return The ImageEncoder or <code>null</code> if none available.
     */
    public static ImageEncoder newInstance(String format,
                                           boolean encodingAlpha) {
        ImageEncoder imageEncoder = newInstance(format);
        imageEncoder.setEncodingAlpha(encodingAlpha);
        return imageEncoder;
    }

    /**
     * Used to retrieve an ImageEncoder for a specific image format.
     *
     * @param format  The image format required.
     * @param quality  The quality to be set before returning.
     * @param encodingAlpha  Sets whether alpha transparency should be encoded.
     *
     * @return The ImageEncoder or <code>null</code> if none available.
     */
    public static ImageEncoder newInstance(String format, float quality,
                                           boolean encodingAlpha) {
        ImageEncoder imageEncoder = newInstance(format);
        imageEncoder.setQuality(quality);
        imageEncoder.setEncodingAlpha(encodingAlpha);
        return imageEncoder;
    }

}
