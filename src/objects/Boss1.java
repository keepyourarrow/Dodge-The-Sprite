package objects;

import essentials.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


//////// Boss logic //////
 /*
 Variable timer's number corresponds to how much we want the boss to descend. The higher the number, the lower he goes.
 Variable timer2 corresponds to how much we want our boss to wait until he starts moving and shooting
 The reason for using Variable spawn is to store a random integer there.
 And once spawn is 0 we will generate a bullet, since it's happening too fast, there will be lots of bullets.
 You can increase the amount of bullets by adding if spawn == 0 && spawn == 1. There will be twice as many bulletes
  */
public class Boss1 extends GameObject {

    private Handler handler;
    private  Game game;

    private int timer = 50;
    private int timer2 = 150;
    private Random r = new Random();
    private int spawn;

    public Boss1(float x, float y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.game = game;
        this.handler = handler;

        velX = 0;
        velY = 2;
    }

    public void tick() {
        x += velX;
        y += velY;

        if (timer <= 0) velY = 0;
        else timer--;

        if (timer <= 0)
            timer2--;
        if (timer2 <= 0) {
            if (velX == 0) velX = 2;
            spawn = r.nextInt(10);
            if (spawn == 0 || spawn == 1 || spawn == 2)
                handler.addObject(new Boss1Bullet((int)x+48, (int) y+48, ID.Boss1Bullet, handler, game));
        }

        if (x <= 0 || x >= game.WIDTH  - 97)  velX *= -1;
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x,(int)y,90,74);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,90,74);
    }
}
