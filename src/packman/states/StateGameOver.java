/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Button;
import packman.Event;
import packman.GameModes;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 *
 * @author campbell
 */
public class StateGameOver implements State {

    private final Button reset = new Button();
    private int cherries;
    private int lifeAverage;
    private final int stat_cherries;
    private final int stat_lifeCounter;
    private final int stat_lifeChanges;
    private final int stat_lifeAverage;
    private final GameModes stat_mode;

    public StateGameOver(int stat_cherries, int stat_lifeCounter, int stat_lifeChanges, GameModes stat_mode) {
        this.stat_cherries = stat_cherries;
        this.stat_lifeCounter = stat_lifeCounter;
        this.stat_lifeChanges = stat_lifeChanges;
        this.stat_mode = stat_mode;

        stat_lifeAverage = stat_lifeCounter / stat_lifeChanges;
    }

    @Override
    public void setup(Main main, State s) {
    }

    @Override
    public void draw(Main main) {
        main.background(0, 0, 0);
        main.fill(255, 255, 255);
        int textSize = 50;
        main.textSize(textSize);
        String s = "Game\nOver.\n";
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2 - textSize * 3);

        s = "\nCherries eaten:\t" + cherries;
        if (cherries >= stat_cherries) {
            s += "\nAverage Lives:\t" + lifeAverage;
        } else {
            s += "\n";
        }
        if (lifeAverage >= stat_lifeAverage) {
            s += "\nDifficulty: " + stat_mode.toString().replace('_', ' ');
        } else {
            s += "\n";
        }
        int y = main.height / 2 - textSize;
        main.textSize(30);
        main.text(s, (main.width - main.textWidth(s)) / 2, y);

        if (cherries < stat_cherries) {
            cherries += (stat_cherries - cherries) / 100;
            cherries++;
        } else if (lifeAverage < stat_lifeAverage) {
            lifeAverage += (stat_lifeAverage - lifeAverage) / 500;
            lifeAverage++;
        }

        s = "BACK TO MENU";
        reset.setHeight(main.height / 14);
        main.textSize(reset.getHeight());
        reset.setWidth(main.textWidth(s) + 100);
        reset.setX((main.width - reset.getWidth()) / 2);
        reset.setY(main.height / 2 + 150);

        if (reset.isInside(main.mouseX, main.mouseY)) {
            main.fill(150, 150, 150);
        } else {
            main.fill(0, 0, 0);
        }
        main.stroke(255, 255, 255);

        reset.draw(main);

        main.fill(255, 255, 255);
        main.text(s, reset.getX() + 50, reset.getY() + reset.getHeight() - 5);
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
        if (ev instanceof EventMouse.EventMouseClicked && reset.isInside(main.mouseX, main.mouseY)) {
            main.frame.setResizable(false);
            main.popState(1);
        }
    }

}
