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
    public GamePanel gp;
    protected UtilityTool uTool = new UtilityTool();

    // Dialogues
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    // Sprites
    public ArrayList<BufferedImage> up = new ArrayList<>();
    public ArrayList<BufferedImage> down = new ArrayList<>();
    public ArrayList<BufferedImage> left = new ArrayList<>();
    public ArrayList<BufferedImage> right = new ArrayList<>();
    public String direction = "down";
    public BufferedImage shadow;

    // Sprite change
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int spriteDim = 32;
    public int actionLockCounter = 0;

    // Invincible
    public boolean invincible = false;
    public int invincibleCounter = 0;

    // Collision
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean idle = false;

    // Atttack
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean attacking;

    // Character status
    public int maxLife;
    public int life;
    public boolean hurt = false;
    public boolean hpBarOn = false;
    public int hpBarCounter = 0;

    // Stats
    public int level;
    public int strenght;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;

    public Entity currentWeapon;
    public Entity currentShield;

    // Item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";

    // Dying
    public boolean alive = true;
    public boolean dying = false;

    // Types
    public int type; // 0 = player, 1 = npc, 2 = monsters
    public final int typePlayer = 0;
    public final int typeNPC = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeShield = 4;
    public final int typeConsumable = 5;

    // Objects
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;    public int spriteDimX;
    public int spriteDimY;
    public int diffX = 0;
    public int diffY = 0;

    // Mana
    public int maxMana;
    public int mana;
    public Projectile projectile;
    public int useCost;
    public int shotAvaibleCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() { }
    public void use(Entity entity) { }
    public void damageReaction() { }
    public void attack() { }

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
        // 0 -> 5 Movement, 6 -> 9 Idle, 10 -> 13 Attack, 14 -> 21 Diyng, 22 -> 23 Hurt
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Idle.png", false,4, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Attack.png", false,4, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Death.png", false,8, spriteDim, gp.scale);
        uTool.loadEntitySprite(up, "/"+entity+"/"+character+"/U_Hurt.png", false,2, spriteDim, gp.scale);

        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Idle.png", false, 4, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Attack.png", false,4, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Death.png", false,8, spriteDim, gp.scale);
        uTool.loadEntitySprite(down, "/"+entity+"/"+character+"/D_Hurt.png", false,2, spriteDim, gp.scale);

        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Walk.png", false, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Idle.png", false, 4, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Attack.png", false,4, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Death.png", false,8, spriteDim, gp.scale);
        uTool.loadEntitySprite(left, "/"+entity+"/"+character+"/S_Hurt.png", false,2, spriteDim, gp.scale);

        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Walk.png", true, 6, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Idle.png", true, 4, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Attack.png", true,4, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Death.png", true,8, spriteDim, gp.scale);
        uTool.loadEntitySprite(right, "/"+entity+"/"+character+"/S_Hurt.png", true,2, spriteDim, gp.scale);

        try{
            shadow = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/npc/Other/shadow.png"))), 13*gp.scale, 6*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Loaded " + up.size() + " images per direction");
    }

    public void getOBJImage(String path) {
        try{
            image = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream(path))), spriteDimX*gp.scale, spriteDimY*gp.scale);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void hurtAnimation() {
        spriteCounter++;
        if(spriteCounter>50){
            // Sprite Attack
            if(spriteNum < 23){
                spriteNum++;
                /*switch (direction) {
                    case "up" -> worldY += 5 * gp.scale;
                    case "down" -> worldY -= 5 * gp.scale;
                    case "left" -> worldX += 5 * gp.scale;
                    case "right" -> worldX -= 5 * gp.scale;
                }*/
            }
            spriteCounter = 0;
        }

        // End Hurt
        if(life>0 && spriteNum>=23){
            spriteNum = 0;
            hurt = false;
            invincible = true;
        } else if(life <= 0) {
            // Dying
            dying = true;
            hurt = false;
        }
    }

    public void dyingAnimation() {
        spriteCounter++;
        if(spriteCounter>8){
            // Sprite Attack
            if(spriteNum < 21){
                spriteNum++;
            } else {
                dying = false;
                alive = false;
            }
            spriteCounter = 0;
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        // If it is a monster and it is in contact with the player
        if(this.type == typeMonster && contactPlayer) {
            if(!gp.player.invincible) {
                gp.playSE(6);
                int damage = attack - gp.player.defense;
                if(damage < 0){
                    damage = 0;
                } else {
                    // If no damage no invincible
                    gp.player.invincible = true;
                }
                gp.player.life -= damage;
            }
        }

        // Moving
        if(!collisionOn && !idle) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        
        // If it is hurting i don't want upadte the sprite
        if(!hurt){
            spriteCounter++;
            if(spriteCounter>12){
                if(attacking) {
                    spriteNum++;
                    if(spriteNum > 13) {
                        spriteNum = 0;
                        attacking = false;
                    }
                } else {
                    if (idle) {
                        // Idle
                        if (spriteNum < 6) {
                            spriteNum = 6;
                        }

                        if (spriteNum < 9) {
                            spriteNum++;
                        } else {
                            spriteNum = 6;
                        }
                    } else {
                        // Movement
                        if (spriteNum < 5) {
                            spriteNum++;
                        } else {
                            spriteNum = 0;
                        }
                    }
                }
            spriteCounter = 0;
            }
        }

        // Invincible counter
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > gp.FPS/4){
                invincible = false;
                invincibleCounter = 0;
            }
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

            // Object
            if(up.isEmpty()) {
                g2.drawImage(image, screenX+diffX, screenY+diffY, null);
            } else {
                // NPC and Monsters
                BufferedImage image = switch (direction) {
                    case "up" -> up.get(spriteNum);
                    case "down" -> down.get(spriteNum);
                    case "right" -> right.get(spriteNum);
                    case "left" -> left.get(spriteNum);
                    default -> null;
                };

                // Health bar for monsters
                if(type == 2 && hpBarOn){
                    double oneScale = (double) gp.tileSize/maxLife;
                    double hpBarValue = oneScale*life;

                    g2.setColor(gp.ui.backgroundColor);
                    g2.fillRect(screenX-1+(gp.tileSize/2), screenY-16, gp.tileSize+2, 12);

                    g2.setColor(new Color(162, 38, 51));
                    g2.fillRect(screenX+(gp.tileSize/2), screenY-15, (int) hpBarValue, 10);

                    hpBarCounter++;
                    if(hpBarCounter > 10 * gp.FPS){
                        hpBarOn = false;
                        hpBarCounter = 0;
                    }
                }


                // Invincible transparency
                if(invincible && !dying) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
                }

                if(hurt && !dying){
                    hurtAnimation();
                }

                if(dying){
                    dyingAnimation();
                }

                // Shadow has fixed dimensions
                g2.drawImage(shadow, screenX+(spriteDim/2)+10, screenY+spriteDim+20, null);
                g2.drawImage(image, screenX, screenY, null);

                // Reset alpha
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
            }


        }

    }
}