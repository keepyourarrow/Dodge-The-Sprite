package instances;

import essentials.Game;
import essentials.HUD;
import objects.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;

    public KeyInput(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    public boolean keys[] = new boolean[4];
    // key 0 = true  move right
    // key 1 = true move left
    // key 2 = true move up
    // key 3 = true move down

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) { keys[0] = true; }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) { keys[1] = true; }
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) { keys[2] = true; }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) { keys[3] = true; }


        if (key == KeyEvent.VK_P) {

            if (game.gameState == Game.STATE.Game) {
                if (game.paused) {
                    game.paused = false;
                } else game.paused = true;
            }

        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) { keys[0] = false; }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) { keys[1] = false; }
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) { keys[2] = false; }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) { keys[3] = false; }

    }
}
