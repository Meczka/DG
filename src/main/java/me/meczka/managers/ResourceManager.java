package me.meczka.managers;


import me.meczka.core.TileMap;
import me.meczka.creatures.MOB;
import me.meczka.graphics.Animation;
import me.meczka.interfaces.Eatable;
import me.meczka.interfaces.Openable;
import me.meczka.items.Chleb;
import me.meczka.items.Item;
import me.meczka.items.Tile;
import me.meczka.items.Weapon;
import me.meczka.main.Main;
import me.meczka.sprites.Creature;
import me.meczka.sprites.Player;
import me.meczka.structures.Chest;
import me.meczka.structures.Structure;
import me.meczka.utils.Equipment;
import org.json.JSONArray;
import org.json.JSONObject;
import me.meczka.utils.Button;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Chilik on 24.01.2018.
 */
public class ResourceManager {
    //obrazki kafelkow
    private Image a, b;
    public static final Map<String, JSONObject> itemsInfo = new HashMap<String, JSONObject>();
    public static final Map<String,JSONObject> weaponsInfo = new HashMap<>();
    public static final Map<String,JSONObject> mobsInfo = new HashMap<>();
    //obrazek playera
    private Image player;
    //obrazki struktur
    private Image chestimg;
    //obrazki przedmiotow
    private Image chlebimg;
    private boolean isInfoOpened=false;
    //obrazki przyciskow
    private Image button_zjedz,button_wyrzuc;
    //ratimg(temp)
    private Image ratimg;
    //Listy
    private ArrayList<Tile> tiles;
    private ArrayList<Structure> structures;
    private ArrayList<Openable> openables;
    private ArrayList<Creature> creatures;
    private Vector[] sasiedzi;
    private List<Button> buttons;
    private TileMap map;
    private int infoIndex;
    private Item infoItem;
    private int mapnumber = 0;

    public ResourceManager() {
        tiles = new ArrayList<Tile>();
        structures = new ArrayList<Structure>();
        openables = new ArrayList<Openable>();
        buttons = new ArrayList<Button>();
        creatures = new ArrayList<Creature>();

        loadImages();
        loadTiles();
        loadInfo();
        mapnumber++;
        map = loadMap(mapnumber);

        loadMOBS(mapnumber);
    }

    public boolean isInfoOpened() {
        return isInfoOpened;
    }

    public void setInfoOpened(boolean infoOpened) {
        isInfoOpened = infoOpened;
    }

    public void loadStartingPlayerInv(Player player) {
        player.addItem(new Chleb(chlebimg));
    }

    public ArrayList getStructures() {
        return structures;
    }

    public ArrayList getOpenables() {
        return openables;
    }

