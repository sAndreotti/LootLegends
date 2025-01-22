package main;

public class EventHandler {

    GamePanel gp;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        for(int i=0; i<gp.maxWorldCol; i++) {
            for(int j=0; j<gp.maxWorldRow; j++) {
                eventRect[i][j] = new EventRect();
                eventRect[i][j].x = gp.tileSize/2;
                eventRect[i][j].y = gp.tileSize/2;
                eventRect[i][j].width = 4;
                eventRect[i][j].height = 4;
                eventRect[i][j].eventRectDefaultX = eventRect[i][j].x;
                eventRect[i][j].eventRectDefaultY = eventRect[i][j].y;
            }
        }

    }

    public void checkEvent() {
        // Check if player is away from last event
        int xDistance = Math.abs(gp.player.worldX-previousEventX);
        int yDistance = Math.abs(gp.player.worldY-previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }

        // Event check
        if(canTouchEvent){
            if(hit(20, 45, "down")){ damagePit(20, 45, gp.dialogueState); }
            if(hit(41, 45, "up")){ healingPool(gp.dialogueState); }
            if(hit(2, 5, "any")){ teleport(gp.dialogueState); }
        }

    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if(gp.player.direction.equals(reqDirection) || reqDirection.equals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        //eventRect[col][row].eventDone = true;
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life--;

        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You are healed!\nYour life has been recovered";
            gp.player.life = gp.player.maxLife;
            gp.aSetter.setMonster();
        }
    }

    public void teleport(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You have been teleported!";
        gp.player.worldX = 41*gp.tileSize;
        gp.player.worldY = 45*gp.tileSize;
    }
}
