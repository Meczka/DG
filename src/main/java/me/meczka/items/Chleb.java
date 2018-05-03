package me.meczka.items;

import me.meczka.interfaces.Eatable;
import me.meczka.managers.ResourceManager;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Chleb extends Item implements Eatable {
    public Chleb(Image img)
    {
        super("Chleb",img,ResourceManager.itemsInfo.get("chleb").getInt("weight"),ResourceManager.itemsInfo.get("chleb").getString("description"));
    }

    public int getFoodPoints() {
        return 2;
    }
}