    //Laduje Informacje
    public void loadInfo() {
        try {
            File file = new File("info/info.json");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String str = new String(data, "UTF-8");
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                itemsInfo.put(temp.getString("name"), temp);
            }
            jsonArray = jsonObject.getJSONArray("mobs");
            for(int i=0;i < jsonArray.length();i++)
            {
                JSONObject temp = jsonArray.getJSONObject(i);
                mobsInfo.put(temp.getString("name"),temp);
            }
            jsonArray = jsonObject.getJSONArray("weapons");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject temp = jsonArray.getJSONObject(i);
                weaponsInfo.put(temp.getString("name"),temp);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadMOBS(int number)
    {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("maps/"+number+".map"));
            while(true)
            {
                String line = bufferedReader.readLine();
                if(line==null)
                {
                    break;
                }
                if(line.startsWith("$"))
                {
                    line=line.substring(1);
                    String parts[] = line.split(",");
                    Animation anim = new Animation();
                    anim.addFrame(ratimg,50);
                    Equipment eq = new Equipment();
                    String weaponName = mobsInfo.get(parts[0]).getString("weapon");
                    JSONObject wp = weaponsInfo.get(weaponName);
                    eq.setWeapon(new Weapon(wp.getInt("range"),wp.getInt("damage"),wp.getInt("attackSpeed"),wp.getInt("type")));
                    MOB mob = new MOB(anim,mobsInfo.get(parts[0]).getInt("hp"),eq);
                    mob.setX(GameCalcuator.tilesToPixels(Integer.parseInt(parts[1])));
                    mob.setY(GameCalcuator.tilesToPixels(Integer.parseInt(parts[2])));
                    creatures.add(mob);
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public TileMap loadMap(int number) {
        int height = 0, width = 0;
        try {
            int spawnx = 0;
            int spawny = 0;
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
                    spawnx = GameCalcuator.tilesToPixels(Integer.parseInt(parts[0])) + 10;
                    spawny = GameCalcuator.tilesToPixels(Integer.parseInt(parts[1])) + 10;

                } else if (line.startsWith("!")) {
                    line = line.substring(1);
                    String[] parts = line.split(",");
                    if (parts[0].equalsIgnoreCase("chest")) {
                        Chest chest = new Chest(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), chestimg);
                        for (int i = 3; i < parts.length; i++) {
                            if (parts[3].equalsIgnoreCase("chleb")) {
                                chest.addItem(new Chleb(chlebimg));
                            }
                        }
                        structures.add(chest);
                        openables.add(chest);
                    }
                } else if(!line.startsWith("$")) {
                    lines.add(line);
                    width = Math.max(width, line.length());
                }
            }
                height = lines.size();
                sasiedzi = new Vector[height * width];
                for(int i=0;i<sasiedzi.length;i++)
                {
                    sasiedzi[i] = new Vector();
                }
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
                            if (tiles.get(tile).isWalkable()) {
                                int pos = GameCalcuator.coordinatesToIndex(x,y,width);
                                if(x!=0) {
                                    if (map.getTile(x - 1, y).isWalkable()) {
                                        sasiedzi[pos].add(pos-1);
                                        sasiedzi[pos - 1].add(pos);
                                    }
                                }
                                if(y!=0) {

                                    if (map.getTile(x, y - 1).isWalkable()) {
                                        sasiedzi[pos - width].add(pos);
                                        sasiedzi[pos].add(pos - width);
                                    }
                                }
                            }
                        }
                    }
                }

            return map;
        } catch (FileNotFoundException e) {
            System.out.println("Nie odnaleziono pliku");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadNewMap() {
        mapnumber++;
        map = loadMap(mapnumber);
    }

    public TileMap getMap() {
        return map;
    }
    public Vector[] getSasiedzi()
    {
        return sasiedzi;
    }
    public void loadTiles() {
        tiles.add(new Tile(a));
        tiles.add(new Tile(b, true));
    }

    public void loadImages() {
        a = loadImage("a");
        b = loadImage("b");
        player = loadImage("player");
        chestimg = loadImage("chest");
        chlebimg = loadImage("chleb");
        button_zjedz = loadImage("button_zjedz");
        button_wyrzuc = loadImage("button_wyrzuc");
        ratimg = loadImage("rat");
    }

    public Animation createPlayerAnim() {
        Animation anim = new Animation();
        anim.addFrame(player, 50);
        return anim;
    }

    public void generateInventory(ArrayList<Item> inventory, Graphics2D g) {

        int offset = 100;
        for (int i = 0; i < inventory.size(); i++) {
            g.setColor(Color.GRAY);
            g.fillRect(0, offset, 300, 50);
            Item item = inventory.get(i);
            g.setColor(Color.black);
            g.drawImage(item.getIcon(), 10, offset + 10, null);
            g.drawString(item.getName(), 50, offset + 40);
            offset += 50;
        }

    }
    public void generateInfo(Item item,int index,Main main)
    {
        infoIndex=index;
        infoItem=item;
        int leftOffset=300;
        int offset = 100+(infoIndex*50);
        offset+=60;
        buttons.add(new Button(button_wyrzuc,leftOffset,offset+20,Button.TYPE_INFO,()->{
            main.player.getInventory().remove(item);
            isInfoOpened=false;
            Button.removeAllButtonsOfType((ArrayList<Button>) buttons,Button.TYPE_INFO);

        }));
        leftOffset+=100;
        if(infoItem instanceof Eatable) {
            buttons.add(new Button(button_zjedz,leftOffset,offset+20,Button.TYPE_INFO,()->
            {
                main.player.eat((Eatable) item);
                main.player.getInventory().remove(item);
                Button.removeAllButtonsOfType((ArrayList<Button>)buttons,Button.TYPE_INFO);
                isInfoOpened=false;
            }
            ));
            //buttons.add(new ItemButton(button_zjedz,infoItem,leftOffset,offset+20,Button.TYPE_INFO));
        }
    }
    public void generateInfo(Graphics2D g)
    {
        int leftOffset=300;
        int offset = 100+(infoIndex*50);
        g.setColor(Color.GRAY);
        g.fillRect(leftOffset,offset,300,100);
        g.setColor(Color.black);
        g.drawString(infoItem.getDescription(),leftOffset,offset+20);
        g.drawString("Waga: "+infoItem.getWeight(),leftOffset,offset+40);
        offset+=40;


        if(infoItem instanceof Eatable)
        {
            g.drawString("Punkty jedzenia: "+ ((Eatable) infoItem).getFoodPoints(),300,offset+20);
            offset+=20;
        }
        //rysowanie buttonow


    }
    public List<Button> getButtons()
    {
        return buttons;
    }
    public int getIndexByPixels(int x,int y)
    {
        if(x>=0&&x<=300)
        {
            y-=100;
            return (y/50);
        }
        return -1;
    }

    public Image loadImage(String name)
    {
        return new ImageIcon("images/"+name+".png").getImage();
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }
}