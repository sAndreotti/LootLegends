package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Goblin_Mage extends Entity{

    public MON_Goblin_Mage(GamePanel gp){
        super(gp);

        name = "Goblin_Mage";
        speed = 0; // 1
        maxLife = 8;
        life = maxLife;
        type = typeMonster;
        attacking = false;

        // Set stats
        attack = 8;
        defense = 2;
        exp = 20;

        solidArea.x = 3*gp.scale;
        solidArea.y = 3*gp.scale;
        solidArea.width = 14*gp.scale;
        solidArea.height = 15*gp.scale;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage("enemies", 4);
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

}
