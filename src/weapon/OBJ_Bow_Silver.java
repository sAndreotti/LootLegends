package weapon;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow_Silver extends Entity{
    
    public OBJ_Bow_Silver(GamePanel gp){
        super(gp);

        name = "Bow_Silver";
        type = typeSword;
        spriteDimX = 12;
        spriteDimY = 12;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        attackValue = 3;
        attackArea.width = gp.tileSize + gp.tileSize/3;
        attackArea.height = gp.tileSize + gp.tileSize/3;

        getOBJImage("/weapon/bow_silver.png");
        description = name.replace("_", " ") + "\nA silver bow";
    }

}
