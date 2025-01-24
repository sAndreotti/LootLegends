package main;

import entity.NPC_OldMan;
import monster.MON_Goblin;
import monster.MON_Rat;
import object.OBJ_Key;
import object.OBJ_PotionRed;
import object.OBJ_Shield_Silver;
import object.OBJ_Sword_Black;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        
        // Keys
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 2*gp.tileSize;
        gp.obj[0].worldY = 12*gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 11*gp.tileSize;
        gp.obj[1].worldY = 12*gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 11*gp.tileSize;
        gp.obj[2].worldY = 5*gp.tileSize;

        gp.obj[3] = new OBJ_Sword_Black(gp);
        gp.obj[3].worldX = 5*gp.tileSize;
        gp.obj[3].worldY = 5*gp.tileSize;

        gp.obj[4] = new OBJ_Shield_Silver(gp);
        gp.obj[4].worldX = 6*gp.tileSize;
        gp.obj[4].worldY = 5*gp.tileSize;

        gp.obj[5] = new OBJ_PotionRed(gp);
        gp.obj[5].worldX = 7*gp.tileSize;
        gp.obj[5].worldY = 5*gp.tileSize;

        /* 
        // Doors
        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 20*gp.tileSize;
        gp.obj[3].worldY = 41*gp.tileSize;

        gp.obj[4] = new OBJ_DoubleDoor(gp);
        gp.obj[4].worldX = 30*gp.tileSize;
        gp.obj[4].worldY = 41*gp.tileSize;

        gp.obj[5] = new OBJ_DoubleDoor(gp);
        gp.obj[5].worldX = 40*gp.tileSize;
        gp.obj[5].worldY = 41*gp.tileSize;

        // Chest
        gp.obj[6] = new OBJ_Chest(gp, "down");
        gp.obj[6].worldX = 26*gp.tileSize;
        gp.obj[6].worldY = 5*gp.tileSize;

        // Boots
        gp.obj[7] = new OBJ_Book(gp);
        gp.obj[7].worldX = 10*gp.tileSize;
        gp.obj[7].worldY = 10*gp.tileSize;
        */

    }

    public void setNpc() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*26;
        gp.npc[0].worldY = gp.tileSize*4;
        gp.npc[0].direction = "right";

    }

    public void setMonster() {
        gp.monster[0] = new MON_Rat(gp);
        gp.monster[0].worldX = gp.tileSize*17;
        gp.monster[0].worldY = gp.tileSize*10;
        gp.monster[0].direction = "right";

        gp.monster[1] = new MON_Goblin(gp);
        gp.monster[1].worldX = gp.tileSize*17;
        gp.monster[1].worldY = gp.tileSize*5;
    }

}
