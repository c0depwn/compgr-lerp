package ch.fhnw.compgr;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;

public class Main {
    public static void main(String[] args) {
        int w = 600;
        int h = 300;

        var frame = new JFrame();
        var source = draw(w, h);
        var image = Toolkit.getDefaultToolkit().createImage(source);
        var img = new javax.swing.ImageIcon(image);
        frame.add(new JLabel(img));
        frame.setSize(w, h);
        frame.setVisible(true);
    }
    public static MemoryImageSource draw(int w, int h) {
        var red = new Vector3(1, 0, 0);
        var green = new Vector3(0, 1, 0);

        int[] pixels = new int[w*h];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                // calculate the color in linear rgb using vector based linear interpolation
                var color = lerp(red, green, (float) col / w);

                // convert the linear RGB to sRGB
                var sRGB = sRGB(color, 2.2f);

                // fill the pixels alpha=FF r=x g=y b=z
                pixels[(row*w)+col] =
                        (255 << 24) |
                                (Math.round(sRGB.x()) << 16) |
                                (Math.round(sRGB.y()) << 8) |
                                (Math.round(sRGB.z()));
            }
        }

        return new MemoryImageSource(w, h, ColorModel.getRGBdefault(), pixels, 0, w);
    }

    /**
     * Perform vector based linear interpolation based on parameter t.
     * @param a the starting vector
     * @param b the target vector
     * @param t the parameter characterising "progress" between the two vectors
     * @return a new Vector which points to the lerp'ed point
     */
    public static Vector3 lerp(Vector3 a, Vector3 b, float t) {
        return b.subtract(a).multiply(t).add(a);
    }

    /**
     * Convert a linear RGB value to a sRGB value.
     * @param lRGB a linear RGB value used for computations.
     * @return sRGB value which can be used to display color.
     */
    public static Vector3 sRGB(Vector3 lRGB, float gammaAdjustmentFactor) {
        var clipped = lRGB.multiply(1 / lRGB.length());
        var adjusted = new Vector3(
                Math.pow(clipped.x(), 1 / gammaAdjustmentFactor),
                Math.pow(clipped.y(), 1 / gammaAdjustmentFactor),
                Math.pow(clipped.z(), 1 / gammaAdjustmentFactor)
        );
        return adjusted.multiply(255);
    }
}