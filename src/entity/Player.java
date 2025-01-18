package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    UtilityTool uTool = new UtilityTool();
    boolean idle = true;
    public int character = 3;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        // Middle of screen for camera centering
        screenX = gp.screenWidth/2 - (spriteDim/2);
        screenY = gp.screenHeight/2 - (spriteDim/2);

        setDefaultValues();
        getPlayerImage();

        // Collision
        solidArea = new Rectangle(30, 30, 35, 35);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues() {
        //Start position
        worldX = gp.tileSize*5;
        worldY = gp.tileSize*7;

        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {
        // 0 -> 5 Movement, 6 -> 9 Idle
        uTool.loadEntitySprite(up, "/player/"+character+"/U_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/player/"+character+"/U_Idle.png", false,4, spriteDim, gp.scale);

        uTool.loadEntitySprite(down, "/player/"+character+"/D_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/player/"+character+"/D_Idle.png", false, 4, spriteDim, gp.scale);

        uTool.loadEntitySprite(left, "/player/"+character+"/S_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/player/"+character+"/S_Idle.png", false, 4, spriteDim, gp.scale);

        uTool.loadEntitySprite(right, "/player/"+character+"/S_Walk.png", true, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/player/"+character+"/S_Idle.png", true, 4, spriteDim, gp.scale);

        try{
            shadow = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/Other/shadow.png"))), 13*gp.scale, 6*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Up sprites dim: " + up.size());
        System.out.println("Down sprites dim: " + down.size());
        System.out.println("Left sprites dim: " + left.size());
        System.out.println("Right sprites dim: " + right.size());
    }

    public void update() {
        if(keyH.upPressed){
            direction = "up";
            idle = false;
        }else if(keyH.downPressed){
            direction = "down";
            idle = false;
        }else if(keyH.leftPressed){
            direction = "left";
            idle = false;
        }else if(keyH.rightPressed){
            direction = "right";
            idle = false;
        }else {
            idle = true;
        }

        // Collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // Check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // Check NPC collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        // Moving
        if(!collisionOn && !idle) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if(spriteCounter>12){
            if (idle){
                // Idle
                if(spriteNum < 6){
                    spriteNum = 6;
                }

                if(spriteNum < 9){
                    spriteNum++;
                } else {
                    spriteNum = 6;
                }
            }else{
                // Movement
                if(spriteNum < 5){
                    spriteNum++;
                } else {
                    spriteNum = 1;
                }
            }
            spriteCounter = 0;
        }

    }

    public void pickUpObject(int i) {
        if(i != -1){

        }
    }

    public void interactNPC(int i) {
        if(i != -1) {

        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> up.get(spriteNum);
            case "down" -> down.get(spriteNum);
            case "right" -> right.get(spriteNum);
            case "left" -> left.get(spriteNum);
            default -> null;
        };

        // Shadow has fixed dimesions
        g2.drawImage(shadow, screenX+(spriteDim/2)+10, screenY+spriteDim+20, null);
        g2.drawImage(image, screenX, screenY, null);
    }
}
