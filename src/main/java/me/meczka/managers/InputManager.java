package me.meczka.managers;

/**
 * Created by Chilik on 24.01.2018.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Chilik on 15.06.2017.
 */
public class InputManager implements KeyListener, MouseListener {
    private Component comp;
    private static final int NUM_KEY_CODES = 600;
    private GameAction[] keyActions = new GameAction[NUM_KEY_CODES];
    private GameAction mouseAction;
    public InputManager(Component comp) {
        this.comp = comp;
        comp.addKeyListener(this);
        comp.addMouseListener(this);
        comp.setFocusTraversalKeysEnabled(true);
        setInvisibleCursor();
    }
    public static String getKeyName(int keyCode) {
        return KeyEvent.getKeyText(keyCode);
    }

    public void keyPressed(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if(gameAction!=null)
        {
            gameAction.press();
        }
        e.consume();
    }
    public void setMouseAction(GameAction action)
    {
        mouseAction=action;
    }

    public void keyReleased(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if(gameAction!=null)
        {
            gameAction.release();
        }
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    public void setInvisibleCursor() {
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""),
                new Point(0, 0),
                "invisible");
    }

    public void mapToKey(GameAction action, int keyCode) {
        keyActions[keyCode] = action;
    }

    public void clearMap(GameAction gameAction) {
        for (int i = 0; i < keyActions.length; i++) {
            if (keyActions[i] == gameAction) {
                keyActions[i] = null;
            }
        }
        gameAction.reset();
    }
    public void resetAllGameActions() {
        for (int i = 0; i < keyActions.length; i++) {
            if (keyActions[i] != null) {
                keyActions[i].reset();
            }
        }
    }
    private GameAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (mouseAction != null) {
            mouseAction.press();
            mouseAction.setMousePressX(e.getX());
            mouseAction.setMousePressY(e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(mouseAction!=null)
        {
            mouseAction.release();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
