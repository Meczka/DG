package me.meczka.main;

import me.meczka.core.GameCore;
import me.meczka.core.TileMap;
import me.meczka.graphics.Sprite;
import me.meczka.interfaces.Openable;
import me.meczka.items.Item;
import me.meczka.items.Tile;
import me.meczka.managers.GameAction;
import me.meczka.managers.GameCalcuator;
import me.meczka.managers.InputManager;
import me.meczka.managers.ResourceManager;
import me.meczka.sprites.Creature;
import me.meczka.sprites.Player;
import me.meczka.structures.Structure;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Chilik on 24.01.2018.
 */
public class Main extends GameCore {
    private InputManager input;
    private ResourceManager resourceManager;
    private GameAction przod,lewo,prawo,tyl,myszka,ekwipunek,esc;
    private boolean isInventoryOpened = false;
    private ArrayList<Item> openInventory;
    private Player player;
    public static void main(String[] args) {
        new Main().run();
    }
    public void init()
    {
        super.init();
        input = new InputManager(screen.getFullScreenWindow());
        resourceManager = new ResourceManager();
        player=new Player(resourceManager.createPlayerAnim());
        resourceManager.loadStartingPlayerInv(player);
        player.setX(resourceManager.getMap().getSpawnX());
        player.setY(resourceManager.getMap().getSpawnY());
        createGameActions();
    }
    public void draw(Graphics2D g){
        TileMap map = resourceManager.getMap();
        g.fillRect(0,0,1920,1200);
        for(int y=0;y<map.getHeight();y++)
        {
            for(int x=0;x<map.getWidth();x++)
            {
                int width = GameCalcuator.tilesToPixels(x);
                int height = GameCalcuator.tilesToPixels(y);

                g.drawImage(map.getTile(x,y).getImg(), width, height, null);
            }
        }
        g.drawImage(player.getImage(),(int)player.getX(),(int)player.getY(),null);
        ArrayList structures=resourceManager.getStructures();
        /*Structure test = (Structure)structures.get(0);
        System.out.println(test.getX());*/
        Iterator iterator = structures.iterator();
        while(iterator.hasNext())
        {
            Structure s = (Structure) iterator.next();
            g.drawImage(s.getImage(),GameCalcuator.tilesToPixels(s.getX())+10,GameCalcuator.tilesToPixels(s.getY())+10,null);
        }
        if(isInventoryOpened)
        {
            resourceManager.generateInventory(openInventory,g);
        }
        g.dispose();
    }
    public void createGameActions(){
        przod = new GameAction("przod");
        lewo = new GameAction("lewo");
        prawo = new GameAction("prawo");
        tyl = new GameAction("tyl");
        myszka = new GameAction("myszka",GameAction.DETECT_INITAL_PRESS_ONLY);
        ekwipunek=new GameAction("inventory",GameAction.DETECT_INITAL_PRESS_ONLY);
        esc = new GameAction("escape",GameAction.DETECT_INITAL_PRESS_ONLY);
        //klawisze
        input.mapToKey(przod, KeyEvent.VK_W);
        input.mapToKey(lewo,KeyEvent.VK_A);
        input.mapToKey(prawo,KeyEvent.VK_D);
        input.mapToKey(tyl,KeyEvent.VK_S);
        input.mapToKey(ekwipunek,KeyEvent.VK_E);
        input.mapToKey(esc,KeyEvent.VK_ESCAPE);
        //myszka
        input.setMouseAction(myszka);
    }
    public void update(long elapsedTime)
    {
        chceckInput();
        updateCreature(player,elapsedTime);
        player.update(elapsedTime);
    }
    public void chceckInput()
    {
        if(przod.isPressed())
        {
            player.setVelY(-0.2f);
        }
        else if(tyl.isPressed())
        {
            player.setVelY(0.2f);
        }
        else
        {
            player.setVelY(0f);
        }
        if(lewo.isPressed())
        {
            player.setVelX(-0.2f);
        }
        else if(prawo.isPressed())
        {
            player.setVelX(0.2f);
        }
        else
        {
            player.setVelX(0f);
        }
        if(myszka.isPressed())
        {
            int x=myszka.getMousePressX();
            int y=myszka.getMousePressY();
            Iterator iterator= resourceManager.getOpenables().iterator();
            while(iterator.hasNext())
            {
                Openable openable = (Openable) iterator.next();
                int ox = GameCalcuator.tilesToPixels(openable.getX())+10;
                int oy = GameCalcuator.tilesToPixels(openable.getY())+10;
                if(x>=ox&&y>=oy&&x<=ox+30&y<=oy+30)
                {
                    int px = (int)GameCalcuator.pixelsToTiles((int)player.getX());
                    int py = (int)GameCalcuator.pixelsToTiles((int)player.getY());

                    int openx = openable.getX();
                    int openy = openable.getY();
                    int resx,resy;
                    resx = Math.min(openx,px)-Math.max(openx,px);
                    resy = Math.min(openy,py)-Math.max(openy,py);
                    if(resx==-1||resx==0&&resy==0||resy==-1) {
                        isInventoryOpened = true;
                        openInventory = openable.getInventory();
                    }
                }
            }
        }
        if(ekwipunek.isPressed())
        {
            isInventoryOpened=true;
            openInventory=player.getInventory();
        }
        if(esc.isPressed())
        {
            
        }
    }
    public void updateCreature(Creature creature,long elapsedTime)
    {
        float newX = creature.getX()+(creature.getVelX()*elapsedTime);
        float newY = creature.getY()+(creature.getVelY()*elapsedTime);
        if(checkWalkable((int)newX,(int)newY))
        {
            creature.setX(newX);
            creature.setY(newY);
        }
    }
    public boolean checkWalkable(int x,int y)
    {

        int x1=(int)GameCalcuator.pixelsToTiles(x);
        int y1=(int)GameCalcuator.pixelsToTiles(y);
        int x2=(int)GameCalcuator.pixelsToTiles(x+30);
        int y2=(int)GameCalcuator.pixelsToTiles(y+30);
        Tile tile = resourceManager.getMap().getTile(x1,y1);
        Tile tile1 = resourceManager.getMap().getTile(x2,y2);
        Tile tile2 = resourceManager.getMap().getTile(x2,y1);
        Tile tile3 = resourceManager.getMap().getTile(x1,y2);
        if(tile.isWalkable()&&tile1.isWalkable()&&tile2.isWalkable()&&tile3.isWalkable())
        {
           return true;
        }
        return false;
    }
}
