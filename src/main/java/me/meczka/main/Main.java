package me.meczka.main;

import me.meczka.core.GameCore;
import me.meczka.core.TileMap;
import me.meczka.graphics.Sprite;
import me.meczka.items.Tile;
import me.meczka.managers.GameAction;
import me.meczka.managers.GameCalcuator;
import me.meczka.managers.InputManager;
import me.meczka.managers.ResourceManager;
import me.meczka.sprites.Creature;
import me.meczka.sprites.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Chilik on 24.01.2018.
 */
public class Main extends GameCore {
    private InputManager input;
    private ResourceManager resourceManager;
    private GameAction przod,lewo,prawo,tyl;
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
        player.setX(resourceManager.getMap().getSpawnX());
        player.setY(resourceManager.getMap().getSpawnY());
        createGameActions();
    }
    public void draw(Graphics2D g){
        TileMap map = resourceManager.getMap();
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
        g.dispose();
    }
    public void createGameActions(){
        przod = new GameAction("przod");
        lewo = new GameAction("lewo");
        prawo = new GameAction("prawo");
        tyl = new GameAction("tyl");
        input.mapToKey(przod, KeyEvent.VK_W);
        input.mapToKey(lewo,KeyEvent.VK_A);
        input.mapToKey(prawo,KeyEvent.VK_D);
        input.mapToKey(tyl,KeyEvent.VK_S);
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
