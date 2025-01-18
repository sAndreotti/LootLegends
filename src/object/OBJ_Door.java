package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject{

    BufferedImage imageOpened;

    public OBJ_Door(GamePanel gp) {
        name = "Door";
        spriteDimX = 16;
        spriteDimY = 12;
        collision = true;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Doors/1.png")));
            image = uTool.scaleImage(image, spriteDimX*gp.scale, spriteDimY*gp.scale);

            imageOpened = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Doors/2.png")));
            imageOpened = uTool.scaleImage(imageOpened, spriteDimX*gp.scale, spriteDimY*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        spriteDimX = 7;
        spriteDimY = 24;

        diffX = -10;
        diffY = -10;
        collision = false;
        solidArea = null;

        image = imageOpened;
    }
}
