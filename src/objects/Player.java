package objects;

import essentials.Game;
import essentials.HUD;
import instances.KeyInput;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject {
    // acceleration decceleration
    private float acc = 1f;
    private float dcc = 0.5f;

    private Game game;
    private KeyInput kInput;
    private Handler handler;


    public Player(float x, float y, ID id, KeyInput kInput, Handler handler, Game game) {
        super(x, y, id);
        this.kInput = kInput;
        this.game = game;
        this.handler = handler;
    }

    public void tick() {

        x += velX;
        y += velY;



       x = game.outOfBounds(x,0,Game.WIDTH - 47);
       y = game.outOfBounds(y,0,Game.HEIGHT - 70);

        move();
        collision(); // if we hit an enemy

        handler.addObject(new Trail(x,y,ID.Trail,Color.white,32,32,handler, (float)0.05));
    }

    private void move() {

        // Horizontal movement
        // key 0 = true  move right
        // key  1 = true move left
        if (kInput.keys[0]) velX += acc;
        else if (kInput.keys[1]) velX -= acc;
        else if (!kInput.keys[0] && !kInput.keys[1]) {
            if (velX > 0) velX -= dcc;
            else if (velX < 0) velX += dcc;
        }

        // Vertical movement
        // key 2 = true move up
        // key 3 = true  move down
        if (kInput.keys[2]) velY -= acc;
        else if (kInput.keys[3]) velY += acc;
        else if (!kInput.keys[2] && !kInput.keys[3]) {
            if (velY > 0) velY -= dcc;
            else if (velY < 0) velY += dcc;
        }
        // makes sure our velocity has a max and min value (otherwise it would get uncontrollable)
        velX = clamp(velX, 7, -5);
        velY = clamp(velY, 7, -5);
    }

    private void collision() {

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);


            // if we encounter Basic (red) enemy or fast enemy or smart enemy
            // HP will drop the same
            if (tempObject.getId() == ID.BasicEnemy
                    || tempObject.getId() == ID.FastEnemy
                    || tempObject.getId() == ID.SmartEnemy) {
                //collision code below
                if (tempObject.getBounds().intersects(getBounds()))
                HUD.HEALTH -= 2;

                // If we encounter Powerful enemy, hp will drop twice as much
            } else if (tempObject.getId() == ID.PowerfulEnemy) {
                if (tempObject.getBounds().intersects(getBounds()))
                    HUD.HEALTH -= 4;
            } else if (tempObject.getId() == ID.Boss1) {
                if (tempObject.getBounds().intersects(getBounds()))
                    HUD.HEALTH -= 100; // DEATH
            }
        }
    }

    private float clamp(float value, int max, int min) {
        if (value > max) value = max;
        else if (value <= min) value = min;

        return value;
    }
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int)x,(int)y,32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
