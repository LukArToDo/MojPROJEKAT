package company.Main.Support.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WhiteToTransparent {

	public static BufferedImage changeToTransparent(File in) throws IOException{
    final BufferedImage source = ImageIO.read(in);

    final int color = source.getRGB(0, 0);

    final Image imageWithTransparency = makeColorTransparent(source, new Color(color));

    final BufferedImage transparentImage = imageToBufferedImage(imageWithTransparency);
    
    // next 2 line change in future, replace to another class where from picture are
  /*  final File out = new File("C:/Users/PC/Desktop/ProbaTransparent.jpg");
    ImageIO.write(transparentImage, "PNG", out);*/
	
    return transparentImage;
 }

 /**
  * Convert Image to BufferedImage.
  *
  * @param image Image to be converted to BufferedImage.
  * @return BufferedImage corresponding to provided Image.
  */
 private static BufferedImage imageToBufferedImage(final Image image)
 {
    final BufferedImage bufferedImage =
       new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    final Graphics2D g2 = bufferedImage.createGraphics();
    g2.drawImage(image, 0, 0, null);
    g2.dispose();
    return bufferedImage;
  }

 /**
  * Make provided image transparent wherever color matches the provided color.
  *
  * @param im BufferedImage whose color will be made transparent.
  * @param color Color in provided image which will be made transparent.
  * @return Image with transparency applied.
  */
 public static Image makeColorTransparent(final BufferedImage im, final Color color)
 {
    final ImageFilter filter = new RGBImageFilter()
    {
       // the color we are looking for (white)... Alpha bits are set to opaque
       public int markerRGB = color.getRGB() | 0xFFFFFFFF;

       public final int filterRGB(final int x, final int y, final int rgb)
       {
          if ((rgb | 0xFF000000) == markerRGB)
          {
             // Mark the alpha bits as zero - transparent
             return 0x00FFFFFF & rgb;
          }
          else
          {
             // nothing to do
             return rgb;
          }
       }
    };

    final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
    return Toolkit.getDefaultToolkit().createImage(ip);
 }

	
}
