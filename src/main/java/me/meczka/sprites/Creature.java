package me.meczka.sprites;

import me.meczka.graphics.Animation;
import me.meczka.graphics.Sprite;
import me.meczka.utils.Equipment;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Creature extends Sprite{
    private boolean isHostile;
    private Equipment eq;
    private int hp;
    public Creature(Animation anim,Equipment eq,int hp)
    {
        this(anim,false,eq,hp);
    }
    public Creature(Animation anim,boolean isHostile,Equipment eq,int hp) {
        super(anim);
        this.isHostile=isHostile;
        this.eq=eq;
        this.hp=hp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Equipment getEq() {
        return eq;
    }

    public boolean isHostile()
    {
        return  isHostile;
    }
}
