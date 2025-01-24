package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    public final int originalTileSize = 16; // 32x32 tile
    public final int scale = 3; // scaling sprites

    public final int tileSize = originalTileSize*scale; // 96x96 tile
    public final int maxScreenCol = 24; // 16 columns displayed
    public final int maxScreenRow = 18; // 12 row displayed
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
    public KeyHandler keyH = new KeyHandler(this);
    public Thread gameThread;
    public Sound music = new Sound();
    public Sound se = new Sound();
    public EventHandler eHandler = new EventHandler(this);

    // UI
    public UI ui = new UI(this);

    // Entities
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Collisions
    public CollisionChecker cChecker = new CollisionChecker(this);

    // Game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;



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
        aSetter.setMonster();

        // Intro music
        //playMusic(0);

        gameState = titleState;
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

            for(int i=0; i<monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    } else if(!monster[i].alive && !monster[i].dying){
                        monster[i] = null;
                    }
                }
            }

        }else if(gameState == pauseState){
            // Pause
        }else if(gameState == dialogueState){

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Title Screen
        if(gameState == titleState) {
            ui.draw(g2);

            return;
        }

        // Debug
        long drawStart = 0;
        if (keyH.showDebugText){
            drawStart = System.nanoTime();
        }

        // Tile
        tileM.draw(g2);

        // Add Entities to list
        // Player
        entityList.add(player);

        // NPCs
        for (Entity entity : npc) {
            if (entity != null) {
                entityList.add(entity);
            }
        }

        // Objects
        for (Entity entity : obj) {
            if (entity != null) {
                entityList.add(entity);
            }
        }

        // Monsters
        for (Entity entity : monster) {
            if (entity != null) {
                entityList.add(entity);
            }
        }

        // Sort by worldY
        entityList.sort(Comparator.comparingInt(o -> o.worldY));

        // Draw entities
        for(Entity entity : entityList){
            entity.draw(g2);
        }

        // Empty list
        entityList.clear();

        // UI
        ui.draw(g2);

        // Debug
        if(keyH.showDebugText){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX: " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Column: " + (player.worldX+player.solidArea.x)/tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row: " + (player.worldY+player.solidArea.y)/tileSize, x, y);
            y += lineHeight;
            g2.drawString("Draw time: " + passed, x, y );
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
