package entity;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NPC_OldMan extends Entity{

    public int character = 2;

    public NPC_OldMan(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;

        //35, 40, 25, 25
        //35, 7, 25, 64
        solidArea = new Rectangle(12*gp.scale, 13*gp.scale, 9*gp.scale, 9*gp.scale);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage("npc", character);
        setDialogue();
    }

    // Here because maybe you want different NPC for different players
    public void updateSprite(int i){
        if(i != this.character){
            this.character = i;

            // Clearing not used images
            up = new ArrayList<>();
            down = new ArrayList<>();
            left = new ArrayList<>();
            right = new ArrayList<>();
            getImage("npc", character);
        }
    }

    public void setDialogue() {
        dialogues[0] = "Hello adventurer.";
        dialogues[1] = "So you've come to this dungeon\nfor the treasure?";
        dialogues[2] = "I was a great adventurer..\nbefore an arrow hit my knee.";
        dialogues[3] = "Well, good luck\n and keep an one on arrows.";

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
