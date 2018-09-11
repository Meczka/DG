package me.meczka.structures;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Structure {
    private int x,y;
    private Image image;
    public Structure(int x,int y,Image image)
    {
        this.x=x;
        this.y=y;
        this.image=image;
    }
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
