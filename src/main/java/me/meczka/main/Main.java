package me.meczka.main;

import me.meczka.core.GameCore;
import me.meczka.core.TileMap;
import me.meczka.creatures.MOB;
import me.meczka.graphics.Animation;
import me.meczka.graphics.Sprite;
import me.meczka.interfaces.Openable;
import me.meczka.items.Item;
import me.meczka.items.Tile;
import me.meczka.items.Weapon;
import me.meczka.managers.GameAction;
import me.meczka.managers.GameCalcuator;
import me.meczka.managers.InputManager;
import me.meczka.managers.ResourceManager;
import me.meczka.sprites.Creature;
import me.meczka.sprites.Player;
import me.meczka.sprites.Projectile;
import me.meczka.structures.Structure;
import me.meczka.utils.Button;
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
    public Player player;
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

        //Rysowanie struktur
        ArrayList structures=resourceManager.getStructures();
        /*Structure test = (Structure)structures.get(0);
        System.out.println(test.getX());*/
        Iterator iterator = structures.iterator();
        while(iterator.hasNext())
        {
            Structure s = (Structure) iterator.next();
            g.drawImage(s.getImage(),GameCalcuator.tilesToPixels(s.getX())+10,GameCalcuator.tilesToPixels(s.getY())+10,null);
        }
        //Rysowanie mobow
        ArrayList<MOB> creatures = resourceManager.getMOBS();
        for(int i=0;i<creatures.size();i++)
        {
            MOB c = creatures.get(i);
           g.drawImage(c.getImage(),(int)c.getX(),(int)c.getY(),null);
           g.drawString("HP "+c.getHp(),(int)c.getX(),c.getY()+70);
        }
        //Rysowanie projectile
        ArrayList<Projectile> projectiles = resourceManager.getProjectiles();
        for (int i=0;i<projectiles.size();i++)
        {
            Projectile projectile = projectiles.get(i);
            g.drawImage(projectile.getImage(),(int)projectile.getX(),(int)projectile.getY(),null);
        }
        g.setColor(Color.WHITE);
        g.drawString("HP: "+player.getHp(),100,800);
        ArrayList buttons = (ArrayList) resourceManager.getButtons();
        //Rysowanie ekwipunku
        if(isInventoryOpened)
        {
            resourceManager.generateInventory(openInventory,g);
            if(openInventory==player.getInventory())
            {
                resourceManager.generateEquipment(player.getEq(),g);
            }
        }
        if(resourceManager.isInfoOpened())
        {
            resourceManager.generateInfo(g);
        }
        for(int i=0;i<buttons.size();i++)
        {
            Button button = (Button) buttons.get(i);
            g.drawImage(button.getImg(),button.getX(),button.getY(),null);
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
    public synchronized void update(long elapsedTime)
    {
        chceckInput();
        updateCreature(player,elapsedTime);
        player.update(elapsedTime);
        ArrayList<MOB> mobs =  resourceManager.getMOBS();
        for(int i=0;i<mobs.size();i++)
        {
            MOB mob = mobs.get(i);
            mob.chceckTrigger((int)player.getX(),(int)player.getY(),resourceManager.getSasiedzi(),resourceManager.getMap().getWidth(),player);
            if(mob.getHp()<=0)
            {
                player.getInventory().addAll(mobs.get(i).getDrop().drop());
                mobs.remove(i);
            }
            updateCreature(mob,elapsedTime);
        }
        for(int i=0;i<resourceManager.getProjectiles().size();i++)
        {
            updateProjectile((Projectile) resourceManager.getProjectiles().get(i),elapsedTime);
        }
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
            boolean pressedSomwhere=false;
            int x=myszka.getMousePressX();
            int y=myszka.getMousePressY();
            System.out.println("press x: " + x+ " press y: "+y);
            if(isInventoryOpened)
            {
                int index = resourceManager.getIndexByPixels(x,y);
                if(index!=-1)
                {
                    if(openInventory.size()!=0&&index<openInventory.size()) {
                        if (openInventory != player.getInventory()) {

                            player.getInventory().add(openInventory.get(index));
                            openInventory.remove(index);
                            pressedSomwhere=true;
                        } else {
                            System.out.println(index);
                            System.out.println(openInventory.size());
                            ArrayList<Button> buttons  =(ArrayList<Button>) resourceManager.getButtons();
                            Button.removeAllButtonsOfType(buttons,Button.TYPE_INFO);
                            resourceManager.generateInfo(openInventory.get(index), index, this);
                            resourceManager.setInfoOpened(true);
                            pressedSomwhere=true;
                        }
                    }
                }
                //Sprawdzanie czy kliknięto przedmiot w eq
                if(openInventory==player.getInventory())
                {
                    index = resourceManager.getEqIndexByPixels(x,y);
                    if(index!=-1)
                    {
                        pressedSomwhere=true;
                        if(player.getEq().ItemByIndex(index)!=null) {
                            ArrayList<Button> buttons = (ArrayList<Button>) resourceManager.getButtons();
                            Button.removeAllButtonsOfType(buttons, Button.TYPE_INFO);
                            resourceManager.generateInfo(player.getEq().ItemByIndex(index), index, this);
                            resourceManager.setInfoOpened(true);
                        }
                    }
                }

            }
            ArrayList<MOB> mobs= resourceManager.getMOBS();
            for(int i=0;i<mobs.size();i++) {
                MOB mob = mobs.get(i);
                int mobX = (int) mob.getX();
                int mobY = (int) mob.getY();
                if (x >= mobX && y >= mobY && x <= mobX + 50 & y <= mobY + 50) {
                    int resx,resy;
                    int px = (int)GameCalcuator.pixelsToTiles((int)player.getX());
                    int py = (int)GameCalcuator.pixelsToTiles((int)player.getY());
                    mobX=(int)GameCalcuator.pixelsToTiles(mobX);
                    mobY=(int)GameCalcuator.pixelsToTiles(mobY);
                    resx = Math.min(mobX,px)-Math.max(mobX,px);
                    resy = Math.min(mobY,py)-Math.max(mobY,py);
                    int range = player.getEq().getWeapon().getRange();
                    if(resx<=range&&resx>=-range&&resy<=range&&resy>=-range) {
                        if(player.getEq().getWeapon().getWeaponType()== Weapon.WEAPON_TYPE_BLADE) {
                            player.attack(mob);
                        }

                    }
                }

            }
            ArrayList<Button> buttons = (ArrayList) resourceManager.getButtons();
            for(int i=0;i<buttons.size();i++)
            {
                if(x>buttons.get(i).getX()&&x<buttons.get(i).getX()+100&&y>buttons.get(i).getY()&&y<buttons.get(i).getY()+30)
                {
                    buttons.get(i).run();
                    pressedSomwhere=true;
                }
            }
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
                        resourceManager.setInfoOpened(false);
                        openInventory = openable.getInventory();
                        pressedSomwhere=true;
                    }
                }
            }
            if(!pressedSomwhere)
            {
                if(player.getEq().getWeapon()!=null) {
                    if (player.getEq().getWeapon().getWeaponType() == Weapon.WEAPON_TYPE_WAND) {
                        float deltaX = Math.abs(x - player.getX());
                        float deltaY = Math.abs(y - player.getY());
                        double temp = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
                        double s = Math.sqrt(temp);
                        double t = s / 0.2;
                        Animation anim = new Animation();
                        anim.addFrame(ResourceManager.loadImage("projectile"), 1000);
                        double velX = deltaX / t;
                        double velY = deltaY / t;
                        if (player.getX() > x) {
                            velX = -velX;
                        }
                        if (player.getY() > y) {
                            velY = -velY;
                        }
                        Projectile proj = new Projectile(anim, (float) velX, (float) velY, (int) player.getX(), (int) player.getY(), player.getEq());
                        System.out.println("Proj; velX " + velX + ";velY" + velY);
                        resourceManager.getProjectiles().add(proj);
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
            if(resourceManager.isInfoOpened())
            {
                resourceManager.setInfoOpened(false);
              /*  ArrayList<Button> buttons =(ArrayList<Button>) resourceManager.getButtons();
                Button.removeAllButtonsOfType(buttons,Button.TYPE_INFO);*/
            }
            else if(isInventoryOpened)
            {
                isInventoryOpened=false;
                openInventory=null;
            }
        }
    }
    public synchronized void updateProjectile(Projectile projectile, long elapsedTime)
    {
        float newX = projectile.getX()+(projectile.getVelX()*elapsedTime);
        float newY = projectile.getY()+(projectile.getVelY()*elapsedTime);
        if(checkWalkable((int)newX,(int)newY,10))
        {
            projectile.setX(newX);
            projectile.setY(newY);
        }
        else
        {
            resourceManager.getProjectiles().remove(projectile);
        }
        int projX = (int)GameCalcuator.pixelsToTiles((int)projectile.getX());
        int projY = (int)GameCalcuator.pixelsToTiles((int)projectile.getY());
        int px = (int)GameCalcuator.pixelsToTiles((int)player.getX());
        int py = (int)GameCalcuator.pixelsToTiles((int)player.getY());
        if(projX==px&&projY==py)
        {
            if(projectile.getAttacker()!=player.getEq()) {
                projectile.attack(player);
                resourceManager.getProjectiles().remove(projectile);
            }
        }
        ArrayList<MOB> mobs = resourceManager.getMOBS();
        for(int i=0;i<mobs.size();i++)
        {
            MOB mob = mobs.get(i);
            int mobX = (int)GameCalcuator.pixelsToTiles((int)mob.getX());
            int mobY = (int)GameCalcuator.pixelsToTiles((int)mob.getY());
            if(mobX==projX&&mobY==projY)
            {
                if(projectile.getAttacker()!=mob.getEq()) {
                    projectile.attack(mob);
                    resourceManager.getProjectiles().remove(projectile);
                    break;
                }

            }
        }
    }
    public void updateCreature(Creature creature,long elapsedTime)
    {
        float newX = creature.getX()+(creature.getVelX()*elapsedTime);
        float newY = creature.getY()+(creature.getVelY()*elapsedTime);
        if(checkWalkable((int)newX,(int)newY,30))
        {
            creature.setX(newX);
            creature.setY(newY);
        }
        else
        {
            if(creature instanceof MOB){
                MOB mob =(MOB) creature;
                mob.debug();
            }
        }
    }
    public boolean checkWalkable(int x,int y,int size)
    {

        int x1=(int)GameCalcuator.pixelsToTiles(x);
        int y1=(int)GameCalcuator.pixelsToTiles(y);
        int x2=(int)GameCalcuator.pixelsToTiles(x+size);
        int y2=(int)GameCalcuator.pixelsToTiles(y+size);
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
