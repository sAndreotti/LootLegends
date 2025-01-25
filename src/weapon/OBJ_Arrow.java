package weapon;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Arrow extends Projectile{

    public OBJ_Arrow(GamePanel gp) {
        super(gp);

        name = "Arrow";

        // Set the object type
        speed = 5;
        maxLife = 50;
        life = maxLife;

        // Attack value
        attack = 0;
        // 0 mana to use arrow
        useCost = 0;
        alive = false;

        spriteDimX = 10;
        spriteDimY = 3;
        right_image = loadImage("/player/Other/Arrow.png");
        left_image = loadImage("/player/Other/Arrow_left.png");

        spriteDimX = 3;
        spriteDimY = 10;
        up_image = loadImage("/player/Other/Arrow_up.png");
        down_image = loadImage("/player/Other/Arrow_down.png");

    }

    public void getSolidArea(String direction) {
        if (direction.equals("right") || direction.equals("left")) {
            spriteDimX = 10;
            spriteDimY = 3;

        } else if (direction.equals("up") || direction.equals("down")) {
            spriteDimX = 3;
            spriteDimY = 10;

        }

        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)*gp.scale;

        solidArea.x = gp.tileSize/2;
        solidArea.y = gp.tileSize/2;
        solidArea.width = spriteDimX;
        solidArea.height = spriteDimY;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
