package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {

        this.gp = gp;

        name = "Heart";
        spriteDimX = 10;
        spriteDimY = 10;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;


        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/heart_full.png")));
            image = uTool.scaleImage(image, spriteDimX*gp.scale, spriteDimY*gp.scale);

            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/heart_half.png")));
            image2 = uTool.scaleImage(image2, spriteDimX*gp.scale, spriteDimY*gp.scale);

            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/heart_empty.png")));
            image3 = uTool.scaleImage(image3, spriteDimX*gp.scale, spriteDimY*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
