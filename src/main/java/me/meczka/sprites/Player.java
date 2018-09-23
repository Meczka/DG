package me.meczka.sprites;

import me.meczka.creatures.MOB;
import me.meczka.graphics.Animation;
import me.meczka.graphics.Sprite;
import me.meczka.interfaces.Eatable;
import me.meczka.interfaces.Openable;
import me.meczka.items.Item;
import me.meczka.managers.GameCalcuator;
import me.meczka.utils.Equipment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Player extends Creature {
    private int foodPoints = 10;
    private ArrayList inventory;
    private boolean isOnColldown=false;
    private Timer timer;
    public Player(Animation anim)
    {

        super(anim,new Equipment(),20);
        inventory = new ArrayList();

        timer = new Timer();
    }

    public void attack(MOB mob)
    {
        if(!isOnColldown) {
            mob.setHp(mob.getHp() - GameCalcuator.calculateDamage(getEq(),mob.getEq()));
            isOnColldown=true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isOnColldown=false;
                }
            },getEq().getWeapon().getAttackSpeed());
        }
    }
    public void eat(Eatable meal)
    {
        foodPoints+=meal.getFoodPoints();
    }
    public int getFoodPoints()
    {
        return foodPoints;
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
