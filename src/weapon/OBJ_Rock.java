package weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{

    public OBJ_Rock(GamePanel gp) {
        super(gp);

        name = "Fireball";

        // Set the object type
        speed = 7;
        maxLife = 80;
        life = maxLife;

        // Attack value
        attack = 2;
        // 0 mana to use fireballs (for mage)
        useCost = 1;
        alive = false;

        spriteDimX = 4;
        spriteDimY = 4;
        right_image = loadImage("/player/Other/Fireball.png");
        left_image = right_image;
        up_image = right_image;
        down_image = right_image;
    }

    public void getSolidArea(String direction) {
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

    public boolean haveResource(Entity user) {
        return user.ammo >= useCost;
    }

    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }
}
