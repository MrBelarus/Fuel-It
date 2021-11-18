package FuelIt.Utils;

import FuelIt.Application;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Util class for image scaling
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class ImageScaller {
    public static Image scaleImage(String imagePath,
                                   Dimension newDimension,
                                   int hints){
        BufferedImage bufferedImage = null;
        try{
            bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage.getScaledInstance(newDimension.width, newDimension.height, Image.SCALE_SMOOTH);
    }
}
