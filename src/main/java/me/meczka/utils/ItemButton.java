package me.meczka.utils;

import me.meczka.interfaces.Executable;
import me.meczka.interfaces.ItemExecutable;
import me.meczka.items.Item;

import java.awt.*;

/**
 * Created by Patryk on 03.05.2018.
 */
public class ItemButton extends  Button{
    private Item relatedItem;
    public ItemButton(Image img, Item item, int x, int y, int type, Executable e)
    {
        super(img,x,y,type,e);
        relatedItem=item;
    }
    public void run()
    {
        super.action.execute();
    }
    public Item getRelatedItem()
    {
        return relatedItem;
    }
}
