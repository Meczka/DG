package me.meczka.sprites;

import me.meczka.graphics.Animation;
import me.meczka.graphics.Sprite;
import me.meczka.items.Weapon;
import me.meczka.managers.GameCalcuator;
import me.meczka.utils.Equipment;

public class Projectile extends Sprite {
    private Equipment attacker;
    public Projectile(Animation anim, float velX, float velY, int x, int y, Equipment attacker)
    {
        super(anim);
        setVelX(velX);
        setVelY(velY);
        setX(x);
        setY(y);
        this.attacker=attacker;
    }

    public Equipment getAttacker() {
        return attacker;
    }

    public void attack(Creature defender)
    {
        int damage = GameCalcuator.calculateDamage(attacker,defender.getEq());
        defender.setHp(defender.getHp()-damage);
    }
}
