package me.meczka.main;

import me.meczka.core.GameCore;
import me.meczka.core.TileMap;
import me.meczka.managers.GameCalcuator;
import me.meczka.managers.InputManager;
import me.meczka.managers.ResourceManager;

import java.awt.*;

/**
 * Created by Chilik on 24.01.2018.
 */
public class Main extends GameCore {
    private InputManager input;
    private ResourceManager resourceManager;
    public static void main(String[] args) {
        new Main().run();
    }
    public void init()
    {
        super.init();
        input = new InputManager(screen.getFullScreenWindow());
        resourceManager = new ResourceManager();
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
    }
}
