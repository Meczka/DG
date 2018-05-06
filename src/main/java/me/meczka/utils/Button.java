package me.meczka.utils;

import me.meczka.interfaces.Executable;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Patryk on 03.05.2018.
 */
public class Button {
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
    public void run()
    {
        action.execute();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }
    public static void removeAllButtonsOfType(ArrayList<Button> list,int type)
    {

        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getType()==type)
            {
                list.remove(i);
                i--;
            }
        }
    }
}
