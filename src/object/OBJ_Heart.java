package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {
        super(gp);

        name = "Heart";

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/BarTileLife1.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/BarTileLife2.png")));
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);

            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/status/BarTileLife3.png")));
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
