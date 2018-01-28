package me.meczka.sprites;

import me.meczka.graphics.Animation;
import me.meczka.interfaces.Eatable;
import me.meczka.interfaces.Openable;
import me.meczka.items.Item;

import java.util.ArrayList;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Player extends Creature    {
    private int hunger = 10;
    private int health = 20;
    public Player(Animation anim)
    {
        super(anim);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void eat(Eatable meal)
    {
        hunger+=meal.getFoodPoints();
    }

    public ArrayList getInventory() {
        return null;
    }

    public void transferItem(int index, ArrayList<Item> inventory) {

    }

    public void addItem(Item item) {

    }

    public void getItem(int index) {

    }
}
