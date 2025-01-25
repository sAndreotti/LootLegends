package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_Goblin extends Entity {

    int defaultX;
    int defaultY;

    public MON_Goblin(GamePanel gp, int x, int y) {
        super(gp);
        this.gp = gp;

        name = "Goblin";
        speed = 0; // 1
        maxLife = 8;
        life = maxLife;
        type = typeMonster;
        attacking = false;

        // Set stats
        attack = 4;
        defense = 2;
        exp = 10;

        solidArea.x = 3*gp.scale;
        solidArea.y = 3*gp.scale;
        solidArea.width = 14*gp.scale;
        solidArea.height = 15*gp.scale;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage("enemies", 2);
        // Set default position
        defaultX = x;
        defaultY = y;

    }

    public void setAction() {
        upAndDown();
    }

    public void passive() {
        actionLockCounter++;

        if(actionLockCounter == gp.FPS*2){
            Random random = new Random();
            int i = random.nextInt(100)+1;
            super.idle = false;

            if(i<=20) {
                direction = "up";
            } else if(i<= 40) {
                direction = "down";
            } else if(i<=60) {
                direction = "left";
            } else if(i<=80) {
                direction = "right";
            } else {
                super.idle = true;
            }

            actionLockCounter = 0;
        }
    }

    public void upAndDown() {
        if(worldY/gp.tileSize - defaultY == 3) {
            direction = "up";
        } else if(worldY/gp.tileSize - defaultY == -3) {
            direction = "down";
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void attack() {
        // Set first sprite for attack
        spriteCounter = 10;
        gp.player.attacking = true;
    }

}
