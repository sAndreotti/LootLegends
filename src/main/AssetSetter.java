package main;

import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // Keys
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 10*gp.tileSize;
        gp.obj[0].worldY = 5*gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 3*gp.tileSize;
        gp.obj[1].worldY = 7*gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 4*gp.tileSize;
        gp.obj[2].worldY = 6*gp.tileSize;

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


    }

}
