package me.meczka.structures;

import me.meczka.interfaces.Openable;
import me.meczka.items.Item;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Chest extends Structure implements Openable{
    private ArrayList<Item> inventory;
    public Chest( int x, int y, Image img)
    {
        super(x,y,img);
        inventory=new ArrayList<Item>();
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
        inventory.get(index);
    }
}
