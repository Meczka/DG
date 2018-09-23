package me.meczka.items;

import me.meczka.interfaces.Equipable;
import me.meczka.sprites.Player;
import me.meczka.utils.Equipment;
import me.meczka.utils.Perk;

import java.awt.*;
import java.util.ArrayList;

public class Weapon extends Item implements Equipable {
    public static final int WEAPON_TYPE_BLADE = 0,WEAPON_TYPE_WAND = 1,WEAPON_TYPE_BOW = 2;
    public static final int DAMAGE_TYPE_PHYSIC = 0,DAMAGE_TYPE_FIRE = 1,DAMAGE_TYPE_WATER = 2;
    private ArrayList<Perk> perks;
    private int range,damage,attackSpeed,weaponType,damageType;
    public Weapon(int range,int damage,int attackSpeed,int weaponType,int damageType,String name,Image icon,int weight,String description)
    {
        super(name,icon,weight,description);
        this.range=range;
        this.damage=damage;
        this.attackSpeed=attackSpeed;
        this.weaponType=weaponType;
        this.damageType=damageType;
        perks = new ArrayList<>();
    }

    public int getDamage() {
        return damage;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }
    public void equip(Player player)
    {
        Equipment eq = player.getEq();
        if(eq.getWeapon()!=null)
        {
            player.addItem(eq.getWeapon());
        }
        eq.setWeapon(this);
    }

    public int getRange() {
        return range;
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }

    public int getWeaponType() {
        return weaponType;
    }
}
