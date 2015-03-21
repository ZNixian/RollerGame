/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Button;
import packman.ButtonManager;
import packman.Event;
import packman.GameMode;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 * Title screen menu.
 *
 * @author campbell
 */
public class StateMenu implements State {

    private final Button play = new Button(); // play-game button
    private final Button modeChanger = new Button(); // change the current difficulty
    private final Button help = new Button(); // show the help menu
    private final Button quit = new Button(); // quit the game
    
    private final ButtonManager manager = new ButtonManager(); // the ButtonManager
    
    private GameMode mode = GameMode.VERY_EASY; // the current difficulty

    @Override
    public void setup(Main main, State s) {
        // set up all the buttons
        
        manager.getButtons().add(play);
        play.setText("PLAY");
        
        manager.getButtons().add(modeChanger);
        
        manager.getButtons().add(help);
        help.setText("HELP");
        
        manager.getButtons().add(quit);
        quit.setText("QUIT GAME");
    }

    @Override
    public void draw(Main main) {
        // black background with white text
        main.background(0, 0, 0);
        main.fill(255, 255, 255);
        
        // text that is 1/6th of the screen height
        main.textSize(main.height / 6);
        
        // the game's name
        String s = "Roller";
        
        // display it
        main.text(s, (main.width - main.textWidth(s)) / 2, main.height / 2);

        ///////
        
        // set the mode change button's text
        s = mode.toString().replace('_', ' ');
        modeChanger.setText(s);
        
        // draw all the buttons
        manager.draw(main.height / 2 + 25, main);
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
        // if the mouse was clicked
        if (ev instanceof EventMouse.EventMouseClicked) {
            // depending on what button was pressed, do
            // the correct thing
            
            if (play.isInside(main.mouseX, main.mouseY)) {
                main.pushState(new StateInGame(mode)); // start the game
            } else if (modeChanger.isInside(main.mouseX, main.mouseY)) {
                int id = mode.ordinal(); // advance the mode
                id = (id + 1) % GameMode.values().length;
                mode = GameMode.values()[id];
            } else if (quit.isInside(main.mouseX, main.mouseY)) {
                main.popState(1); 
                // pop our state. There will be no more
                // states, and the game will quit
            } else if (help.isInside(main.mouseX, main.mouseY)) {
                main.pushState(new StateHelp()); // open the help menu
            }
        }
    }

}
