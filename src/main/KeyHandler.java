package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed;
    GamePanel gp;

    // Debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title
        if(gp.gameState == gp.titleState) {
            if(gp.ui.titleScreenState==0){
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 3;
                    }
                } else if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3){
                        gp.ui.commandNum = 0;
                    }
                } else if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(gp.ui.commandNum == 1){

                    } else if(gp.ui.commandNum == 2){
                        gp.ui.commandNum = 0;
                        gp.ui.titleScreenState = 1;
                    } else if(gp.ui.commandNum == 3){
                        System.exit(0);
                    }
                }
            } else if(gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 3;
                    }
                } else if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3){
                        gp.ui.commandNum = 0;
                    }
                } else if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 3){
                        gp.ui.titleScreenState=0;
                    } else {
                        gp.player.updateSprite(gp.ui.commandNum+1);
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                }
            }

        }

        // Play
        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            } else if (code == KeyEvent.VK_A) {
                leftPressed = true;
            } else if (code == KeyEvent.VK_S) {
                downPressed = true;
            } else if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        // Pause
        if(code == KeyEvent.VK_ESCAPE) {
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
                gp.stopMusic();
            }else{
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
        }

        // Dialogue
        if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }

        // Debug
        if (code == KeyEvent.VK_P) {
            checkDrawTime = ! checkDrawTime;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        } else if (code == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_S) {
            downPressed = false;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }
}
