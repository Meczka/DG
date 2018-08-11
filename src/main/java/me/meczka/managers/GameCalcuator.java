package me.meczka.managers;

/**
 * Created by Chilik on 25.01.2018.
 */
public class GameCalcuator {
    public static final int DIRECTON_X = 1,DIRECTION_Y=2,DIRECTION_minX=-1,DIRECTION_minY=-2;
    public static float pixelsToTiles(int pixels)
    {
        return pixels/50;
    }
    public static int tilesToPixels(int tiles)
    {
        return tiles*50;
    }
    public static int calculateDirection(int posA,int posB,int width)
    {
        if(posA==posB-1)
        {
            return  DIRECTON_X;
        }
        else if(posA==posB+1)
        {
            return DIRECTION_minX;
        }
        else if(posA==posB-width)
        {
            return DIRECTION_Y;
        }
        else if(posA==posB+width)
        {
            return DIRECTION_minY;
        }
        return 5;
    }
    public static int coordinatesToIndex(int x,int y,int width)
    {
        return y*width+x;
    }
}
