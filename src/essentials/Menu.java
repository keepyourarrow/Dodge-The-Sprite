package essentials;

import instances.KeyInput;
import objects.BasicEnemy;
import objects.Handler;
import objects.ID;
import objects.Player;
import org.lwjgl.openal.AL;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu  extends MouseAdapter {

    private Game game;
    private Handler handler;
    private KeyInput kInput;
    private HUD hud;

    public Menu (Game game, Handler handler, KeyInput kInput, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.kInput = kInput;
        this.hud = hud;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.gameState == Game.STATE.Menu) {
            // Play button
            if (mouseOver(mx,my,280,190,200,64)) {
                game.gameState = Game.STATE.Game;
                handler.clearEnemies(false);
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, kInput, handler, game));
                handler.addObject(new BasicEnemy(Game.WIDTH / 2 - 48, 0, ID.BasicEnemy, handler, game));

                AudioPlayer.getSound("click").play(1F,0.5F);
                AudioPlayer.getGameMusic("game_music").loop(0.5F,0.5F);
            }
            // Help button
            if (mouseOver(mx,my,280,290,200,64)) {
                game.gameState = Game.STATE.Help;
                AudioPlayer.getSound("click").play(1F,0.5F);
            }
            // Quit button
            if (mouseOver(mx,my,280,390,200,64)) {
                AL.destroy();
                System.exit(1);
            }
        }


        // Back button
        if (game.gameState == Game.STATE.Help) {
            if (mouseOver(mx, my, 15, 510, 200, 64)) {
                game.gameState = Game.STATE.Menu;
                AudioPlayer.getSound("click").play(1F,0.5F);
            }
        }

        // Try again button
        if (game.gameState == Game.STATE.GameOver) {
            if (mouseOver(mx, my, 250, 415, 300, 64)) {
                hud.setLevel(1);
                hud.setScore(0);
                handler.clearEnemies(true); // kill all particles
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32,
                        ID.Player, kInput, handler, game));
                handler.addObject(new BasicEnemy(Game.WIDTH / 2 - 48, 0, ID.BasicEnemy, handler, game));
                game.won = false;
            }
        }

    }

    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height)
                return true;
            else return false;
        } else return false;
    }

    public void tick() {

    }
    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Menu) {
            Font f = new Font("arial", 1, 50);
            Font f2 = new Font("arial", 1, 30);

            g.setFont(f);
            g.setColor(Color.white);
            g.drawString("Dodge The Sprite", 195, 135);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Play", 350, 235);
            g.setColor(Color.white);
            g.drawRect(280, 190, 200, 64);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Help", 350, 335);
            g.setColor(Color.white);
            g.drawRect(280, 190, 200, 64);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Quit", 350, 435);
            g.setColor(Color.white);
            g.drawRect(280, 190, 200, 64);

            g.setColor(Color.white);
            g.drawRect(280, 290, 200, 64);

            g.setColor(Color.white);
            g.drawRect(280, 390, 200, 64);
        } else if (game.gameState == Game.STATE.Help) {
            help(g);
            back(g);
        } else if (game.gameState == Game.STATE.GameOver) {
            gameOver(g);
        }
    }

    private void help(Graphics g) {
        Font f = new Font("arial", 1, 50);
        Font f2 = new Font("arial", 1, 24);

        g.setFont(f);
        g.setColor(Color.white);
        g.drawString("Help", 315, 135);

        g.setFont(f2);
        g.drawString("Use WASD or Arrow keys to move around.", 55, 195);
        g.drawString("Try your best to dodge and survive the onslaught of enemies.", 55, 225);
        g.drawString("You will win if you survive long enough.", 55, 255);
        g.drawString("Get to 4000 to get a BOSS, get 6000 to WIN.", 55, 315);
        g.drawString("Press P to pause.", 55, 345);

    }

    private void back(Graphics g) {
        Font f = new Font("arial", 1, 35);

        g.setFont(f);
        g.setColor(Color.white);
        g.drawString("Back", 15, 530);
    }

    private void gameOver(Graphics g) {
        Font f = new Font("arial", 1, 50);
        Font f2 = new Font("arial", 1, 30);

        if (!game.won) {
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString("Game Over", 255, 135);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Your final score is: " + hud.getScore(), 225, 205);
            g.drawString("What happened?", 275, 305);
            g.drawString("Got beaten by a simple kids game? ",  155, 355);

        } else {
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString("CONGRATULATIONS,", 145, 135);
            g.drawString("YOU WON!!!", 245, 185);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Amazing! Couldn't have done it better myself!", 45, 245);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Your final score is: " + hud.getScore(), 228, 325);
        }


        g.setFont(f2);
        g.setColor(Color.white);
        g.drawString("Wanna Try again?", 260, 455);
        g.drawRect(240, 415, 300, 64);



    }
}
