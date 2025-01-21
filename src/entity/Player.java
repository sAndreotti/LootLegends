package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyH;
    boolean idle = true;
    public int character = 1;
    boolean attacking = false;
    int attackRange = 2*gp.scale;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        // Middle of screen for camera centering
        screenX = gp.screenWidth/2 - (spriteDim/2);
        screenY = gp.screenHeight/2 - (spriteDim/2);

        System.out.println("Preparing the player...");
        setDefaultValues();
        getImage("player", this.character);
        System.out.println(" ");

        // Collision
        solidArea = new Rectangle(10*gp.scale, 10*gp.scale, 12*gp.scale, 12*gp.scale);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Attack area
        attackArea.width = gp.tileSize+attackRange;
        attackArea.height = gp.tileSize+attackRange;

    }

    public void setDefaultValues() {
        //Start position
        worldX = gp.tileSize*5;
        worldY = gp.tileSize*7;
        speed = 4;
        direction = "right";

        // Player status
        maxLife = 6;
        life = maxLife;
    }

    public void updateSprite(int i){
        if(i != this.character){
            this.character = i;

            // Clearing not used images
            up = new ArrayList<>();
            down = new ArrayList<>();
            left = new ArrayList<>();
            right = new ArrayList<>();
            getImage("player", this.character);
        }
    }

    public void update() {
        // Attack
        if(keyH.jPressed){
            attacking = true;
            //gp.playSE(7);
            gp.keyH.jPressed = false;
            spriteNum = 10;
        }

        if(attacking){
            attacking();
        }else{
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

            // Check Monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Check event
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

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

            // Invincible counter
            if(invincible) {
                invincibleCounter++;
                if(invincibleCounter > gp.FPS*2){
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }

    }

    public void attacking() {
        spriteCounter++;
        if(spriteCounter>8){
            // Sprite Attack
            if(spriteNum < 13){
                spriteNum++;
            } else {
                // Check if hit something
                int currentWorldX = worldX;
                int currentWorldY = worldY;
                int solidAreaWidth = solidArea.width;
                int solidAreaHeight = solidArea.height;

                switch (direction){
                    case "up": worldY -= attackArea.height;
                    case "down": worldY += attackArea.height;
                    case "left": worldX -= attackArea.width;
                    case "right": worldX += attackArea.width;
                }

                solidArea.width = attackArea.width;
                solidArea.height = attackArea.height;

                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                damageMonster(monsterIndex);

                worldX = currentWorldX;
                worldY = currentWorldY;
                solidArea.width = solidAreaWidth;
                solidArea.height = solidAreaHeight;

                // End attack
                idle = true;
                spriteCounter = 0;
                attacking = false;
                return;
            }
            spriteCounter = 0;
        }
    }

    public void damageMonster(int i){
        if(i != -1) {
            if(!gp.monster[i].invincible){
                //gp.playSE(5);
                // Monster damaged
                gp.monster[i].life--;
                gp.monster[i].invincible = true;

                // React to damage
                gp.monster[i].damageReaction();

                // Hurted sprite
                gp.monster[i].hurt = true;
                gp.monster[i].spriteNum = 22;
                gp.monster[i].spriteCounter = 0;

                // HP bar show
                gp.monster[i].hpBarOn = true;
                gp.monster[i].hpBarCounter = 0;

                // If it dies
                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                    gp.monster[i].hurt = false;
                    gp.monster[i].spriteNum = 14;
                    gp.monster[i].hpBarOn = false;
                }
            }
        }
    }

    public void pickUpObject(int i) {
        if(i != -1){

        }
    }

    public void interactNPC(int i) {
        if(i != -1 && gp.keyH.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }

    public void speak(){
        // For specific iterations
        super.speak();
    }

    public void contactMonster(int i) {
        if(i!= -1){
            if(!invincible){
                //gp.playSE(6);
                gp.monster[i].attack();
                life--;
                gp.player.spriteNum = 22;
                gp.player.spriteCounter = 0;
                gp.player.hurt = true;
                invincible = true;

                if(gp.player.life <= 0){
                    gp.player.dying = true;
                    gp.player.hurt = false;
                    gp.player.spriteNum = 14;
                }
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

        if(hurt){
            hpBarOn = true;
            hpBarCounter = 0;
            System.out.println(hpBarOn);
            super.hurtAnimation();
        }

        if(dying){
            super.dyingAnimation();
        }

        if(!alive) {
            gp.gameState = gp.pauseState;
            return;
        }

        // Invincible transparency
        if(invincible && invincibleCounter%5==0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        // Shadow has fixed dimesions
        g2.drawImage(shadow, screenX+(spriteDim/2)+10, screenY+spriteDim+20, null);
        g2.drawImage(image, screenX, screenY, null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));


    }
}