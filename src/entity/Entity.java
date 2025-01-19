package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {
    public int worldX, worldY;
    public int speed;
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();

    // Dialogues
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    // Sprites
    public ArrayList<BufferedImage> up = new ArrayList<>();
    public ArrayList<BufferedImage> down = new ArrayList<>();
    public ArrayList<BufferedImage> left = new ArrayList<>();
    public ArrayList<BufferedImage> right = new ArrayList<>();
    public String direction;
    BufferedImage shadow;

    // Sprite change
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int spriteDim = 32;
    public int actionLockCounter = 0;

    // Collision
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean idle = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() { }

    public void speak() {
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // NPC look at the player
        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "right" -> direction = "left";
            case "left" -> direction = "right";
        }
    }

    public void getImage(String entity, int character) {
        // 0 -> 5 Movement, 6 -> 9 Idle
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Idle.png", false,4, spriteDim, gp.scale);

        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Idle.png", false, 4, spriteDim, gp.scale);

        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Idle.png", false, 4, spriteDim, gp.scale);

        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Walk.png", true, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Idle.png", true, 4, spriteDim, gp.scale);

        try{
            shadow = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/npc/Other/shadow.png"))), 13*gp.scale, 6*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

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

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // If the tile is visible draw the tile
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) {

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
}
