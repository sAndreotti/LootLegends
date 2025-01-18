package entity;

import jdk.jshell.execution.Util;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Door;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    UtilityTool uTool = new UtilityTool();
    boolean idle = true;
    public int character = 3;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        // Middle of screen for camera centering
        screenX = gp.screenWidth/2 - (spriteDim/2);
        screenY = gp.screenHeight/2 - (spriteDim/2);

        setDefaultValues();
        getPlayerImage();

        // Collision
        solidArea = new Rectangle(35, 40, 25, 20);
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
        loadSprite(up, "/player/"+character+"/U_Walk.png", false, 6);
        loadSprite(up, "/player/"+character+"/U_Idle.png", false,4);

        loadSprite(down, "/player/"+character+"/D_Walk.png", false, 6);
        loadSprite(down, "/player/"+character+"/D_Idle.png", false, 4);

        loadSprite(left, "/player/"+character+"/S_Walk.png", false, 6);
        loadSprite(left, "/player/"+character+"/S_Idle.png", false, 4);

        loadSprite(right, "/player/"+character+"/S_Walk.png", true, 6);
        loadSprite(right, "/player/"+character+"/S_Idle.png", true, 4);

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

    private void loadSprite(ArrayList<BufferedImage> arr, String spriteLocation, boolean reverse, int size){
        try {
            BufferedImage playerSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(spriteLocation)));

            if(reverse) {
                int width = playerSprite.getWidth();
                int height = playerSprite.getHeight();

                // Create a temporary array to hold one row of pixels
                int[] rowPixels = new int[width];

                // Iterate over each row
                for (int y = 0; y < height; y++) {
                    // Get the pixel data for the current row
                    playerSprite.getRGB(0, y, width, 1, rowPixels, 0, width);

                    // Reverse the row (flip horizontally)
                    for (int x = 0; x < width / 2; x++) {
                        int temp = rowPixels[x];
                        rowPixels[x] = rowPixels[width - 1 - x];
                        rowPixels[width - 1 - x] = temp;
                    }

                    // Write the flipped row back to the image
                    playerSprite.setRGB(0, y, width, 1, rowPixels, 0, width);
                }
            }

            for (int i=0; i<size; i++){
                BufferedImage img = playerSprite.getSubimage(32*i, 0, spriteDim, spriteDim);
                arr.add(uTool.scaleImage(img,spriteDim*gp.scale, spriteDim*gp.scale));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a Key!");
                    break;

                case "Door":
                    if(hasKey >0) {
                        gp.playSE(3);
                        // Should be a cast for the class
                        gp.obj[i].open();
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;

                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.obj[i].open();
                    gp.stopMusic();
                    gp.playSE(4);
                    break;

                case "Book":
                    gp.playSE(2);
                    gp.ui.showMessage("You got an upgrade!");
                    speed += 2;
                    gp.obj[i] = null;
                    break;
            }

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
        //g2.drawImage(shadow, screenX+(spriteDim/2)+10, screenY+spriteDim+20, null);
        g2.drawImage(image, screenX, screenY, null);
    }
}
