package me.meczka.items;

import me.meczka.utils.Perk;

import java.awt.*;
import java.util.ArrayList;

public class Armor extends Item{
    public final int TYPE_HELMET=0,TYPE_CHEST=1,TYPE_BOOTS=2;
    private int armor;
    private int durability;
    private int type;
    private ArrayList<Perk> perks;
    public Armor(int armor,int durability,int type,Image icon,String name,int weight,String description)
    {
        super(name,icon,weight,description);
        this.armor=armor;
        this.type=type;
        this.durability=durability;
        perks = new ArrayList<>();
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }
}
