package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    protected float x, y;  // coordinates
    protected float velX, velY;  // velocity
    protected ID id;  // Enum of all our objects - player,enemy etc...

    public GameObject(float x, float y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }


    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds(); // to detect collision


    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
