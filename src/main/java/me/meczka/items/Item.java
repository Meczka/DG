package me.meczka.items;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Item {
    private String name;
    private int weight;
    private Image icon;
    public Item(String name, Image icon,int weight)
    {
        this.name=name;
        this.icon=icon;
        this.weight=weight;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
}
