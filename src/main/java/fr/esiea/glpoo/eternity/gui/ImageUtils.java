package fr.esiea.glpoo.eternity.gui;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public class ImageUtils {

  public final static ColorModel COLOR_MODEL_RGBA = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

  
  public static int getScreenImageType() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    ColorModel cm = gc.getColorModel();
    int imageType = cm.getColorSpace().getType();
    return imageType;
  }
  
  public static BufferedImage createBufferedImage(ColorModel colorModel, Dimension dim) {
    return createBufferedImage(colorModel, dim.width, dim.height);
  }

  public static BufferedImage createBufferedImage(ColorModel colorModel, int width, int height) {
    // Won't work between Indexed and RGB for example
    // int numComponents = colorModel.getNumComponents();
    // WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, numComponents, null);

    WritableRaster raster = colorModel.createCompatibleWritableRaster(width, height);
    BufferedImage bufferedImage = new BufferedImage(colorModel, raster, false, new Hashtable<>());
    return bufferedImage;
  }
}
