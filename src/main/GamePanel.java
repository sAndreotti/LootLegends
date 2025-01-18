package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    public final int originalTileSize = 16; // 32x32 tile
    public final int scale = 3; // scaling sprites

    public final int tileSize = originalTileSize*scale; // 96x96 tile
    public final int maxScreenCol = 16; // 16 columns displayed
    public final int maxScreenRow = 12; // 12 row displayed
    public final int screenWidth = tileSize*maxScreenCol; // 1536 pixels
    public final int screenHeight = tileSize*maxScreenRow; // 1152 pixels

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    public int FPS = 60;

    // System
    TileManager tileM = new TileManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    KeyHandler keyH = new KeyHandler(this);
    public Thread gameThread;
    public Sound music = new Sound();
    public Sound se = new Sound();

    // UI
    public UI ui = new UI(this);

    // Entities
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    // Entities
    public Entity npc[] = new Entity[10];


    // Collisions
    public CollisionChecker cChecker = new CollisionChecker(this);

    // Game state
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNpc();

        playMusic(0);

        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1_000_000_000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        //long timer = 0;
        //int drawCount = 0;

        while(gameThread!=null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            //timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                //drawCount++;
            }

            //if(timer >= 1000000000) {
            //    System.out.println("FPS: " + drawCount);
            //    drawCount = 0;
            //    timer = 0;
            //}
        }

    }

    public void update() {

        if(gameState == playState){
            // Play
            player.update();

            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }

        }else if(gameState == pauseState){
            // Pause
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Debug
        long drawStart = 0;
        if (keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }

        // Tile
        tileM.draw(g2);

        // Objects
        for(int i=0; i<obj.length; i++) {
            if(obj[i]!=null) {
                obj[i].draw(g2, this);
            }
        }

        // NPC
        for(int i=0; i<npc.length; i++) {
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }

        // Player
        player.draw(g2);

        // UI
        ui.draw(g2);

        // Debug
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400 );
            System.out.println("Draw time: " + passed);
        }


        g2.dispose();
    }

    // Sound methods
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        // Sound effect
        se.setFile(i);
        se.play();
    }

}
