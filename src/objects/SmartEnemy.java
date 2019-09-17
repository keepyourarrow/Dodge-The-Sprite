package objects;

import essentials.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject {

    private Handler handler;
    private  Game game;
    private GameObject player;

    public SmartEnemy(float x, float y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.game = game;
        this.handler = handler;


        for (int i = 0; i < handler.object.size(); i++) {
            if(handler.object.get(i).getId() == ID.Player ) {
                player = handler.object.get(i);
            }
        }

        velX = 5;
        velY = 5;
    }

    public void tick() {
        x += velX;
        y += velY;

        //// ALGORITHM for following Player
        float diffX = x - player.getX() - 16; //  difference in x position
        float diffY = y - player.getY() - 16; //  difference in y position
        float distance = (float) Math.sqrt((x - player.getX()) * (x - player.getX())
                + (y - player.getY()) * (y - player.getY()));

        velX =  ((-1 / distance) * diffX) ;
        velY = ((-1 / distance) * diffY) ;

        if (y <= 0 || y >= game.HEIGHT - 66) velY *= -1;
        if (x <= 0 || x >= game.WIDTH  - 32)  velX *= -1;

        handler.addObject(new Trail(x,y,ID.Trail,Color.green,16,16, handler, 0.02f));
    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x,(int)y,16,16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,16,16);
    }
}
