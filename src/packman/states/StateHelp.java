/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packman.states;

import packman.Event;
import packman.Main;
import packman.State;
import packman.events.EventMouse;

/**
 * Displays in-game help
 *
 * @author campbell
 */
public class StateHelp implements State {

    @Override
    public void setup(Main main, State s) {
    }

    @Override
    public void draw(Main main) {
        // white with black text
        main.background(255, 255, 255);
        main.fill(0, 0, 0);
        
        // text size
        int textSize = 20;
        main.textSize(textSize);
        
        // the help text
        String s = "HELP"
                + "\n* Arrow keys to roll packmann"
                + "\n* If you come off the edge, you lose a life"
                + "\n* Try to eat all the cherries"
                + "\n* Small boxes fall from the sky. They are"
                + "\n   explosives. When they go white, they are"
                + "\n   armed and will explode on contact."
                + "\n* Explosives do not kill you, but rather can"
                + "\n   fling you off the side."
                + "\n* ESC to enter pause menu in-game"
                + "\n* If you run out of lives, you die."
                + "\n"
                + "\nClick to return.";
        
        int numLines = s.split("\n").length; // find the number of lives
        int textHeight = textSize * numLines; // and the total text height
        
        // draw it in the middle of the screen
        main.text(s, (main.width - main.textWidth(s)) / 2, (main.height - textHeight) / 2);
    }

    @Override
    public void exit(Main main) {
    }

    @Override
    public void suspend(Main main, State s) {
    }

    @Override
    public void handleEvent(Main main, Event ev) {
        if (ev instanceof EventMouse.EventMouseClicked) {
            main.popState(1);
        }
    }

}
