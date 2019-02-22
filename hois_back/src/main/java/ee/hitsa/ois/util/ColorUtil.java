package ee.hitsa.ois.util;

/**
 * Resources:
 * https://en.wikipedia.org/wiki/CIELAB_color_space
 * https://stackoverflow.com/a/49170325
 */
public class ColorUtil {
    
    public static final double[] WHITE_LAB = new double[] {
        100.0,
        0.005260500115067401,
        -0.01040818483546424
    };
    
    public static final double[] BLACK_LAB = new double[] {
        -5.9604644775390625E-8,
        0.0,
        0.0
    };
    
    private static final double[] ILLUMINANT_D65 = new double[] {
        95.047,
        100.000,
        108.883
    };

    public static double getDeltaE_CIE76(String color, double[] colorToCompare) {
        Integer intval = Integer.decode(color);
        return distance(colorToCompare, RGB2LAB(getRGB(intval.intValue())));
    }
    
    private static double distance(double[] a, double[] b) {
        return Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2) + Math.pow(b[2] - a[2], 2));
    }
    
    private static int[] getRGB(int color) {
        return new int[] {(color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF};
    }
    
    private static double[] RGB2XYZ(int[] rgb) {
        double[] mathColors = new double[] {
            rgb[0] / 255.0,
            rgb[1] / 255.0,
            rgb[2] / 255.0
        };
        for (int i = 0; i < mathColors.length; i++) {
            if (mathColors[i] > 0.04045) {
                mathColors[i] = Math.pow((mathColors[i] + 0.055) / 1.055, 2.4) ;
            } else {
                mathColors[i] /= 12.92;
            }
            mathColors[i] *= 100;
        }
        return new double[] {
            (mathColors[0] * 0.4124) + (mathColors[1] * 0.3576) + (mathColors[2] * 0.1805),
            (mathColors[0] * 0.2126) + (mathColors[1] * 0.7152) + (mathColors[2] * 0.0722),
            (mathColors[0] * 0.0193) + (mathColors[1] * 0.1192) + (mathColors[2] * 0.9505)
        };
    }
    
    /**
     * Reference used is Illuminant D65
     * 
     * @param xyz CIEXYZ
     * @return CIELAB
     */
    private static double[] XYZ2LAB(double[] xyz) {
        double[] colorXYZ = new double[] {
            xyz[0] / ILLUMINANT_D65[0],
            xyz[1] / ILLUMINANT_D65[1],
            xyz[2] / ILLUMINANT_D65[2]
        };
        for (int i = 0; i < xyz.length; i++) {
            if (colorXYZ[i] > 0.008856) {
                colorXYZ[i] = Math.pow(colorXYZ[i], 1/3f);
            } else {
                colorXYZ[i] = colorXYZ[i] * 7.787 + (4 / 29f);
            }
        }
        return new double[] {
            116 * colorXYZ[1] - 16,
            500 * (colorXYZ[0] - colorXYZ[1]),
            200 * (colorXYZ[1] - colorXYZ[2])
        };
    }
    
    private static double[] RGB2LAB(int[] rgb) {
        return XYZ2LAB(RGB2XYZ(rgb));
    }
    
}
