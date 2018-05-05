package me.meczka.utils;

import me.meczka.interfaces.Executable;

import java.awt.*;

/**
 * Created by Patryk on 03.05.2018.
 */
public abstract class Button {
    public static final int TYPE_INFO = 0;
    protected Executable action;
    private Image img;
    private int x,y,type;
    public Button(Image img,int x,int y,int type,Executable action)
    {
        this.img=img;
        this.x=x;
        this.y=y;
        this.type=type;
        this.action=action;
    }

    public Image getImg() {
        return img;
    }
    public abstract void run();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

}
