package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        name = "Shield_Normal";
        spriteDimX = 8;
        spriteDimY = 5;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        solidArea.x = spriteDimX;
        solidArea.y = spriteDimY;

        defenseValue = 1;

        getOBJImage("/weapon/shield_normal.png");
        //getOBJImage("/objects/Bookshelf decor/15.png");
    }

}
