package objects;

import essentials.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class FastEnemy extends GameObject {

    private Handler handler;
    private  Game game;

    public FastEnemy(float x, float y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.game = game;
        this.handler = handler;

        velX = 2;
        velY = 13;
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= game.HEIGHT - 66) velY *= -1;
        if (x <= 0 || x >= game.WIDTH  - 32)  velX *= -1;

        handler.addObject(new Trail(x,y,ID.Trail,Color.cyan,24,24,handler, 0.04f));
    }

    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect((int)x,(int)y,24,24);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,24,24);
    }
}
