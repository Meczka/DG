package me.meczka.utils;

import me.meczka.items.Armor;
import me.meczka.items.Weapon;

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
}
