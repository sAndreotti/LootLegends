package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Goblin_Sword extends Entity{

    public MON_Goblin_Sword(GamePanel gp){
        super(gp);

        name = "Goblin_Sword";
        speed = 0; // 2
        maxLife = 10;
        life = maxLife;
        type = typeMonster;
        attacking = false;

        // Set stats
        attack = 6;
        defense = 4;
        exp = 15;

        solidArea.x = 3*gp.scale;
        solidArea.y = 3*gp.scale;
        solidArea.width = 14*gp.scale;
        solidArea.height = 15*gp.scale;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage("enemies", 3);
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
