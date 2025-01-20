package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_Rat extends Entity {

    public MON_Rat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rat";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        type = 2;
        attack = false;

        /*
        solidArea.x = 10*gp.scale;
        solidArea.y = 10*gp.scale;
        solidArea.width = 12*gp.scale;
        solidArea.height = 12*gp.scale;
        */

        solidArea.x = 8*gp.scale;
        solidArea.y = 8*gp.scale;
        solidArea.width = 14*gp.scale;
        solidArea.height = 14*gp.scale;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage("enemies", 1);
    }

    public void setAction() {
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

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void attack() {
        // Set first sprite for attack
        spriteCounter = 10;
        gp.player.attack = true;
    }

}
