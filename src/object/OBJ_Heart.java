package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {
        super(gp);

        name = "Heart";
        spriteDimX = 16; //16
        spriteDimY = 16; // 16
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;


        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/heart_full.png")));
            image = uTool.scaleImage(image, (spriteDimX*gp.scale)/2, (spriteDimY*gp.scale)/2);

            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/heart_half.png")));
            image2 = uTool.scaleImage(image2, (spriteDimX*gp.scale)/2, (spriteDimY*gp.scale)/2);

            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/heart_empty.png")));
            image3 = uTool.scaleImage(image, (spriteDimX*gp.scale)/2, (spriteDimY*gp.scale)/2);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
