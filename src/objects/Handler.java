package objects;


import essentials.Game;

import java.awt.Graphics;
import java.util.LinkedList;

/* Purpose of this Class:
1) It stores every Gameobject object in LInkedList
2) During our update(tick) stage that essentials.Game class starts, we call Handler's tick method (this class)
3) TIck method is simple, it just updates everything our game has. All objects. Player, Enemy, Blocks, anything
4) Render is also the same, it renders those objects.
5) Forgot to add that Handler tick and render loops through ALL OF OUR objects that are being stored in LInkedList
- and it calls tick and render method of every object since all objects have tick and render methods
6) Add Object is how we add objects to LinkedList to begin with. As it starts off empty
7) Remove Object removes object. So when a PLayer kills an enemy, the enemy should disappear (removeObject called)
 */

public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    Game game;

    public Handler(Game game) {
        this.game = game;
    }
    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            object.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            object.get(i).render(g);
        }
    }

    public GameObject addObject(GameObject tempObject) {
        object.add(tempObject);
        return tempObject;
    }

    public void removeObject(GameObject tempObject) {
        object.remove(tempObject);
    }

    public void clearEnemies(boolean killPlayer) {

        // if killPlayer is true kill player and everyone else

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            if (!killPlayer) {
                if (object.get(i).getId() != ID.Player) {
                    removeObject(tempObject);
                    i--;
                }
            } else if (killPlayer) {
                removeObject(tempObject);
                i--;
            }
        }
    }

}
