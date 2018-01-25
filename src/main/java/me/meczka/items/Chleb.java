package me.meczka.items;

import me.meczka.interfaces.Eatable;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Chleb extends Item implements Eatable {
    public Chleb(Image img)
    {
        super("Chleb",img,1);
    }

    public int getFoodPoints() {
        return 2;
    }
}
