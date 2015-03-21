/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Button;
import packman.Event;
import packman.GameMode;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 * Game-over state. Shown when the player dies or when the player presses "End
 * game" in the {@link StatePause pause screen}.
 *
 * @author campbell
 */
public class StateGameOver implements State {

    private final Button reset = new Button(); // go back to the main menu
    private int cherries; // counting-up number of cherries eaten
    private int lifeAverage; // counting-up number of average lives

    private final int stat_cherries; // actual number of cherries eaten
    private final int stat_lifeCounter; // sum of the number of lives packmann
    // has each time he gains or looses them.

    private final int stat_lifeChanges; // number of times packmann gains or loses lifes.
    private final int stat_lifeAverage; // average number of lives. The lifeCounter/lifeChanges

    private final GameMode stat_mode; // The gamemode the user was playing in.

    public StateGameOver(int stat_cherries, int stat_lifeCounter, int stat_lifeChanges, GameMode stat_mode) {
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
        // set the background and text colour
        main.background(0, 0, 0);
        main.fill(255, 255, 255);

        // nice big font.
        int textSize = 50;
        main.textSize(textSize);

        // draw the Game Over text
        String s = "Game\nOver.\n";
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2 - textSize * 3);

        // now, we need to display the counting-up stats.
        // "cherries eaten" is always visible
        s = "\nCherries eaten:\t" + cherries;

        // if the cherry animation has finished, draw the life average
        if (cherries >= stat_cherries) {
            s += "\nAverage Lives:\t" + lifeAverage;
        } else {
            s += "\n";
        }

        // if the life average animation has finished, draw the difficulty
        if (lifeAverage >= stat_lifeAverage) {
            s += "\nDifficulty: " + stat_mode.toString().replace('_', ' ');
        } else {
            s += "\n";
        }

        // in a smaller font, draw the stats
        int y = main.height / 2 - textSize;
        main.textSize(30);
        main.text(s, (main.width - main.textWidth(s)) / 2, y);

        // di the animations
        if (cherries < stat_cherries) {
            cherries += (stat_cherries - cherries) / 100;
            cherries++;
        } else if (lifeAverage < stat_lifeAverage) {
            lifeAverage += (stat_lifeAverage - lifeAverage) / 500;
            lifeAverage++;
        }

        // draw the back to menu button
        s = "BACK TO MENU";
        reset.setText(s);
        reset.setHeight(main.height / 14);
        reset.autosetWidth(main);
        reset.setX((main.width - reset.getWidth()) / 2);
        reset.setY(main.height / 2 + 150);

        reset.draw(main);
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
        // when the user clicks the back to menu button, take them back.
        if (ev instanceof EventMouse.EventMouseClicked
                && reset.isInside(main.mouseX, main.mouseY)) {
            main.popState(1);
        }
    }

}
