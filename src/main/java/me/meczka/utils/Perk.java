package me.meczka.utils;

public class Perk {
    public static final int PHYSIC_DAMAGE_RESISTANCE=0;
    private int type,value;
    public Perk(int type,int value)
    {
        this.type=type;
        this.value=value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public static int affectedWeaponType(int type)
    {
        if(type==0)
        {
            return 0;
        }
            return -1;
    }
    public static double calculate(int type,double damage,double value)
    {
        switch (type)
        {
            case PHYSIC_DAMAGE_RESISTANCE:
            {
                value/=100;
                damage-=damage*value;
            }
        }
        return damage;
    }
}
