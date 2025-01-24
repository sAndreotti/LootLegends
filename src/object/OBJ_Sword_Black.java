package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Black extends Entity{

    public OBJ_Sword_Black(GamePanel gp){
        super(gp);

        name = "Sword_Black";
        type = typeSword;
        spriteDimX = 12;
        spriteDimY = 12;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        attackValue = 3;
        attackArea.width = gp.tileSize + gp.tileSize/3;
        attackArea.height = gp.tileSize + gp.tileSize/3;

        getOBJImage("/weapon/sword_black.png");
        description = name.replace("_", " ") + "\nAn upgraded sword";
    }
}