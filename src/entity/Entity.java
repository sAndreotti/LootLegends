package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public ArrayList<BufferedImage> up = new ArrayList<>();
    public ArrayList<BufferedImage> down = new ArrayList<>();
    public ArrayList<BufferedImage> left = new ArrayList<>();
    public ArrayList<BufferedImage> right = new ArrayList<>();
    public String direction;
    BufferedImage shadow;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int spriteDim = 32;

    // Collision
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
