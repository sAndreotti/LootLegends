package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_DoubleDoor extends SuperObject{

    BufferedImage imageOpened;

    public OBJ_DoubleDoor(GamePanel gp) {
        name = "Door";
        spriteDimX = 33;
        spriteDimY = 12;
        collision = true;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;
        diffX += 22;

        solidArea.width = 2*gp.tileSize;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Doors/3.png")));
            image = uTool.scaleImage(image, spriteDimX*gp.scale, spriteDimY*gp.scale);

            imageOpened = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Doors/4.png")));
            imageOpened = uTool.scaleImage(imageOpened, spriteDimX*gp.scale, spriteDimY*gp.scale);
        } catch (
                IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        spriteDimX = 7;
        spriteDimY = 40;

        diffX = -10;
        diffY = -10;
        collision = false;
        solidArea = null;

        image = imageOpened;
    }
}
