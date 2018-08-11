package me.meczka.items;

import java.awt.*;

public class Weapon {
    public static final int WEAPON_TYPE_BLADE = 0;
    private int range,damage,attackSpeed,weaponType;
    private int damageType;
    public Weapon(int range,int damage,int attackSpeed,int weaponType)
    {
        this.range=range;
        this.damage=damage;
        this.attackSpeed=attackSpeed;
        this.weaponType=weaponType;
    }

    public int getDamage() {
        return damage;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getWeaponType() {
        return weaponType;
    }
}
