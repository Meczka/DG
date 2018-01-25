package me.meczka.managers;

import me.meczka.core.TileMap;
import me.meczka.graphics.Animation;
import me.meczka.interfaces.Openable;
import me.meczka.items.Chleb;
import me.meczka.items.Item;
import me.meczka.items.Tile;
import me.meczka.structures.Chest;
import me.meczka.structures.Structure;

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
    //obrazki kafelkow
    private Image a,b;
    //obrazek playera
    private Image player;
    //obrazki struktur
    private Image chestimg;
    //obrazki przedmiotow
    private Image chlebimg;
    private ArrayList<Tile> tiles;
    private ArrayList<Structure> structures;
    private ArrayList<Openable> openables;
    private TileMap map;
    private int mapnumber=0;
    public ResourceManager()
    {
        tiles = new ArrayList<Tile>();
        structures = new ArrayList<Structure>();
        openables = new ArrayList<Openable>();
        loadImages();
        loadTiles();
        mapnumber++;
        map=loadMap(mapnumber);
    }
    public ArrayList getStructures()
    {
        return structures;
    }
    public ArrayList getOpenables()
    {
        return openables;
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
                    spawnx=GameCalcuator.tilesToPixels(Integer.parseInt(parts[0]))+10;
                    spawny=GameCalcuator.tilesToPixels(Integer.parseInt(parts[1]))+10;

                }else if(line.startsWith("!"))
                {
                    line=line.substring(1);
                    String[] parts = line.split(",");
                    if(parts[0].equalsIgnoreCase("chest"))
                    {
                        Chest chest = new Chest(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),chestimg);
                        for(int i=3;i<parts.length;i++)
                        {
                            if(parts[3]=="chleb")
                            {
                                chest.addItem(new Chleb(chlebimg));
                            }
                        }
                        structures.add(chest);
                        openables.add(chest);
                    }
                }
                else {
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
        player = loadImage("player");
        chestimg = loadImage("chest");
        chlebimg = loadImage("chleb");
    }
    public Animation createPlayerAnim()
    {
        Animation anim = new Animation();
        anim.addFrame(player,50);
        return anim;
    }
    public Image loadImage(String name)
    {
        return new ImageIcon("images/"+name+".png").getImage();
    }
}