package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PotionRed extends Entity {

    int value = 2;

    public OBJ_PotionRed(GamePanel gp) {
        super(gp);
    
        name = "Potion_Red";
        type = typeConsumable;
        spriteDimX = 12;
        spriteDimY = 12;
        // Center the object in the tile
        diffX = (gp.originalTileSize - spriteDimX)/2*gp.scale;
        diffY = (gp.originalTileSize - spriteDimY)/2*gp.scale;

        getOBJImage("/potion/potion_red.png");
        description = name.replace("_", " ") + "\nA red potion that heals " + value + " HP";
    }

    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drank the red potion and\nhealed " + value + " HP";
        entity.life += value;
        if(entity.life > entity.maxLife) entity.life = entity.maxLife;
        //gp.playSE(1);
    }

}
