package weapon;

import entity.Entity;
import main.GamePanel;

public class OBJ_Wand_Normal extends Entity{

    public OBJ_Wand_Normal(GamePanel gp) {
        super(gp);

        name = "Wand_Normal";
        type = typeSword;
        spriteDimX = 12;
        spriteDimY = 12;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        attackValue = 1;
        attackArea.width = gp.tileSize + gp.tileSize/4;
        attackArea.height = gp.tileSize + gp.tileSize/4;

        getOBJImage("/weapon/wand_normal.png");
        description = name.replace("_", " ") + "\nA normal wand";
    }


}
