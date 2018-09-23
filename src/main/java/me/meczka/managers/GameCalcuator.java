package me.meczka.managers;

import me.meczka.interfaces.Equipable;
import me.meczka.items.Item;
import me.meczka.items.Weapon;
import me.meczka.utils.Equipment;
import me.meczka.utils.Perk;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chilik on 25.01.2018.
 */
public class GameCalcuator {
    public static final int DIRECTON_X = 1,DIRECTION_Y=2,DIRECTION_minX=-1,DIRECTION_minY=-2;
    public static float pixelsToTiles(int pixels)
    {
        return pixels/50;
    }
    public static int tilesToPixels(int tiles)
    {
        return tiles*50;
    }
    public static int calculateDirection(int posA,int posB,int width)
    {
        if(posA==posB-1)
        {
            return  DIRECTON_X;
        }
        else if(posA==posB+1)
        {
            return DIRECTION_minX;
        }
        else if(posA==posB-width)
        {
            return DIRECTION_Y;
        }
        else if(posA==posB+width)
        {
            return DIRECTION_minY;
        }
        return 5;
    }
    public static int coordinatesToIndex(int x,int y,int width)
    {
        return y*width+x;
    }
    public static Weapon generateWeapon(JSONObject wp)
    {
        Weapon weapon = new Weapon(wp.getInt("range"),wp.getInt("damage"),wp.getInt("attackSpeed"),wp.getInt("type"),wp.getInt("damageType"),wp.getString("name"),
                ResourceManager.loadImage(wp.getString("name")),wp.getInt("weight"),wp.getString("description"));
        JSONArray Jperks = wp.getJSONArray("perks");
        ArrayList<Perk> perks = new ArrayList<>();
        for(int i=0;i<Jperks.length();i++)
        {
            JSONObject temp = Jperks.getJSONObject(i);
            perks.add(new Perk(temp.getInt("id"),temp.getInt("value")));
        }
        weapon.setPerks(perks);
        return weapon;
    }
    public static Item generateItem(JSONObject item)
    {
        if(item.getString("name").equalsIgnoreCase("chleb"))
        {

        }
        Item retItem = new Item(item.getString("name"),ResourceManager.loadImage(item.getString("name")),item.getInt("weight"),item.getString("description"));
        return retItem;
    }
    public static int calculateDamage(Equipment attacker, Equipment defender)
    {
        ArrayList<Perk> defenderPerks = getPerksFromEquipment(defender);
        double damage = attacker.getWeapon().getDamage();
        for(int i=0;i<defenderPerks.size();i++)
        {
            if(Perk.affectedWeaponType(defenderPerks.get(i).getType())==attacker.getWeapon().getWeaponType())
            {
                double temp = defenderPerks.get(i).getValue();
                damage = Perk.calculate(defenderPerks.get(i).getType(),damage,temp);
            }
        }
        System.out.println(damage);
        return (int) Math.round(damage);
    }
    public static ArrayList<Perk> getPerksFromEquipment(Equipment eq)
    {
        ArrayList<Perk> retVal = new ArrayList<>();
        retVal.addAll(eq.getWeapon().getPerks());
        if(eq.getHelmet()!=null)
        {
            retVal.addAll(eq.getHelmet().getPerks());
        }
        if(eq.getChest()!=null)
        {
            retVal.addAll(eq.getChest().getPerks());
        }
        if(eq.getPants()!=null)
        {
            retVal.addAll(eq.getPants().getPerks());
        }



        return retVal;
    }
}
