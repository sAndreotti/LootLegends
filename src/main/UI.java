package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    UtilityTool uTool;
    Font vt323;
    public String currentDialogue = "";
    public int commandNum = 0;
    BufferedImage selector = null;
    // Class selection
    public int titleScreenState = 0;
    BufferedImage archer = null;
    BufferedImage knight = null;
    BufferedImage mage = null;

    public UI(GamePanel gp) {
        this.gp = gp;
        uTool  = new UtilityTool();

        try{
            InputStream is = getClass().getResourceAsStream("/font/VT323-Regular.ttf");
            assert is != null;
            vt323 = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

    }

    // Y in drawstring indicates the base of the string
    public void draw(Graphics2D g2) {
       this.g2 = g2;
       g2.setFont(vt323);
       g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g2.setColor(Color.white);

       if(gp.gameState == gp.titleState) {
           // Title
           loadClasses();
           drawTitleScreen();
       } else if(gp.gameState == gp.playState) {
           // Play

       } else if(gp.gameState == gp.pauseState) {
           // Pause
           drawPauseScreen();
       } else if(gp.gameState == gp.dialogueState) {
           // Dialogue
           drawDialogueScreen();
       }
    }

    public void loadClasses() {
        try{
            archer = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/1/static.png")));
            archer = uTool.scaleImage(archer, 32*(gp.scale+1), 32*gp.scale+1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            knight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/2/static.png")));
            knight = uTool.scaleImage(knight, 32*(gp.scale+1), 32*gp.scale);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            mage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/3/static.png")));
            mage = uTool.scaleImage(mage, 32*gp.scale, 32*gp.scale);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawTitleScreen() {
        // Window color
        g2.setColor(new Color(24, 20, 37));
        g2.drawRect(0, 0, gp.screenWidth, gp.screenHeight);
        if(titleScreenState == 0){

            // Title Background
            try{
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/title.png")));
                image = uTool.scaleImage(image, 194*gp.scale, 64*gp.scale);
                g2.drawImage(image, gp.tileSize*2, gp.tileSize/4, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Title
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 65F));
            String text = "Loot Legends";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*2 + (gp.tileSize/4);

            // Shadow
            g2.drawString(text, x+5, y+5);

            // Draw Title
            g2.setColor(new Color(254, 174, 52));
            g2.drawString(text, x, y);

            // Player image
            x = gp.screenWidth/2 - (gp.tileSize*2);
            y += gp.tileSize*2;
            g2.drawImage(archer, x, y, gp.tileSize*4, gp.tileSize*4, null);

            // Menu
            try {
                selector = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/selector.png")));
                selector = uTool.scaleImage(selector, 10*gp.scale, 10*gp.scale);
            } catch (IOException e) {
                e.printStackTrace();
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "New Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*4;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

            text = "Load Game";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

            text = "Select Class";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

            text = "Quit";
            x = getXforCenteredText(text);
            y = gp.screenHeight-(gp.tileSize/3);
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

        } else if(titleScreenState == 1){
            // Class selection
            g2.setColor(new Color(254, 174, 52));
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Archer";
            x = getXforCenteredText(text);
            y += gp.tileSize*5;

            int imgX = gp.screenWidth/2-(gp.tileSize*2);
            int imgY = y-(gp.tileSize*4);

            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawImage(archer, imgX, imgY,gp.tileSize*4, gp.tileSize*4, null);
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

            text = "Knight";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawImage(knight, imgX, imgY, gp.tileSize*4, gp.tileSize*4,null);
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

            text = "Mage";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawImage(mage, imgX, imgY, gp.tileSize*4, gp.tileSize*4, null);
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y = gp.screenHeight-(gp.tileSize/3);
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawImage(selector, x-gp.tileSize, y-(10*gp.scale), null);
            }

        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 65F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // Window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(new Color(192, 203, 220));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

}
