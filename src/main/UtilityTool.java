package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        // Efficient scaling
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public BufferedImage loadTileSprite(String spriteLocation){
        try {
            BufferedImage sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(spriteLocation)));
            return sprite;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadEntitySprite(ArrayList<BufferedImage> arr, String spriteLocation, boolean reverse, int size, int spriteDim, int scale){
        try {
            BufferedImage playerSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(spriteLocation)));

            for (int i=0; i<size; i++){
                BufferedImage img = playerSprite.getSubimage(32*i, 0, spriteDim, spriteDim);

                // Reverse for right moves
                if(reverse) {
                    int width = img.getWidth();
                    int height = img.getHeight();

                    // Create a temporary array to hold one row of pixels
                    int[] rowPixels = new int[width];

                    // Iterate over each row
                    for (int y = 0; y < height; y++) {
                        // Get the pixel data for the current row
                        img.getRGB(0, y, width, 1, rowPixels, 0, width);

                        // Reverse the row (flip horizontally)
                        for (int x = 0; x < width / 2; x++) {
                            int temp = rowPixels[x];
                            rowPixels[x] = rowPixels[width - 1 - x];
                            rowPixels[width - 1 - x] = temp;
                        }

                        // Write the flipped row back to the image
                        img.setRGB(0, y, width, 1, rowPixels, 0, width);
                    }
                }

                arr.add(scaleImage(img,spriteDim*scale, spriteDim*scale));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
