package essentials;

import instances.KeyInput;
import instances.Spawn;
import objects.BasicEnemy;
import objects.GameObject;
import objects.Handler;
import objects.ID;
import objects.MenuParticle;
import objects.Player;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9; // aspect ratio formula
    public static final String TITLE = "Dodge The Sprite";

    private Thread thread;
    private boolean isRunning = false;

    private Menu menu;

    // HeadsUP display
    private HUD hud;

    // Instances
    private Handler handler;
    private KeyInput kInput;  // Keyboard
    private Spawn spawner;

    private Random r = new Random();

    // Is game paused
    public boolean paused = false;
    // Did you win
    public  boolean won = false;

    public int surviveCountdown = 0;

    // State of our game
    public enum STATE {
        Menu,
        Select,
        Help,
        Game,
        GameOver
    };

    public STATE gameState = STATE.Menu;

    public Game() {
        new Window(WIDTH,HEIGHT,TITLE,this);
        start();
        init();
    }

    public void init() {
        handler = new Handler(this);
        hud = new HUD();
        kInput = new KeyInput(this,handler,hud);
        spawner = new Spawn(handler, hud,this);
        menu = new Menu(this, handler, kInput,hud);


        AudioPlayer.load();  //////// Load the Sound and Music
        AudioPlayer.getMenuMusic("menu_music").loop(0.5F,0.5F);

        this.addKeyListener(kInput);
        this.addMouseListener(menu);



        if (gameState == STATE.Game) {
            handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32,
                    ID.Player, kInput, handler, this));
            handler.addObject(new BasicEnemy(Game.WIDTH / 2 - 48, 0, ID.BasicEnemy, handler, this));
        } else {
            for (int i = 0; i < 45; i++) {
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT),
                        ID.MenuParticle, handler, this));
            }
        }
    }

    private synchronized void start() {
        // safety belt to avoid a mistake of running the game when the game is already running
        if (isRunning) return;

        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    private synchronized void stop() {
        // safety belt to avoid a mistake of stopping the game when the game is already stopped
        if (!isRunning) return;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRunning = false;
    }

    //essentials.Game Loop
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanosecondConversion = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) /nanosecondConversion;
            lastTime = now;;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(frames + " FPS");
                frames = 0;
            }
        }
        stop();

    }

    // Updates game
    private void tick() {

            if (!paused) {
                handler.tick();
                if (gameState == STATE.Game) {
                hud.tick();
                spawner.tick();
                isGameOver();
                    surviveCountdown++;
            }
        } else if (gameState == STATE.Menu || gameState == STATE.GameOver) {
                surviveCountdown = 0;
            menu.tick();
        }

    }

    // Renders game
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        if (paused ) {
            g.setColor(Color.red);
            Font f = new Font("ariel",1,40);
            g.setFont(f);
            g.drawString("PAUSED",300,55);
            g.setColor(Color.white);
            g.drawRect(265,15,220,50);
        }
            handler.render(g);
         if (gameState == STATE.Game) {
             if (surviveCountdown <= 180) {
                 Font f = new Font("ariel",1,40);
                 g.setFont(f);
                 g.setColor(Color.white);
                 g.drawString("SURVIVE", 300, 290);
                 g.setColor(Color.white);
                 g.drawRect(280,252,220,50);
             }


             Font f2 = new Font("ariel",1,15);
             g.setFont(f2);
            hud.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help  || gameState == STATE.GameOver) {
            menu.render(g);
        }

        bs.show();
        g.dispose();
    }

    // makes sure we dont go out of the borders of our game
    public static float outOfBounds(float val, int min, int max) {
        if (val >= max) {
            return val = max;
        } else if (val <= min) {
            return val = min;
        } else return val;
    }

    // method to determine if the game is over
    public void isGameOver() {

        // YOU LOSE
        if (hud.HEALTH <= 0) {
            HUD.HEALTH = 100;
            Spawn.scoreKeep = 0;
            gameState = STATE.GameOver; // Game over we go to gameover menu
            handler.clearEnemies(true); // kill all enemies and then create particles
            for (int i = 0; i < 45; i++) {
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT),
                        ID.MenuParticle, handler, this));
            }
        }

        // YOU WIN
        else if (hud.getScore() >= 6000) {
            won = true;
            HUD.HEALTH = 100;
            Spawn.scoreKeep = 0;
            gameState = STATE.GameOver; // Game over we go to gameover menu
            handler.clearEnemies(true); // kill all enemies and then create particles
            for (int i = 0; i < 45; i++) {
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT),
                        ID.MenuParticle, handler, this));
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
