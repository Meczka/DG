package me.meczka.sprites;

import me.meczka.graphics.Animation;
import me.meczka.graphics.Sprite;
import me.meczka.interfaces.Eatable;
import me.meczka.interfaces.Openable;
import me.meczka.items.Item;

import java.util.ArrayList;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Player extends Creature {
    private int foodPoints = 10;
    private int health = 20;
    private ArrayList inventory;
    public Player(Animation anim)
    {

        super(anim);
        inventory = new ArrayList();

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void eat(Eatable meal)
    {
        foodPoints+=meal.getFoodPoints();
    }

    public ArrayList getInventory() {
        return inventory;
    }

    public void transferItem(int index, ArrayList<Item> inventory) {

    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void getItem(int index) {

    }
}
