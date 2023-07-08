package src.game.utils;

import game.entities.Dino;

//Component selects a random new theme after dinos score reaches a factor of 200
public class Repaint {

    //Bla bla
    public String theme;

    private Dino dino;
    public Repaint(){
        theme = "origin";
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }


}
