package me.meczka.items;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Item {
    private String name,description;
    private int weight;
    private Image icon;
    public Item(String name, Image icon,int weight,String description)
    {
        this.name=name;
        this.icon=icon;
        this.weight=weight;
        this.description=description;
    }
    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
    public int getWeight(){return weight;}
    public String getDescription()
    {
        return description;
    }
    public String getName() {
        return name;
    }
}
