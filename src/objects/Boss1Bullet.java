package objects;

import essentials.Game;
import essentials.HUD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Boss1Bullet extends GameObject {

    private Handler handler;
    private  Game game;
    Random r = new Random();

    public Boss1Bullet (float x, float y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.game = game;
        this.handler = handler;

        velX = (r.nextInt(5 - -5) + -5); // random number between 5 and -5
        velY = 5;
    }

    public void tick() {
        x += velX;
        y += velY;

//        if (y <= 0 || y >= game.HEIGHT - 66) velY *= -1;
//        if (x <= 0 || x >= game.WIDTH  - 32)  velX *= -1;

        if ( y >= Game.HEIGHT) handler.removeObject(this);

        collision();

    }
    public void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);


            // if we encounter Player
            // HP will drop the same
            if (tempObject.getId() == ID.Player) {
                //collision code below
                if (tempObject.getBounds().intersects(getBounds())) {
                    handler.removeObject(this);
                    HUD.HEALTH -=18;
                }
            }
        }
        }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int)x,(int)y,9,9);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,9,9);
    }
}
