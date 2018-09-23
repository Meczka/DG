package me.meczka.utils;

import me.meczka.items.Item;
import me.meczka.managers.GameCalcuator;
import me.meczka.managers.ResourceManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Drop {
    private HashMap<Item,Integer> dropy;
    public Drop(HashMap<Item,Integer> dropy)
    {
        this.dropy=dropy;
    }
    public ArrayList<Item> drop()
    {
        ArrayList<Item> drop= new ArrayList<>();
        Random random = new Random();
        for(Map.Entry<Item,Integer> entry : dropy.entrySet())
        {
            int rand = random.nextInt(100);
            if(rand<=entry.getValue())
            {
                drop.add(entry.getKey());
            }
        }
        return drop;
    }
    public static Drop generateDrop(JSONArray jsonArray, Map<String, JSONObject> itemsInfo)
    {
        Map<Item, Integer> dropy = new HashMap<>();

        for(int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            int chance = jsonObject.getInt("chance");
            dropy.put(GameCalcuator.generateItem(itemsInfo.get(name)),chance);
        }
        return new Drop((HashMap<Item,Integer>) dropy);
    }
}
