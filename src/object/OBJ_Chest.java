package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class OBJ_Chest extends SuperObject{

    public ArrayList<BufferedImage> images = new ArrayList<>();
    public String facing;
    String path = "";
    boolean reverse = false;
    int scale;

    int spriteCounter = 0;

    public OBJ_Chest(GamePanel gp, String facing) {
        name = "Chest";
        spriteDimX = 16;
        spriteDimY = 24;
        collision = true;
        this.facing = facing;
        this.scale = gp.scale;

        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        switch (facing) {
            case "up" -> path = "/objects/Animated/Chest2_U.png";
            case "down" -> path = "/objects/Animated/Chest2_D.png";
            case "left" -> path = "/objects/Animated/Chest2_S.png";
            case "right" -> {
                path = "/objects/Animated/Chest2_S.png";
                reverse = true;
            }
        }

        loadSprite(images, path, reverse);
        image = images.get(0);
    }

    private void loadSprite(ArrayList<BufferedImage> arr, String spriteLocation, boolean reverse){
        try {
            BufferedImage playerSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(spriteLocation)));

            if(reverse) {
                int width = playerSprite.getWidth();
                int height = playerSprite.getHeight();

                // Create a temporary array to hold one row of pixels
                int[] rowPixels = new int[width];

                // Iterate over each row
                for (int y = 0; y < height; y++) {
                    // Get the pixel data for the current row
                    playerSprite.getRGB(0, y, width, 1, rowPixels, 0, width);

                    // Reverse the row (flip horizontally)
                    for (int x = 0; x < width / 2; x++) {
                        int temp = rowPixels[x];
                        rowPixels[x] = rowPixels[width - 1 - x];
                        rowPixels[width - 1 - x] = temp;
                    }

                    // Write the flipped row back to the image
                    playerSprite.setRGB(0, y, width, 1, rowPixels, 0, width);
                }
            }

            for (int i=0; i<4; i++){
                arr.add(uTool.scaleImage(playerSprite.getSubimage(spriteDimX*i, 0, spriteDimX, spriteDimY),
                        spriteDimX*scale ,spriteDimX*scale));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        image = images.get(2);
    }
}
