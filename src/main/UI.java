package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_30, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    // Y in drawstring indicates the base of the string
    public void draw(Graphics2D g2) {
        g2.setFont(arial_30);
        g2.setColor(Color.white);
        if(gameFinished) {
            String text = "You found the treasure!";
            int textLen = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth/2 - textLen/2;
            int y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);

            text = "Your time is " + dFormat.format(playTime) + " s";
            textLen = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLen/2;
            y = gp.screenHeight/2 + (gp.tileSize*4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            text = "Congraturations!";
            textLen = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLen/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString(String.valueOf(gp.player.hasKey), 85, 55);

            // Time
            playTime += (double)1/60;
            g2.drawString("Time: "+ dFormat.format(playTime), gp.tileSize*12, 55);

            // Message
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 10);

                messageCounter++;
                // Stay on for 2 seconds
                if (messageCounter > gp.FPS * 2) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }

}
