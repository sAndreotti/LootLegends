package weapon;

import entity.Entity;
import main.GamePanel;

public class OBJ_Wand_Gold extends Entity{

    public OBJ_Wand_Gold(GamePanel gp) {
        super(gp);

        name = "Wand_Gold";
        type = typeSword;
        spriteDimX = 12;
        spriteDimY = 12;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        attackValue = 3;
        attackArea.width = gp.tileSize + gp.tileSize/4;
        attackArea.height = gp.tileSize + gp.tileSize/4;

        getOBJImage("/weapon/wand_gold.png");
        description = name.replace("_", " ") + "\nA golden wand";
    }

}
