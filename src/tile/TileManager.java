package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

import main.UtilityTool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[110];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        loadSprites("/maps/spritesheet.png");
        loadMap("/maps/map.json");
    }

    public void loadSprites(String spritePath){
        UtilityTool uTool = new UtilityTool();

        int counter = 0;
        try {
            System.out.println(spritePath);
            // leggo png, e creo le tile
            BufferedImage tiles = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(spritePath)));
            int width = tiles.getWidth();
            int height = tiles.getHeight();

            int imagesRow = height/gp.originalTileSize;
            int imagesCol = width/gp.originalTileSize;

            for(int i=0; i<imagesRow; i++) {
                for(int j=0; j<imagesCol; j++) {
                    tile[counter] = new Tile();
                    //System.out.println(counter + " -> " + gp.originalTileSize*i + " - " + gp.originalTileSize*j);
                    // Use efficient image scaling
                    tile[counter].image = uTool.scaleImage(tiles.getSubimage(gp.originalTileSize*j, gp.originalTileSize*i,
                            gp.originalTileSize, gp.originalTileSize), gp.tileSize, gp.tileSize);

                    // Set collision for tiles
                    if(counter!=13) {
                        //System.out.println("Adding collision to tile " + counter);
                        tile[counter].collision = true;
                    }

                    counter++;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Images loaded: " + counter);
    }

    public void loadMap(String mapPath){
        int counter = 0;

        // leggo json e metto numeri nella matrice
        try {
            // Parse the JSON file
            JSONParser parser = new JSONParser();
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            assert inputStream != null;
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(inputStream));
            JSONArray layers = (JSONArray) jsonObject.get("layers");

            // Iterate through layers and tiles to populate the matrix
            for (Object layerObj : layers) {
                JSONObject layer = (JSONObject) layerObj;
                JSONArray tiles = (JSONArray) layer.get("tiles");

                for (Object tileObj : tiles) {
                    JSONObject tile = (JSONObject) tileObj;

                    int x = ((Long) tile.get("x")).intValue();
                    int y = ((Long) tile.get("y")).intValue();
                    int id = Integer.parseInt((String) tile.get("id"));

                    // Ensure coordinates are within matrix bounds
                    if (x >= 0 && x <= gp.maxWorldCol && y >= 0 && y <= gp.maxWorldRow) {
                        mapTileNum[x][y] = id;
                        counter++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loaded cells, "+ counter);
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // If the tile is visible draw the tile
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}