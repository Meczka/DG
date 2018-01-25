package me.meczka.managers;

import me.meczka.core.TileMap;
import me.meczka.items.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chilik on 24.01.2018.
 */
public class ResourceManager {
    private Image a,b;
    private ArrayList<Tile> tiles;
    private TileMap map;
    private int mapnumber=0;
    public ResourceManager()
    {
        tiles = new ArrayList<Tile>();
        loadImages();
        loadTiles();
        mapnumber++;
        map=loadMap(mapnumber);
    }
    public TileMap loadMap(int number)
    {
        int height=0,width=0;
        try {
            int spawnx=0;
            int spawny=0;
            ArrayList lines = new ArrayList();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("maps/" + number + ".map"));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("#")) {

                    line = line.substring(1);
                    String parts[] = line.split(",");
                    spawnx=Integer.parseInt(parts[0]);
                    spawny=Integer.parseInt(parts[1]);

                } else {
                    lines.add(line);
                    width = Math.max(width, line.length());
                }
            }

            height = lines.size();
            TileMap map = new TileMap(width, height);
            map.setSpawnY(spawny);
            map.setSpawnX(spawnx);
            for (int y = 0; y < height; y++) {
                String line = (String) lines.get(y);
                for (int x = 0; x < width; x++) {
                    char ch = line.charAt(x);
                    int tile = ch - 'A';
                    if (tile >= 0 && tile <= tiles.size()) {
                        map.setTile(x, y, (Tile) tiles.get(tile).clone());
                    }
                }
            }

            return map;
        } catch (FileNotFoundException e) {
            System.out.println("Nie odnaleziono pliku");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public void loadNewMap()
    {
        mapnumber++;
        map=loadMap(mapnumber);
    }
    public TileMap getMap()
    {
        return map;
    }
    public void loadTiles()
    {

        tiles.add(new Tile(a));
        tiles.add(new Tile(b,true));
    }
    public void loadImages()
    {
        a =loadImage("a");
        b = loadImage("b");
    }
    public Image loadImage(String name)
    {
        return new ImageIcon("images/"+name+".png").getImage();
    }
}