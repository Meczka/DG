package me.meczka.utils;

import me.meczka.items.Weapon;

public class Equipment {
    private Weapon weapon;
    public void setWeapon(Weapon weapon)
    {
        this.weapon=weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
