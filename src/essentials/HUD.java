package essentials;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

    public static int HEALTH = 100;
    private int greenValue = 255;

    private int score = 0;
    private int level = 1;

    public void tick() {
        greenValue  = HEALTH * 2;

        score++;
    }

    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0,0,200,32);

        // What this does - sets the color for our Health bar. The lower our score,
        // the darker the bar gets. I surrounded it with try catch, because sometimes, weirdly enough
        // this error comes up.
      try {
          g.setColor(new Color(75, greenValue, 0));
      } catch(IllegalArgumentException e) {
          e.printStackTrace();
          g.setColor(new Color(75, 200, 0));
      }


        g.fillRect(0,0,HEALTH*2,32);
        g.setColor(Color.gray);
        g.drawRect(0,0,200,32);

        g.setColor(Color.white);
        g.drawString("Score: " + score, 3, 54);
        g.drawString("Level: " + level, 6, 70);
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
