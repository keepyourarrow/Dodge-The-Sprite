package essentials;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioPlayer {


    public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
    public static Map<String, Music> menuMusicMap = new  HashMap<String, Music>();
    public static Map<String, Music> gameMusicMap = new  HashMap<String, Music>();

    public static void load() {

        try {
            soundMap.put("click", new Sound("res/click_sound.ogg"));
            menuMusicMap.put("menu_music", new Music("res/menu_music.ogg"));
            gameMusicMap.put("game_music", new Music("res/game_music.ogg"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static Music getMenuMusic(String key) {
        return menuMusicMap.get(key);
    }
    public static Music getGameMusic(String key) {
        return gameMusicMap.get(key);
    }

    public static Sound getSound(String key) {
        return soundMap.get(key);
    }
}
