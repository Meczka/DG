package me.meczka.graphics;

import java.awt.*;

/**
 * Created by Chilik on 25.01.2018.
 */
public class Sprite {
    protected Animation anim;

    private float x,y,velX,velY;
    public Sprite(Animation anim)
    {
        this.anim=anim;
    }
    public void update(long elapsedTime)
    {
    /*    x+=velX*elapsedTime;
        y+=velY*elapsedTime;*/
        anim.update(elapsedTime);
    }
    public Image getImage()
    {
        return anim.getImage();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
    public Object clone()
    {
        return new Sprite(anim);
    }
}
