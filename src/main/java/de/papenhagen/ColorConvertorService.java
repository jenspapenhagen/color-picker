package de.papenhagen;


import de.papenhagen.enities.*;
import jakarta.annotation.Nonnull;
import jakarta.inject.Singleton;

@Singleton
public class ColorConvertorService {

    public Color convertRGBtoCMYK(@Nonnull final Color color) throws IllegalArgumentException {
        if (color instanceof RGB rgb) {
            final double percentageR = rgb.red() / 255.0 * 100;
            final double percentageG = rgb.green() / 255.0 * 100;
            final double percentageB = rgb.blue() / 255.0 * 100;

            final double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);

            if (k == 100) {
                return new CMYK(0, 0, 0, 100);
            }

            int c = (int) ((100 - percentageR - k) / (100 - k) * 100);
            int m = (int) ((100 - percentageG - k) / (100 - k) * 100);
            int y = (int) ((100 - percentageB - k) / (100 - k) * 100);

            return new CMYK(c, m, y, k);
        } else {
            throw new IllegalArgumentException("wrong color typ");
        }
    }

    public Color convertRGBtoHSL(@Nonnull final Color color) throws IllegalArgumentException {
        if (color instanceof RGB rgb) {

            final float r = rgb.red();
            final float g = rgb.green();
            final float b = rgb.blue();

            //	Minimum and Maximum RGB values are used in the HSL calculations
            final float min = Math.min(r, Math.min(g, b));
            final float max = Math.max(r, Math.max(g, b));

            //  Calculate the Hue
            float h = 0;

            if (max == min) {
                h = 0;
            } else if (max == r) {
                h = ((60 * (g - b) / (max - min)) + 360) % 360;
            } else if (max == g) {
                h = (60 * (b - r) / (max - min)) + 120;
            } else if (max == b) {
                h = (60 * (r - g) / (max - min)) + 240;
            }

            //  Calculate the Luminance
            float l = (max + min) / 2;

            //  Calculate the Saturation
            float s = 0;

            if (max == min) {
                s = 0;
            } else if (l <= .5f) {
                s = (max - min) / (max + min);
            } else {
                s = (max - min) / (2 - max - min);
            }

            return new HSL(h, s * 100, l * 100);
        } else {
            throw new IllegalArgumentException("wrong color typ");
        }
    }

    public Color convertRGBtoYUV(@Nonnull final Color color) throws IllegalArgumentException {
        if (color instanceof RGB rgb) {
            final float r = rgb.red();
            final float g = rgb.green();
            final float b = rgb.blue();

            int y = (int) Math.round(r * .299000 + g * .587000
                    + b * .114000);
            int u = (int) Math.round(r * -.168736 + g * -.331264
                    + b * .500000 + 128);
            int v = (int) Math.round(r * .500000 + g * -.418688
                    + b * -.081312 + 128);

            return new YUV(((y & 0xFF) << 16), ((u & 0xFF) << 8), (v & 0xFF));
        } else {
            throw new IllegalArgumentException("wrong color typ");
        }
    }

}
