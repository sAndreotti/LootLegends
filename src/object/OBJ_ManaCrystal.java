package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_ManaCrystal extends Entity {

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);

        name = "Mana Crystal";
        spriteDimX = 32; //16
        spriteDimY = 32; // 16
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;


        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/BarTileMana1.png")));
            image = uTool.scaleImage(image, (spriteDimX*gp.scale)/2, (spriteDimY*gp.scale)/2);

            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/BarTileMana2.png")));
            image2 = uTool.scaleImage(image2, (spriteDimX*gp.scale)/2, (spriteDimY*gp.scale)/2);

            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/BarTileMana3.png")));
            image3 = uTool.scaleImage(image3, (spriteDimX*gp.scale)/2, (spriteDimY*gp.scale)/2);

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
