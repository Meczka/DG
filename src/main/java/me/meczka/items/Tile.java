package me.meczka.items;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Tile{
    private Image image;
    boolean walkable;
    public Tile(Image image)
    {
        this(image,false);
    }
    public Tile(Image image,boolean walkable)
    {
        this.image=image;
        this.walkable=walkable;
    }
    public void setWalkable(boolean walkable)
    {
        this.walkable=walkable;
    }
    public boolean isWalkable()
    {
        return walkable;
    }
    public Image getImg()
    {
        return image;
    }
    public Object clone()
    {
        return  new Tile(image,walkable);
    }
}
