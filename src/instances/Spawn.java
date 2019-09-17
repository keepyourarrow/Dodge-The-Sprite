package instances;

import essentials.Game;
import essentials.HUD;
import objects.BasicEnemy;
import objects.Boss1;
import objects.FastEnemy;
import objects.Handler;
import objects.ID;
import objects.PowerfulEnemy;
import objects.SmartEnemy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Spawn {

    private Handler handler;
    private HUD hud;
    private Game game;

    public static int scoreKeep = 0;

    public Spawn(Handler handler, HUD hud, Game game) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;
    }

    public void tick() {

            scoreKeep();
    }

    ////////////////  UPDATES OUR LEVEL /////////////
    // it keeps our score. And it iterates the same way our ACTUAL score iterates.
    // Once it gets to 500, we get to the next level.
    // Easier to do it with another variable scoreKeep instead of using actual score variable
    // Then spawns more enemies the higher the level
    public void scoreKeep() {
        scoreKeep++;


        if (hud.getLevel() == 1 || hud.getLevel() == 2) {
            if (scoreKeep >= 500) { // change scoreKeep number to decrease Level round
                scoreKeep = 0;
                    hud.setLevel(hud.getLevel() + 1);
                    spawnEnemies();
            }
        } else {
            if (scoreKeep >= 1500) { // change scoreKeep number to decrease Level round
                scoreKeep = 0;
                hud.setLevel(hud.getLevel() + 1);
                spawnEnemies();
            }
        }
    }

    private void spawnEnemies() {

        if (hud.getLevel() == 2) {
            handler.addObject(new BasicEnemy(0, 0,
                    ID.BasicEnemy, handler, game));
        }
        else if (hud.getLevel() == 3) {
            handler.addObject(new SmartEnemy(Game.WIDTH/2-48, 0,
                    ID.BasicEnemy, handler, game));
            handler.addObject(new SmartEnemy(0, 0,
                    ID.BasicEnemy, handler, game));
            handler.addObject(new SmartEnemy(Game.WIDTH -62,0,
                    ID.BasicEnemy, handler, game));
        }
        else if (hud.getLevel() == 4) {
            handler.addObject(new BasicEnemy(0, Game.HEIGHT-70,
                    ID.BasicEnemy, handler, game));
            handler.addObject(new FastEnemy(Game.WIDTH-38, Game.HEIGHT-82,
                    ID.FastEnemy, handler, game));
            handler.addObject(new PowerfulEnemy(0, 0,
                    ID.PowerfulEnemy, handler, game));
        }
        else if (hud.getLevel() == 5) {
            HUD.HEALTH = 100;
            handler.clearEnemies(false);
            handler.addObject(new Boss1(Game.WIDTH/2-48,-100, ID.Boss1,handler,game));
        }
        else { }
    }

}


