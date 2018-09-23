package me.meczka.creatures;

import me.meczka.graphics.Animation;
import me.meczka.managers.GameCalcuator;
import me.meczka.sprites.Creature;
import me.meczka.sprites.Player;
import me.meczka.utils.Drop;
import me.meczka.utils.Equipment;

import java.time.temporal.ValueRange;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class MOB extends Creature {
    public static final int TRIGGER_RANGE=5;
    private int hp;
    private boolean isTiggered=false,isOnCooldown=false;
    private Vector droga,droga1;
    private Timer timer;
    private Drop drop;
    public MOB(Animation anim, int hp, Equipment eq, Drop drop)
    {
        super(anim,true,eq,hp);
        this.hp=hp;
        timer = new Timer();
        this.drop=drop;
    }

    public Drop getDrop()
    {
        return drop;
    }
    public boolean chceckTrigger(int playerx, int playery, Vector[] sasiedzi,int mapWidth,Player player)
    {
        if(!isTiggered) {
            playerx = (int) GameCalcuator.pixelsToTiles(playerx);
            playery = (int) GameCalcuator.pixelsToTiles(playery);
            int x = (int) GameCalcuator.pixelsToTiles((int) getX());
            int y = (int) GameCalcuator.pixelsToTiles((int) getY());
            int x1 = playerx - x;
            int y1 = playery - y;
            if (x1 < MOB.TRIGGER_RANGE && x1 > -MOB.TRIGGER_RANGE) {
                isTiggered = true;
                return true;
            }
        }
        if(isTiggered)
        {
            playerx = (int) GameCalcuator.pixelsToTiles(playerx);
            playery = (int) GameCalcuator.pixelsToTiles(playery);
            int x = (int) GameCalcuator.pixelsToTiles((int) getX());
            int y = (int) GameCalcuator.pixelsToTiles((int) getY());
            boolean[] odwiedzone = new boolean[sasiedzi.length];
            droga = new Vector<>();
            int playerPos = GameCalcuator.coordinatesToIndex(playerx,playery,mapWidth);
            int myPos = GameCalcuator.coordinatesToIndex(x,y,mapWidth);
            if(myPos==playerPos)
            {
                setVelX(0f);
                setVelY(0f);
                attack(player);
                return true;
            }
            DFS(playerPos,myPos,odwiedzone,sasiedzi);
            int pos2 = (int)droga1.get(droga1.size()-2);
            int direction = GameCalcuator.calculateDirection(myPos,pos2,mapWidth);
            if(direction==GameCalcuator.DIRECTION_minX)
            {
                setVelX(-0.1f);
                setVelY(0f);
            }
            else if(direction==GameCalcuator.DIRECTON_X)
            {
                setVelX(0.1f);
                setVelY(0f);
            }
            else if(direction==GameCalcuator.DIRECTION_Y)
            {
                setVelY(0.1f);
                setVelX(0f);
            }
            else if(direction==GameCalcuator.DIRECTION_minY)
            {
                setVelY(-0.1f);
                setVelX(0f);
            }
        }
        return false;
    }
    public synchronized void attack(Player player)
    {
        if(!isOnCooldown) {
            System.out.println(player.getHp());
            player.setHp(player.getHp()- getEq().getWeapon().getDamage());
            isOnCooldown=true;

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isOnCooldown = false;
                }
            }, getEq().getWeapon().getAttackSpeed());
        }
    }
    private void DFS(int pos,int cel,boolean[] odwiedzone,Vector[] sasiedzi)
    {
        odwiedzone[pos] = true;
        droga.add(pos);
        if(pos==cel)
        {
            droga1=(Vector) droga.clone();
        }
        for(int i=0;i<sasiedzi[pos].size();i++)
        {
            if(!odwiedzone[(int)sasiedzi[pos].get(i)])
            {
                DFS((int)sasiedzi[pos].get(i),cel,odwiedzone,sasiedzi);
            }
        }
        droga.remove(droga.size()-1);

    }
    public void debug()
    {
        double newX = getX()/50;
        setX((int)Math.floor(newX)*50);
        double newY = getY()/50;
        setY((int)Math.floor(newY)*50);

   }

}
