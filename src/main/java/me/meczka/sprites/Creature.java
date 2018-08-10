package me.meczka.sprites;

import me.meczka.graphics.Animation;
import me.meczka.graphics.Sprite;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Creature extends Sprite{
    private boolean isHostile;
    public Creature(Animation anim)
    {
        this(anim,false);
    }
    public Creature(Animation anim,boolean isHostile) {
        super(anim);
        this.isHostile=isHostile;
    }
    public boolean isHostile()
    {
        return  isHostile;
    }
}
