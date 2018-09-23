package me.meczka.items;

import me.meczka.interfaces.Eatable;
import me.meczka.managers.ResourceManager;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Food extends Item implements Eatable {
    private String name;
    public Food(Image img,String name)
    {
        super("Chleb",img,ResourceManager.itemsInfo.get(name).getInt("weight"),ResourceManager.itemsInfo.get(name).getString("description"));
        this.name=name;
    }

    public int getFoodPoints() {
        return ResourceManager.itemsInfo.get(name).getInt("food_points");
    }
}
