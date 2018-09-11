package me.meczka.structures;

import me.meczka.core.TileMap;
import me.meczka.interfaces.Clickable;
import me.meczka.items.Tile;

import java.awt.*;

/**
 * Created by Patryk on 30.07.2018.
 */
public class Door extends Structure implements Clickable{
    private Image def, other;
    private int state;
    private Tile boundTile;

    public Door(int x, int y, Image def, Image other, boolean isWalkable, Tile boundTile)
    {
        super(x,y,def);
        this.def=def;
        this.other=other;
        this.boundTile=boundTile;
        boundTile.setWalkable(isWalkable);
    }
    public void click()
    {
        boundTile.setWalkable(!boundTile.isWalkable());
        if(getImage()==def)
        {
            setImage(other);
        }
        else{
            setImage(def);
        }
    }
}
