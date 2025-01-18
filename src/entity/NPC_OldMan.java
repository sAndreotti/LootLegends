package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPC_OldMan extends Entity{

    UtilityTool uTool = new UtilityTool();
    public int character = 2;

    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle(35, 40, 25, 25);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getNPCImage();
    }

    public void getNPCImage() {
        // 0 -> 5 Movement, 6 -> 9 Idle
        uTool.loadEntitySprite(up, "/npc/"+character+"/U_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/npc/"+character+"/U_Idle.png", false,4, spriteDim, gp.scale);

        uTool.loadEntitySprite(down, "/npc/"+character+"/D_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/npc/"+character+"/D_Idle.png", false, 4, spriteDim, gp.scale);

        uTool.loadEntitySprite(left, "/npc/"+character+"/S_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/npc/"+character+"/S_Idle.png", false, 4, spriteDim, gp.scale);

        uTool.loadEntitySprite(right, "/npc/"+character+"/S_Walk.png", true, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/npc/"+character+"/S_Idle.png", true, 4, spriteDim, gp.scale);

        try{
            shadow = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/npc/Other/shadow.png"))), 13*gp.scale, 6*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == gp.FPS*2){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i<=25) {
                direction = "up";
            } else if(i<= 50) {
                direction = "down";
            } else if(i<=75) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
        }

    }
}
