package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

import main.UtilityTool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    String mapPath = "/maps/map3.json";


    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[130];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        System.out.println("Loading map...");
        //loadSprites("/maps/spritesheet.png");
        //loadMap("/maps/map.json");
        loadSprites();
        loadMap();
        System.out.println(" ");
    }

    public void loadSprites(){
        UtilityTool uTool = new UtilityTool();
        int counter = 0;
        try {
            // Parse the JSON file
            JSONParser parser = new JSONParser();
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            assert inputStream != null;
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(inputStream));
            // Get the "layers" array from the root object
            JSONArray layers = (JSONArray) jsonObject.get("layers");

            // Access the first layer object in the "layers" array
            JSONObject layer = (JSONObject) layers.get(0);

            // Get the "tiles" array from the layer object
            JSONArray tiles = (JSONArray) layer.get("tiles");

            // Iterate through tiles to load images
            for (Object tileObj : tiles) {
                JSONObject tileJson = (JSONObject) tileObj;
                int id = Integer.parseInt((String) tileJson.get("id"));

                tile[id] = new Tile();
                String imagePath = "/maps/tiles/Tile_" + id + ".png";
                BufferedImage tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                tile[id].image = uTool.scaleImage(tileImage, gp.tileSize, gp.tileSize);

                // Set collision for tiles
                if (id <= 13 || id == 19 || (id >= 23 && id <= 28) || id == 33 || id == 37 || id == 44 || 
                    (id>=48 && id<=53) || (id>=57 && id<=65) || id==75 || (id>=77 && id<=81) || 
                    (id>=86 && id<=93) || (id>=97 && id<=105)) {
                    tile[id].collision = true;
                }

                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Images loaded: " + counter);
    }

    public void loadMap(){
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