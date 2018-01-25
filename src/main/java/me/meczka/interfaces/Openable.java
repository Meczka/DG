package me.meczka.interfaces;

import me.meczka.items.Item;
import me.meczka.structures.Structure;

import java.util.ArrayList;

/**
 * Created by Chilik on 25.01.2018.
 */
public interface Openable{
    ArrayList getInventory();
    void transferItem(int index,ArrayList<Item> inventory);
    void addItem(Item item);
    void getItem(int index);
    int getX();
    int getY();
}
