package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Book extends SuperObject{

    // Book makes player faster
    public OBJ_Book(GamePanel gp) {

        name = "Book";
        spriteDimX = 7;
        spriteDimY = 8;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        solidArea.x = spriteDimX;
        solidArea.y = spriteDimY;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Bookshelf decor/15.png")));
            image = uTool.scaleImage(image, spriteDimX*gp.scale, spriteDimY*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
