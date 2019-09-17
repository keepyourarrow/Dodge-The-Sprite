package objects;

import essentials.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {

    private Handler handler;
    private  Game game;

    private Random r = new Random();

    ///////////// This class makes the particle effects on the screen of million different color possiblities ////////////
    /*
     RGB values, values that define a value you get on screen.
    I believe, there are over million different combinations
    by setting red,green,blue to random values.
    Color constructor accepts 3 values (RGB values)
    So say we get fortunate to get 0,255,255 as our 3 values
    That's cyan for you.
    */
    private int red = r.nextInt(255);
    private int green = r.nextInt(255);
    private int blue = r.nextInt(255);
    private Color color;

    public MenuParticle (float x, float y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.game = game;
        this.handler = handler;

        velX = (r.nextInt(7 - -7)+ -7);
        velY = (r.nextInt(7 - -7)+ -7);
        // So particles don't just stay in one place
        if (velX == 0) velX = 1;
        if (velY == 0) velY = 1;

        color = new Color(red, green, blue, 40);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= game.HEIGHT - 32) velY *= -1;
        if (x <= 0 || x >= game.WIDTH  - 16)  velX *= -1;

        handler.addObject(new Trail(x,y,ID.Trail,color,16,16,handler, 0.05f));
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int)x,(int)y,16,16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,16,16);
    }
}

