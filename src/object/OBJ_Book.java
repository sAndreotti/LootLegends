package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Book extends Entity {

    // Book makes player faster
    public OBJ_Book(GamePanel gp) {
        super(gp);

        name = "Book";
        spriteDimX = 7;
        spriteDimY = 8;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        solidArea.x = spriteDimX;
        solidArea.y = spriteDimY;

        getOBJImage("/objects/Bookshelf decor/15.png");
    }

}
