package main;

import entity.Entity;
import object.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    UtilityTool uTool;
    Font vt323;
    public String currentDialogue = "";
    public int commandNum = 0;
    BufferedImage selector = null;

    // Player status
    public BufferedImage[] heart_bar = new BufferedImage[3];
    public BufferedImage heart_full, heart_half, heart_blank;

    // Messages
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    // Inventory
    public int slotCol = 0;
    public int slotRow = 0;

    // Class selection
    public int titleScreenState = 0;
    BufferedImage archer = null;
    BufferedImage knight = null;
    BufferedImage mage = null;

    // Colors
    Color textColor = new Color(192, 203, 220);
    Color titleColor = new Color(254, 174, 52);
    public Color backgroundColor = new Color(24, 20, 37);
    public Color backgroundDialogueColor = new Color(0, 0, 0, 220);

    public UI(GamePanel gp) {
        this.gp = gp;
        uTool  = new UtilityTool();

        // Load font
        try{
            InputStream is = getClass().getResourceAsStream("/font/VT323-Regular.ttf");
            assert is != null;
            vt323 = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Load selector image
        try {
            selector = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/title/selector.png")));
            selector = uTool.scaleImage(selector, 10*gp.scale, 10*gp.scale);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load class images
        loadClasses();

        // Create HUD object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        String[] paths = {"/gui/status/Tile_01.png", "/gui/status/Tile_02.png", "/gui/status/Tile_03.png"};
        loadBar(heart_bar, paths);

    }

    // Y in drawstring indicates the base of the string
    public void draw(Graphics2D g2) {
       this.g2 = g2;
       g2.setFont(vt323);
       g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g2.setColor(Color.white);

       if(gp.gameState == gp.titleState) {
           // Title
           drawTitleScreen();
       } else if(gp.gameState == gp.playState) {
           // Play
           drawPlayerLife();
           drawMessage();
       } else if(gp.gameState == gp.pauseState) {
           // Pause
           drawPlayerLife();
           drawPauseScreen();
       } else if(gp.gameState == gp.dialogueState) {
           // Dialogue
           drawPlayerLife();
           drawDialogueScreen();
       } else if(gp.gameState == gp.characterState) {
            // Stats screen
            drawCharacterScreen();
            drawInventory();
       }
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = (gp.maxScreenRow-1)*gp.tileSize;

        g2.setFont(vt323.deriveFont(Font.BOLD, 20F));
        for(int i=message.size()-1; i>=0; i--) {
            if(message.get(i) != null){
                g2.setColor(backgroundColor);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(textColor);
                g2.drawString(message.get(i), messageX, messageY);
                messageY -= gp.tileSize/2;
                messageCounter.set(i, messageCounter.get(i)+1);
                if(messageCounter.get(i) == gp.FPS*3) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
            
        }

    }

    public void drawPlayerLife() {
        int y = gp.tileSize/2;
        int i = 0;

        // Calculating how much bar display
        int imgX = 10*gp.scale;
        int resetX = imgX;
        int nBars = (gp.player.maxLife/2)-2;
        g2.drawImage(heart_bar[0], imgX, y-(3*gp.scale), null);
        imgX += gp.tileSize/2 + gp.tileSize/3;
        for(int j=0; j<nBars; j++){
            g2.drawImage(heart_bar[1], imgX, y-(3*gp.scale), null);
            imgX += gp.tileSize/2 + gp.tileSize/3;
        }
        g2.drawImage(heart_bar[1], imgX, y-(3*gp.scale), null);
        g2.drawImage(heart_bar[2], imgX + gp.tileSize/4, y-(3*gp.scale), null);


        imgX = resetX;
        imgX += (6*gp.scale);
        y += (gp.scale/2);

        // Draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_blank, imgX, y, null);
            i+=2;
            imgX += gp.tileSize/2 + gp.tileSize/3;
        }

        imgX = resetX;
        // Draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, imgX, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, imgX, y, null);
            }
            i++;
            imgX += gp.tileSize/2 + gp.tileSize/3;
        }

    }

    public void drawTitleScreen() {
        // Window color
        g2.setColor(backgroundColor);
        g2.drawRect(0, 0, gp.screenWidth, gp.screenHeight);
        if(titleScreenState == 0){

            // Title Background
            try{
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/title/title.png")));
                image = uTool.scaleImage(image, 194*gp.scale, 64*gp.scale);
                g2.drawImage(image, gp.screenWidth/2-((194*gp.scale)/2), gp.tileSize/4, null);
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
            g2.setColor(titleColor);
            g2.drawString(text, x, y);

            // Player image
            x = gp.screenWidth/2 - (gp.tileSize*2);
            y += gp.tileSize*3;
            g2.drawImage(gp.player.shadow, x+(gp.player.spriteDim)+30, y+(gp.player.spriteDim*3)+15,
                    gp.tileSize+(gp.tileSize/2), gp.tileSize-(gp.tileSize/2),null);
            g2.drawImage(archer, x, y, gp.tileSize*4, gp.tileSize*4, null);

            // Menu options
            g2.setColor(textColor);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "New Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*5;
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
            g2.setColor(textColor);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*2 + (gp.tileSize/4);
            g2.drawString(text, x, y);

            int imgX = gp.screenWidth/2-(gp.tileSize*2);
            int imgY = y+gp.tileSize*3;
            // Draw the shadow
            if(commandNum!=3) {
                g2.drawImage(gp.player.shadow, imgX + (gp.player.spriteDim/2)+gp.tileSize-3, imgY +(gp.tileSize*2) + (gp.tileSize/3),
                        gp.tileSize + (gp.tileSize / 2), gp.tileSize - (gp.tileSize / 2), null);
            }

            text = "Archer";
            x = getXforCenteredText(text);
            y += gp.tileSize*8;
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

    public void drawCharacterScreen() {
        // Create a Frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*9+(gp.tileSize/2);
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(textColor);
        g2.setFont(vt323.deriveFont( 32F));

        int textX = frameX + gp.tileSize/2;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // Parameters
        g2.drawString("Level " , textX, textY);
        textY += lineHeight;
        g2.drawString("Life " , textX, textY);
        textY += lineHeight;
        g2.drawString("Strenght " , textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity " , textX, textY);
        textY += lineHeight;
        g2.drawString("Attack " , textX, textY);
        textY += lineHeight;
        g2.drawString("Defense " , textX, textY);
        textY += lineHeight;
        g2.drawString("Exp " , textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level " , textX, textY);
        textY += lineHeight;
        g2.drawString("Coin " , textX, textY);
        textY += lineHeight+(lineHeight/2);
        g2.drawString("Weapon " , textX, textY);
        textY += lineHeight;
        g2.drawString("Shield " , textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - gp.tileSize;
        // Reset text Y
        textY = frameY + gp.tileSize;
        
        drawValues(String.valueOf(gp.player.level), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.life)+"/"+String.valueOf(gp.player.maxLife), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.strenght), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.dexterity), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.attack), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.defense), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.exp), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.nextLevelExp), tailX, textY, textX);
        textY += lineHeight;
        drawValues(String.valueOf(gp.player.coin), tailX, textY, textX);
        textY += lineHeight+(7*gp.scale);

        // Images for weapon and shield
        g2.drawImage(gp.player.currentWeapon.image, tailX-(gp.tileSize/2), textY-(gp.tileSize/2), gp.tileSize/2, gp.tileSize/2, null);
        textY += lineHeight;
        g2.drawImage(gp.player.currentShield.image, tailX-(gp.tileSize/2), textY-(gp.tileSize/2), gp.tileSize/2, gp.tileSize/2, null);
    
    }

    public void drawValues(String value, int tailX, int textY, int textX) {
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
    }

    public void drawInventory(){
        int frameX = gp.screenWidth - (gp.tileSize*7);
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // TODO maybe little than this
        // Draw player items
        for(int i=0; i<gp.player.inventory.size(); i++){
            g2.drawImage(gp.player.inventory.get(i).image, slotX, slotY, gp.tileSize, gp.tileSize, null);
            slotX += slotSize;
            if(slotX > frameX + frameWidth - gp.tileSize){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXstart + (slotCol * slotSize);
        int cursorY = slotYstart + (slotRow * slotSize);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // Draw cursor
        g2.setColor(textColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description frame
        if(!gp.player.inventory.get(getItemIndexOnSlot()).description.equals("")) {
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight + gp.tileSize/2;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize*3;
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            // Description text
            g2.setColor(textColor);
            g2.setFont(vt323.deriveFont( 20F));
            int dTextX = dFrameX + 20;
            int dTextY = dFrameY + gp.tileSize;
            if(gp.player.inventory.size() > 0){
                for(String line: gp.player.inventory.get(getItemIndexOnSlot()).description.split("\n")){
                    g2.drawString(line, dTextX, dTextY);
                    dTextY += 25;
                }
            }
        }
        

    }

    public int getItemIndexOnSlot() {
        return (slotRow*5)+slotCol;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(backgroundDialogueColor);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(textColor);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

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

    public void loadBar(BufferedImage[] bar, String[] paths) {
        for(int i=0; i<bar.length; i++) {
            try{
                bar[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(paths[i])));
                bar[i] = uTool.scaleImage(bar[i], 16*(gp.scale), 16*gp.scale);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    public int getXforAlignToRight(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

}
