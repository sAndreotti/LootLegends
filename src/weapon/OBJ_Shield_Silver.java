package weapon;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Silver extends Entity{

    public OBJ_Shield_Silver(GamePanel gp) {
        super(gp);
        
        name = "Shield_Silver";
        type = typeShield;
        spriteDimX = 12;
        spriteDimY = 12;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        solidArea.x = spriteDimX;
        solidArea.y = spriteDimY;

        defenseValue = 3;

        getOBJImage("/weapon/shield_silver.png");
        description = name.replace("_", " ") + "\nAn upgraded shield";
    }
    
}
