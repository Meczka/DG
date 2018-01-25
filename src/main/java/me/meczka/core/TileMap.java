package me.meczka.core;

import me.meczka.items.Tile;

/**
 * Created by Chilik on 24.01.2018.
 */
public class TileMap{
    private Tile[][] tilemap;
    int width,height;
    private int spawnX,spawnY;
    public TileMap(int width,int height)
    {
        tilemap = new Tile[width][height];
        this.width=width;
        this.height=height;
    }

    public Tile getTile(int x,int y)
    {
        return tilemap[x][y];
    }
    public void setTile(int x,int y,Tile tile)
    {
        tilemap[x][y]=tile;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }

}