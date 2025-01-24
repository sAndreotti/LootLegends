package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Arrow extends Projectile{

    public OBJ_Arrow(GamePanel gp) {
        super(gp);

        name = "Arrow";
        spriteDimX = 10;
        spriteDimY = 3;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        // Set the object type
        speed = 6;
        maxLife = 80;
        life = maxLife;

        // Attack value
        attack = 2;
        // 0 mana to use arrow
        useCost = 0;
        alive = false;

        // TODO Images for directions and solid area for directions
        getOBJImage("/player/Other/Arrow.png");

    }

}
