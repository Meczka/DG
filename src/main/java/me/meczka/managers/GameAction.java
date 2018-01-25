package me.meczka.managers;

/**
 * Created by Chilik on 24.01.2018.
 */
public class GameAction {

    /**
     Normalne działanie. Dopóki klawisz jest przyciśnięty,
     metoda isPressed() zwraca true.
     */
    public static final int NORMAL = 0;

    /**
     Początkowa obsługa przyciśnięcia. Metoda isPressed() zwraca
     true jedynie po pierwszym naciśnięciu klawisza.
     */
    public static final int DETECT_INITAL_PRESS_ONLY = 1;

    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;
    private int mousePressX;

    public int getMousePressX() {
        return mousePressX;
    }

    public void setMousePressX(int mousePressX) {
        this.mousePressX = mousePressX;
    }

    public int getMousePressY() {
        return MousePressY;
    }

    public void setMousePressY(int mousePressY) {
        MousePressY = mousePressY;
    }

    private int MousePressY;

    private String name;
    private int behavior;
    private int amount;
    private int state;

    /**
     Tworzenie nowego obiektu GameAction z działaniem NORMAL.
     */
    public GameAction(String name) {
        this(name, NORMAL);
    }

    /**
     Tworzenie nowego obiektu GameAction z podanym działaniem.
     */
    public GameAction(String name, int behavior) {
        this.name = name;
        this.behavior = behavior;
        reset();
    }

    /**
     Zwraca nazwę tego obiektu GameAction.
     */
    public String getName() {
        return name;
    }

    /**
     Kasuje stan obiektu GameAction, dzięki czemu zachowuje się jakby
     nie został naciśniety klawisz.
     */
    public void reset() {
        state = STATE_RELEASED;
        amount = 0;
    }

    /**
     Programowe przyciśnięcie w bieżącym obiekcie GameAction. To samo
     co wywołanie press() a następnie release().
     */
    public synchronized void tap() {
        press();
        release();
    }

    /**
     Sygnalizacja naciśnięcia klawisza.
     */
    public synchronized void press() {
        press(1);
    }

    /**
     Sygnalizacja naciśnięcia klawisza określoną liczbę razy,
     lub przesunięcia myszy o określony dystans.
     */
    public synchronized void press(int amount) {
        if (state != STATE_WAITING_FOR_RELEASE) {
            this.amount+=amount;
            state = STATE_PRESSED;
        }
    }

    /**
     Sygnalizuje zwolnienie klawisza.
     */
    public synchronized void release() {
        state = STATE_RELEASED;
    }

    /**
     Zwraca czy był naciśnięty klawisz od ostatniego sprawdzenia.
     */
    public synchronized boolean isPressed() {
        return (getAmount() != 0);
    }

    /**
     Dla klawiszy jest to liczba naciśnięć klawisza od ostatniego
     sprawdzenia.
     Dla przesunięcia myszy jest to odległość.
     */
    public synchronized int getAmount() {
        int retVal = amount;
        if (retVal != 0) {
            if (state == STATE_RELEASED) {
                amount = 0;
            }
            else if (behavior == DETECT_INITAL_PRESS_ONLY) {
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }
        }
        return retVal;
    }
}