package me.meczka.managers;

/**
 * Created by Chilik on 25.01.2018.
 */
public class GameCalcuator {
    public static int pixelsToTiles(int pixels)
    {
        return pixels/50;
    }
    public static int tilesToPixels(int tiles)
    {
        return tiles*50;
    }
}
