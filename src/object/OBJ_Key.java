package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{

    public OBJ_Key(GamePanel gp) {

        name = "Key";
        spriteDimX = 8;
        spriteDimY = 5;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        solidArea.x = spriteDimX;
        solidArea.y = spriteDimY;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Other/15.png")));
            image = uTool.scaleImage(image, spriteDimX*gp.scale, spriteDimY*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
