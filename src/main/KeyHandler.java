package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed, jPressed;
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

        if(gp.gameState == gp.titleState) {
            // Title
            titleState(code);
        } else if(gp.gameState == gp.playState) {
            // Play
            playState(code);
        } else if(gp.gameState == gp.pauseState){
            // Pause
            pauseState(code);
        } else if(gp.gameState == gp.dialogueState) {
            // Dialogue
            dialogueState(code);
        } else if(gp.gameState == gp.characterState) {
            // Character
            characterState(code);
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

    public void titleState(int code){
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
                    // New Game
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                } else if(gp.ui.commandNum == 1){
                    // Load Game

                } else if(gp.ui.commandNum == 2){
                    // Select Class
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 1;
                } else if(gp.ui.commandNum == 3){
                    // Quit
                    System.exit(0);
                }
            }

            if(code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }

        } else if(gp.ui.titleScreenState == 1) {
            selectClassState(code);
        }
    }

    public void selectClassState(int code){
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
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.titleScreenState=0;
        }
    }

    public void playState(int code){
        if(code == KeyEvent.VK_J){
            jPressed = true;
        }

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

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
            gp.stopMusic();
            return;
        }

        if(code == KeyEvent.VK_I) {
            gp.gameState = gp.characterState;
        }
    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
            gp.playMusic(0);
        }
    }

    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_I) {
            gp.gameState = gp.playState;
        }
    }

}
