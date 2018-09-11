package me.meczka.items;

import me.meczka.utils.Perk;

import java.awt.*;
import java.util.ArrayList;

public class Armor {
    public final int TYPE_HELMET=0,TYPE_CHEST=1,TYPE_BOOTS=2;
    private int armor;
    private int durability;
    private int type;
    private Image icon;
    private ArrayList<Perk> perks;
    public Armor(int armor,int durability,int type,Image icon)
    {
        this.armor=armor;
        this.type=type;
        this.durability=durability;
        this.icon=icon;
        perks = new ArrayList<>();
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }
}
