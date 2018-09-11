package me.meczka.interfaces;

import me.meczka.sprites.Player;
import me.meczka.utils.Perk;

import java.util.ArrayList;

public interface Equipable {
    void equip(Player player);
    ArrayList<Perk> getPerks();
};
