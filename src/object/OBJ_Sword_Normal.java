package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{

    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        name = "Sword_Normal";
        spriteDimX = 8;
        spriteDimY = 5;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        solidArea.x = spriteDimX;
        solidArea.y = spriteDimY;

        attackValue = 1;

        //getOBJImage("res/weapons/sword_normal.png");
        getOBJImage("/objects/Other/15.png");
    }

}
