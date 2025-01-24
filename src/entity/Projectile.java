package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;

     }

    public void update() {

        if (user == gp.player) {
            // Check if the projectile hits a monster
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != -1) {
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }

        } else {
            if (gp.player.solidArea.intersects(solidArea)) {
                gp.player.life -= attack;
                alive = false;
            }
        }

        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        life--;
        if(life <= 0) {
            alive = false;
        }
     }

}
