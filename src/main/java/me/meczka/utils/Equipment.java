package me.meczka.utils;

import me.meczka.items.Armor;
import me.meczka.items.Item;
import me.meczka.items.Weapon;

import java.util.ArrayList;

public class Equipment {
    private Weapon weapon;
    private Armor helmet,chest,pants;
    public void setWeapon(Weapon weapon)
    {
        this.weapon=weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getHelmet() {
        return helmet;
    }

    public void setHelmet(Armor helmet) {
        this.helmet = helmet;
    }

    public Armor getChest() {
        return chest;
    }

    public void setChest(Armor chest) {
        this.chest = chest;
    }

    public Armor getPants() {
        return pants;
    }

    public void setPants(Armor pants) {
        this.pants = pants;
    }
    public Item ItemByIndex(int index)
    {
        switch (index)
        {
            case 0:
                return weapon;
            case 1:
                return helmet;
            case 2:
                return chest;
            case 3:
                return pants;
        }
        return null;
    }
    public ArrayList<Item> asItemList()
    {
        ArrayList<Item> eq = new ArrayList<Item>();
        eq.add(weapon);
        eq.add(helmet);
        eq.add(chest);
        eq.add(pants);
        return eq;
    }
    public void remove(Item item)
    {
        if(item==weapon)
        {
            weapon=null;
        }
        else if(item==helmet)
        {
            helmet=null;
        }
        else if(item==chest)
        {
            chest=null;
        }
        else if(item==pants)
        {
            pants=null;
        }
    }
}
